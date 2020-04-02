#!/usr/bin/with-contenv bash

localTime="$(date +%s)"
connectKey=$(eval "$CONNECT_KEY_COMMAND")

curl --tlsv1.2 --silent --show-error --fail --header "X-Connect-Key: $connectKey" --data "time=$localTime" "https://kickstart.jumpcloud.com/Time"