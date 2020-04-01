#!/usr/bin/with-contenv bash

bootstrapFile="/opt/jc/agentBootstrap.json"
connectKey=$(eval "$CONNECT_KEY_COMMAND")

cat <<-EOF >"${bootstrapFile}"
    {
       "publicKickstartUrl": "https://kickstart.jumpcloud.com",
       "privateKickstartUrl": "https://private-kickstart.jumpcloud.com",
       "connectKey": "$connectKey"
    }
EOF