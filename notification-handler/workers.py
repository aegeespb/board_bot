import json
import requests


def vk_get_user_info(user_id, token):
    r = requests.get("https://api.vk.com/method/users.get?user_ids={}&access_token={}&v=5.0".format(user_id, token))
    first_name = r.json()["response"][0]["first_name"]
    last_name  = r.json()["response"][0]["last_name"]
    return (first_name, last_name)

def vk_get_info(msg, token):
    if (msg["type"] == "message_new"):
        first_name, last_name = vk_get_user_info(msg["object"]["message"]["from_id"], token)
        return "*Новое сообщение VK*\n{} {} написал\(а\):\n{}".format(first_name, last_name, msg["object"]["message"]["text"])
    elif (msg["type"] == "group_join"):
        first_name, last_name = vk_get_user_info(msg["object"]["user_id"], token)
        return "*Новый участник группы VK*\n{} {}\nhttps://vk\.com/id{}".format(first_name, last_name, msg["object"]["user_id"])
    elif (msg["type"] == "group_leave"):
        first_name, last_name = vk_get_user_info(msg["object"]["user_id"], token)
        return "*Покинул\(а\) группу VK*\n{} {}\nhttps://vk\.com/id{}".format(first_name, last_name, msg["object"]["user_id"])
    elif (msg["type"] == "wall_post_new"):
        return "*Новый пост на стене VK*"
    else:
        return "Unknown notification"


def notify(tg_bot, src, msg, token, listeners):
    if (src == "vk"):
        info = vk_get_info(msg, token)
    else:
        return

    for chat_id, settings in listeners.items():
        tg_bot.sendMessage(
            chat_id=chat_id,
            text=info,
            parse_mode='MarkdownV2'
        )
