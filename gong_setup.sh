#!/bin/bash

BASE_PROJECT_PAKAGE_NAME="com.xmartlabs.gong"
BASE_PROJECT_NAME="gong"
BASE_PROJECT_REAL_NAME="Base project"
BASE_PROJECT_DB_NAME="base_project_database"
GIT_BASE_PROJECT_URL="https://github.com/xmartlabs/gong.git"
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
  echo "working on $(pwd)"
  # Replace Project Name
  find . -type f \( -name "*.xml" -o -name "*.gradle" \) -exec perl -i -pe "s/$BASE_PROJECT_NAME/$REAL_PROJECT_NAME/gi" {} \;

  new_path=$(sed "s/\./\//g" <<<"$PACKAGE_NAME")
  gong_new_path=$(sed "s/\./\//g" <<<"$BASE_PROJECT_PAKAGE_NAME")

  first_gong_folder=$(sed 's/\..*//' <<<"$BASE_PROJECT_PAKAGE_NAME")

  cd "app/src/" || exit 1

  if [ -d "$TEMPORAL_FOLDER" ]; then rm -Rf $TEMPORAL_FOLDER; fi
  mkdir "$TEMPORAL_FOLDER"

  movePackage "main"
  movePackage "androidTest"
  movePackage "test"

  cd ../../../
  mv $BASE_PROJECT_NAME "$PROJECT_NAME"
  cd "$PROJECT_NAME" || exit 1
}

function changePackageName() {
  echo "working on $(pwd)"
  find . -type f \( -name "*.xml" -o -name "*.gradle" -o -name "*.kt" -o -name "*.java" \) -exec perl -i -pe "s/$BASE_PROJECT_PAKAGE_NAME/$PACKAGE_NAME/g" {} \;
  find . -type f \( -name "*.xml" -o -name "*.gradle" -o -name "*.kt" -o -name "*.java" \) -exec perl -i -pe "s/$BASE_PROJECT_REAL_NAME/$REAL_PROJECT_NAME/g" {} \;
}

function cloneAndSetupRepository() {
  echo "working on $(pwd)"
  git clone --depth=1 $GIT_BASE_PROJECT_URL
  cd "$BASE_PROJECT_NAME" || exit 1
}

function finishGitSetup() {
  currentCommitHash=$(git rev-parse --short HEAD)
  rm -rf .git
  git init
  git add -A
  git commit -m "Initial commit - Based On Gong $currentCommitHash"

  if [ -n "$NEW_REMOTE_URL" ]; then
    git remote add origin "$NEW_REMOTE_URL"
    git remote -v
  fi
}

function removeUnusedFiles() {
  rm "$SCRIPT_NAME"
}

if [ -d "$BASE_PROJECT_NAME" ]; then echo "Gong temporal director error, please delete '$BASE_PROJECT_NAME' folder" && exit 1; fi

echo "-----------------------------------------------------------"
echo "Gong - Xmartlabs' Android Base Project initialization process"
echo -e "-----------------------------------------------------------\n"
echo "What's your project name?"
read -r REAL_PROJECT_NAME
PROJECT_NAME=${REAL_PROJECT_NAME// /-}

echo "What is the new package name? (For example: 'com.xmartlabs.gong')"
read -r PACKAGE_NAME

echo "what is the git remote url? (optional parameter)"
read -r NEW_REMOTE_URL

clear
cloneAndSetupRepository
changePackageName
changeProjectName
removeUnusedFiles
finishGitSetup
cd ..
