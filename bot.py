#!/bin/env python3
'''
in db:
    {
        "listener:<id>": { # different for board and non-board listeners
            "unlocked": true,
            "vk_subscribers": true,
            "vk_new_post": true,
            "fb_new_msg": true
        },
        "listener:<other_id>": { # different for board and non-board listeners
            "unlocked": false,
            "vk_new_post": true
        },
    }
'''

import os
import logging
import random

from telegram.ext import Updater, CommandHandler, MessageHandler, Filters
from telegram import MessageEntity


# Enable logging
logging.basicConfig(format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
                    level=logging.INFO)

logger = logging.getLogger(__name__)

salem_sticker_set = [
    "CAADAgADcAEAAgyHegPoqq29dLfTYhYE",
    "CAADAgADegEAAgyHegMjVMDQm6SOVBYE",
    "CAADAgADgwEAAgyHegNY2CqI4-8UYhYE",
    "CAADAgADiAEAAgyHegN5Nhhq274c4hYE",
    "CAADAgADigEAAgyHegNpazd6JQ9QfhYE",
    "CAADAgADhwEAAgyHegMTFprP8OfduBYE",
    "CAADAgADjAEAAgyHegOOytztBFQXMRYE",
]


def unlocked(chat_id):
    #TODO
    return False


def unlock(chat_id):
    #TODO
    return


def subscribed(chat_id):
    #TODO
    return False


def subscribe(chat_id):
    #TODO
    return


def unsubscribe(chat_id):
    #TODO
    logger.info("Unsubscribing")
    return


def message_one_or_many(update, context, msg_one, msg_many):
    if (update.message.chat.type == "private"):
        context.bot.send_message(chat_id=update.message.chat.id, text=msg_one)
    else:
        context.bot.send_message(chat_id=update.message.chat.id, text=msg_many)


# Define a few command handlers. These usually take the two arguments update and
# context. Error handlers also receive the raised TelegramError object in error.
def start(update, context):
    """Send a welcome message when added to the new chat."""
    bot_id = context.bot.get_me().id
    for user in update.message.new_chat_members:
        if (user.id == bot_id):
            break
    else:
        return

    subscribe(update.message.chat.id)
    message_one_or_many(update, context, "Hello, witch!", "Hello, witches!")
    

def cmd_start(update, context):
    """Send a welcome message when the command /start is issued."""

    if (subscribed(update.message.chat.id)):
        context.bot.send_message(chat_id=update.message.chat.id, text='Hello again!')
        return

    subscribe(update.message.chat.id)
    message_one_or_many(update, context, "Hello, witch!", "Hello, witches!")


def stop(update, context):
    """Stop sending updates and remove id from the list when removed from the chat"""

    bot_id = context.bot.get_me().id
    if (update.message.left_chat_member.id != bot_id):
        return

    logger.info("Was deleted from chat")
    unsubscribe(update.message.chat.id)


def cmd_stop(update, context):
    """Stop sending updates and remove id from the list when the command /stop is issued"""

    #TODO request confirmation
    #TODO check if was started
    logger.info("Recieved command to stop in chat ")
    unsubscribe(update.message.chat.id)
    message_one_or_many(update, context, "Burn in Hell, witch!", "Burn in Hell, witches!")


def help(update, context):
    """Send a message when the command /help is issued."""
    context.bot.send_message(chat_id=update.message.chat.id, text="No one`s gonna help you")
    context.bot.send_message(chat_id=update.message.chat.id, text="*evil laughter*")


def cmd_alohomora(update, context):
    """Unlock hidden functionality"""
    if (unlocked(update.message.chat.id)):
        context.bot.send_message(chat_id=update.message.chat.id, text="You already have everything under your control 😈")
        return

    words = update.message.text.split(' ')
    if (len(words)>=2 and words[1] == os.environ.get('ALOHOMORA')):
        update.message.reply_text("Full features unlocked, you little witch!")
    else:
        update.message.reply_text("This spell is not so easy to break!")


def error(update, context):
    """Log Errors caused by Updates."""
    logger.warning('Update "%s" caused error "%s"', update, context.error)


#@send_action(ChatAction.TYPING)
def random_salem_sticker(update, context):
    """Respond with a random salem sticker to the unknown command or text"""
    context.bot.send_sticker(
        chat_id=update.message.chat.id,
        sticker=random.choice(salem_sticker_set)
    )


def main():
    """Start the bot."""

    BOT_TOKEN = os.environ.get('TG_BOT_TOKEN')
    updater = Updater(BOT_TOKEN, use_context=True)

    # Get the dispatcher to register handlers
    dp = updater.dispatcher

    # on different commands - answer in Telegram
    dp.add_handler(CommandHandler("start", cmd_start))
    dp.add_handler(CommandHandler("stop", cmd_stop))
    dp.add_handler(CommandHandler("alohomora", cmd_alohomora))
    dp.add_handler(CommandHandler("help", help))

    dp.add_handler(MessageHandler((Filters.text | Filters.command), random_salem_sticker))
    dp.add_handler(MessageHandler(Filters.status_update.new_chat_members, start))
    dp.add_handler(MessageHandler(Filters.status_update.left_chat_member, stop))

    # log all errors
    dp.add_error_handler(error)

    # Start the Bot
    updater.start_polling()

    # Run the bot until you press Ctrl-C or the process receives SIGINT,
    # SIGTERM or SIGABRT. This should be used most of the time, since
    # start_polling() is non-blocking and will stop the bot gracefully.
    updater.idle()


if __name__ == '__main__':
    main()
