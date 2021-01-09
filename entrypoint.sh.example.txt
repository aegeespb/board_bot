#!/bin/sh

cat <<EOF
   Conference bot wrapper.
 * Installation directory: ${BOT_DIST}
EOF

echo ${BOT_DIST}
cd ${BOT_DIST}/bin && ./conf-bot
