import os


class Config(object):
    REDIS_URL = os.environ.get("REDIS_URL") or "redis://redis:6379"
    TG_BOT_TOKEN = os.environ.get("TG_BOT_TOKEN")
