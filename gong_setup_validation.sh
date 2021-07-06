#!/bin/bash
TEST_MODE_ARG="TEST_MODE"
TEST_VERIFICATION_FOLDER="/tmp/gong-verification"
APP_NAME="test foo"
APP_PACKAGE="io.foo"

PROJECT_FOLDER="$(pwd)"

echo "Initializing project"
rm -rf "$TEST_VERIFICATION_FOLDER"
mkdir -p "$TEST_VERIFICATION_FOLDER"
cd "$TEST_VERIFICATION_FOLDER" || exit
echo -e "$APP_NAME\n$APP_PACKAGE" |"$PROJECT_FOLDER"/gong_setup.sh $TEST_MODE_ARG "$PROJECT_FOLDER" > /dev/null
cd "$TEST_VERIFICATION_FOLDER/test-foo" || exit 1
echo -e "  OK\n"

echo "Check app's packages"
[ -d "./build" ] && ./gradlew clean -q
GONG_LINES=$(grep -irnw --exclude={README.md,gong_setup_validation.sh,versions.gradle,*.iml} --exclude-dir={.git,README.md,.gradle,.idea,secrets} $TEST_VERIFICATION_FOLDER -e 'gong')
if [ -n "${GONG_LINES}" ]; then
  echo -e "Gong word appear in multiple places\n'$GONG_LINES'"
  exit 1
fi
echo -e "  OK\n"

echo "Check app's folder structure"
cd "app/src/" || exit 1
for folder in */ ; do
  directory="$(pwd)/${folder}java/io/foo/"
  [ ! -d "$directory" ] && echo "Error: Directory $directory DOES NOT exists." && exit 1
done
cd "../.."
echo -e "  OK\n"

echo "Compiling project"
./gradlew build -q
echo -e "  OK\n"
