#!/usr/bin/with-contenv bash

connectKey=$(eval "$CONNECT_KEY_COMMAND")

if [ "$connectKey" == "" ]
then
  >&2 echo "No connect key could be found. Please set the CONNECT_KEY and/or CONNECT_KEY_COMMAND environment variable."
  exit 10
else 
  echo "Connect key found"
fi