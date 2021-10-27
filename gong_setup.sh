#!/bin/bash

TEST_MODE_ARG="TEST_MODE"

BASE_PROJECT_PAKAGE_NAME="com.xmartlabs.gong"
BASE_PROJECT_NAME="gong"
GIT_BASE_PROJECT_URL="https://github.com/xmartlabs/gong.git"
GIT_BRANCH="" # If it's empty, the project thefault branch is used
TEMPORAL_FOLDER="/tmp/gong"
SCRIPT_NAME="gong_setup.sh"

function movePackage() {
  if [ -d "$TEMPORAL_FOLDER/$first_gong_folder" ]; then rm -Rf "${TEMPORAL_FOLDER:?}/$first_gong_folder"; fi

  mv "$1/java/$first_gong_folder" "$TEMPORAL_FOLDER"
  temporalNewPath="$1/java/$new_path"
  mkdir -p "$temporalNewPath"
  mv "$TEMPORAL_FOLDER/$gong_new_path"/* "$temporalNewPath"
}

function changeProjectName() {
  # Replace Project Name
  find "app/src/main/res/values" -type f -name "*.xml" -exec perl -i -pe "s/$BASE_PROJECT_NAME/$REAL_PROJECT_NAME/gi" {} \;
  find "app/src/dev/res/values" -type f -name "*.xml" -exec perl -i -pe "s/$BASE_PROJECT_NAME/$REAL_PROJECT_NAME/gi" {} \;
  perl -i -pe "s/$BASE_PROJECT_NAME/$REAL_PROJECT_NAME/gi" build.gradle
  perl -i -pe "s/\"$BASE_PROJECT_NAME/\"$REAL_PROJECT_NAME/gi" app/build.gradle

  # Replace package names
  find . -type f \( -name "*.xml" -o -name "*.gradle" -o -name "*.kt" -o -name "*.java" -o -name "*.pro" \) -exec perl -i -pe "s/$BASE_PROJECT_PAKAGE_NAME/$PACKAGE_NAME/g" {} \;

  # Change file structure
  new_path=$(sed "s/\./\//g" <<<"$PACKAGE_NAME")
  gong_new_path=$(sed "s/\./\//g" <<<"$BASE_PROJECT_PAKAGE_NAME")
  first_gong_folder=$(sed 's/\..*//' <<<"$BASE_PROJECT_PAKAGE_NAME")
  cd "app/src/" || exit 1

  if [ -d "$TEMPORAL_FOLDER" ]; then rm -Rf $TEMPORAL_FOLDER; fi
  mkdir "$TEMPORAL_FOLDER"

  for folder in */ ; do
    movePackage "$folder"
  done

  cd ../../../
  mv $BASE_PROJECT_NAME "$PROJECT_NAME"
  cd "$PROJECT_NAME" || exit 1
}

function cloneAndSetupRepository() {
  git clone $GIT_BASE_PROJECT_URL --quiet
  cd "$BASE_PROJECT_NAME" || exit 1
  if [ -n "$GIT_BRANCH" ]; then
    git checkout "$GIT_BRANCH" --quiet
    git branch --quiet | grep -v "$GIT_BRANCH" | xargs git branch -D --quiet
  fi
}

function finishGitSetup() {
  currentCommitHash=$(git rev-parse --short HEAD)
  rm -rf .git
  git init >/dev/null
  git add -A

  # Add excluded files
  git add .idea/codeStyles/Project.xml -f
  git add .idea/codeStyles/codeStyleConfig.xml -f
  git add .idea/dictionaries/project_dictionary.xml -f
  git add .idea/navEditor.xml -f

  git commit -m "Initial commit - Based On Gong $currentCommitHash" --quiet
  git branch -M main

  if [ -n "$NEW_REMOTE_URL" ]; then
    git remote add origin "$NEW_REMOTE_URL"
  fi
}

function removeUnusedFiles() {
  rm "$SCRIPT_NAME" "gong_setup_validation.sh" ".github/CODEOWNERS" "LICENSE" ".github/workflows/check_setup_script.yml"
}

if [ -d "$BASE_PROJECT_NAME" ]; then echo "Gong temporal director error, please delete '$BASE_PROJECT_NAME' folder" && exit 1; fi

echo "-------------------------------------------------------------"
echo "Gong - Xmartlabs' Android Base Project initialization process"
echo -e "-------------------------------------------------------------\n"
echo "What's your project name?"
read -r REAL_PROJECT_NAME
REAL_PROJECT_NAME=$(echo "$REAL_PROJECT_NAME" | xargs)
PROJECT_NAME=${REAL_PROJECT_NAME// /-}

[[ -z "$PROJECT_NAME" ]] && echo "Project name cannot be empty" && exit 1

echo "What is the new package name? (For example: 'com.xmartlabs.gong')"
read -r PACKAGE_NAME
PACKAGE_NAME=$(echo "$PACKAGE_NAME" | xargs)
[[ -z "$PACKAGE_NAME" ]] && echo "Package name cannot be empty" && exit 1

echo "what is the git remote url? (optional parameter)"
read -r NEW_REMOTE_URL

echo "Start clone repository process..."
if [ "$1" = "$TEST_MODE_ARG" ]; then
  echo -e "TEST MODE\n\n"
  REPO_URL="$2"

  cp -rf "$REPO_URL" .
  cd "$BASE_PROJECT_NAME" || exit
else
  [[ -z "$1" ]] || GIT_BRANCH="$1"
  cloneAndSetupRepository
fi

echo "Rename project files..."
changeProjectName
echo "Remove unused files..."
removeUnusedFiles
echo "Setup new Git repository..."
finishGitSetup
cd ..
echo "Process finished, you can check the project at $(pwd)/$PROJECT_NAME"
