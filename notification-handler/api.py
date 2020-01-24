import os
import json

from flask import Flask, request, Response
import redis
from rq import Queue
from telegram import Bot

from config import Config
from workers import notify


app = Flask(__name__)
app.config.from_object(Config)

rd = redis.from_url(app.config['REDIS_URL'], decode_responses=True)
q = Queue(connection=rd)

tg_bot = Bot(app.config['TG_BOT_TOKEN'])


@app.route('/vk', methods=['POST'])
def handle_vk():

    # Content-Type must be set to 'application/json'
    if (not request.is_json):
        return Response(status=404)

    req = request.json

    # Check that this group_id is authorized
    gid = req["group_id"]
    group_info = json.loads(rd.get("vkgid:"+str(gid)))

    if (req["secret"] != group_info["secret"]):
        return Response(status=403)

    # Handle request
    if (req["type"] == "confirmation"):
        return group_info["confirm"]

    if (req["type"] in ["message_new", "group_join", "group_leave", "wall_post_new"]):
        q.enqueue(notify, args=(tg_bot, 'vk', req, group_info["token"], group_info["listeners"]))
        return "ok"
    
    return Response(status=404)


@app.route('/fb', methods=['POST'])
def handle_fb():
    return Response(status=404)
