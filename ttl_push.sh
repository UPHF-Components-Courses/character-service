#!/bin/sh

##############################################################################################
# This script builds the project using maven and pushes it to https://ttl.sh/.
# An image name has to be provided.
#
# It requires to have maven and docker installed locally
#
# Example usage:
# $ ./ttl_push.sh myimage-1.0.0
#
##############################################################################################
# [SETTINGS]
# The amount of time the image has to be stored
TTL_STORAGE_DURATION=1h

##############################################################################################
# [EXECUTION]

# Declare variables
STEP=0

# Declare utils functions
printExec() {
  echo "\033[1;37m[Step $STEP: \033[1;33mExecuting\033[1;37m]\033[0m $1"
}

printSuccess() {
  echo "\033[1;37m[Step $STEP: \033[1;32mDone\033[1;37m]\033[0m $1"
}

printFailure() {
  echo "\033[1;37m[Step $STEP: \033[1;31mFailure\033[1;37m]\033[0m $1"
}

# [Retrieve image name]
STEP=$((STEP+1))
printExec "Reading image name..."

# Check inputs
if [ $# != 1 ]; then
  printFailure "Failed to start command: an image name should be provided"
  printf "\nExample usage:\n $ ./ttl_push.sh myimage-1.0.0\n";
  exit 1;
fi
IMAGE_NAME=$1

printSuccess "Image name set to $IMAGE_NAME"

# [Build project]
STEP=$((STEP+1))
printExec "Building project using maven..."

mvn clean package \
  || (printFailure "An error occurred while building project" && exit 1)

printSuccess "The project has been successfully built"

# [Create tag]
STEP=$((STEP+1))
printExec "Creating image tag..."

IMAGE_TAG=ttl.sh/"$IMAGE_NAME":"$TTL_STORAGE_DURATION"

printSuccess "Image tag set to $IMAGE_TAG"

# [Build docker image]
STEP=$((STEP+1))
printExec "Building docker image..."

docker build -t "$IMAGE_TAG" . \
  || (printFailure "An error occurred while building docker image" && exit 1)

printSuccess "The docker image has been successfully built"

# [Push image]
STEP=$((STEP+1))
printExec "Pushing docker image..."

docker push "$IMAGE_TAG" \
  || (printFailure "An error occurred while pushing docker image" && exit 1)

printSuccess "The docker image has been successfully pushed"

# [Next]
printf "\nYou can now retrieve the image by using the following command:\n$ docker pull %s\n" "$IMAGE_TAG"

exit 0