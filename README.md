# #Aegee Board Bot

## Supported notifications
 * A new member has joined
 * A member has left
 * A new wall post has published

## Environment variables 
 * `BOARD_BOT_TOKEN` - can be fetched from BotFather
 * `VK_GROUP_ID` - https://regvk.com/id/
 * `VK_GROUP_TOKEN` - https://vk.com/club%groupId%?act=tokens
 * `FIREBASE_DB_URL` - https://console.firebase.google.com/project/%projectName%/settings/serviceaccounts/adminsdk
 * `FIREBASE_DB_SERVICE_ACCOUNT_KEY_CONTENT` - https://console.firebase.google.com/project/%projectName%/settings/serviceaccounts/adminsdk

## Deployment on heroku
 * pause: `heroku ps:scale worker=0`
 * restart: `heroku ps:scale worker=1`
 * deploy new commits: `git push heroku master`
 * see logs: `heroku logs`
 * set environment variable: `heroku config:set %key%=%value%`