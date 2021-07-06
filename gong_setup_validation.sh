#!/bin/bash
TEST_MODE_ARG="TEST_MODE"
TEST_VERIFICATION_FOLDER="/tmp/gong-verification"
APP_NAME="test foo"
APP_PACKAGE="io.foo"

PROJECT_FOLDER="$(pwd)"

rm -rf "$TEST_VERIFICATION_FOLDER"
mkdir -p "$TEST_VERIFICATION_FOLDER"
cd "$TEST_VERIFICATION_FOLDER" || exit
echo -e "$APP_NAME\n$APP_PACKAGE" |"$PROJECT_FOLDER"/gong_setup.sh $TEST_MODE_ARG "$PROJECT_FOLDER"

cd "$TEST_VERIFICATION_FOLDER/test-foo" || exit 1
[ -d "./build" ] && ./gradlew clean
GONG_LINES=$(grep -irnw --exclude={README.md,gong_setup_validation.sh,versions.gradle,*.iml} --exclude-dir={.git,README.md,.gradle,.idea,secrets} $TEST_VERIFICATION_FOLDER -e 'gong')

if [ -n "${GONG_LINES}" ]; then
  echo -e "Gong word appear in multiple places\n'$GONG_LINES'"
  exit 1
fi

cd "$PROJECT_FOLDER" || exit 1
