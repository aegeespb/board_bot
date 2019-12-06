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

from telegram.ext import Updater, CommandHandler, MessageHandler, Filters, ConversationHandler
from telegram import MessageEntity, ReplyKeyboardMarkup, ReplyKeyboardRemove


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
    return True


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


# Settings Conversation
PARAMETER, VK_SUBS, VK_POST, FB_MSG = range(4)


def settings(update, context):

    if (not unlocked(update.message.chat.id)):
        context.bot.send_message(
            chat_id=update.message.chat.id,
            text='Unfortunately, settings are available only for witches :('
        )
        return ConversationHandler.END


    reply_keyboard = [['vk_subs', 'vk_post', 'fb_msg']]

    context.bot.send_message(
        chat_id=update.message.chat.id,
        text='You can change the following parameters:\n'
        'vk_subs - notify about VK community subscribers\n'
        'vk_post - notify about new posts in VK community\n'
        'fb_msg - notify about new PM in FB community\n'
        '\n'
        'Send /cancel to stop talking to me.\n\n'
        'Which parameter do you want to change?',
        reply_markup=ReplyKeyboardMarkup(reply_keyboard, 
            resize_keyboard=True,
            one_time_keyboard=True))

    return PARAMETER


def choose_param(update, context):
    reply_keyboard = [['Enable', 'Disable']]

    if (update.message.text == 'vk_subs'):
        context.bot.send_message(
            chat_id=update.message.chat.id,
            text="'Enable' - notify when people subscribe/unsubscribe\n"
            "'Disable' - stop notifications about subscriptions/unsubscriptions\n\n"
            "Current status is: DISABLED",
            reply_markup=ReplyKeyboardMarkup(reply_keyboard, 
                resize_keyboard=True,
                one_time_keyboard=True))
        return VK_SUBS
    elif (update.message.text == 'vk_post'):
        context.bot.send_message(
            chat_id=update.message.chat.id,
            text="'Enable' - notify when new post was created in the community\n"
            "'Disable' - stop notifications about subscriptions/unsubscriptions\n\n"
            "Current status is: DISABLED",
            reply_markup=ReplyKeyboardMarkup(reply_keyboard, 
                resize_keyboard=True,
                one_time_keyboard=True))
        return VK_POST
    elif (update.message.text == 'fb_msg'):
        context.bot.send_message(
            chat_id=update.message.chat.id,
            text="'Enable' - notify when people subscribe/unsubscribe\n"
            "'Disable' - stop notifications about subscriptions/unsubscriptions\n\n"
            "Current status is: DISABLED",
            reply_markup=ReplyKeyboardMarkup(reply_keyboard, 
                resize_keyboard=True,
                one_time_keyboard=True))
        return FB_MSG

    return None


def set_value(param, val):
    #TODO implement
    pass


def set_vk_subs(update, context):
    set_value("vk_subs", update.message.text == 'Enabled')
    logger.info("Settings for VK subscriptions were successfully updated")
    update.message.reply_text('Success! Settings for VK subscriptions were updated',
                              reply_markup=ReplyKeyboardRemove())
    return ConversationHandler.END


def set_vk_post(update, context):
    set_value("vk_post", update.message.text == 'Enabled')
    logger.info("Settings for VK post notifications were successfully updated")
    update.message.reply_text('Success! Settings for VK post notifications were updated',
                              reply_markup=ReplyKeyboardRemove())
    return ConversationHandler.END


def set_fb_msg(update, context):
    set_value("fb_msg", update.message.text == 'Enabled')
    logger.info("Settings for FB message notifications were successfully updated")
    update.message.reply_text('Success! Settings for FB message notifications were updated',
                              reply_markup=ReplyKeyboardRemove())
    return ConversationHandler.END


def cancel(update, context):
    logger.info("Settings were cancelled")
    update.message.reply_text('Cancelled.',
                              reply_markup=ReplyKeyboardRemove())

    return ConversationHandler.END


def unknown_response(update, context):
    context.bot.send_message(
        chat_id=update.message.chat.id,
        text='Unknown parameter, try again.'
    )
    return None


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

    # Add conversation handler with the states GENDER, PHOTO, LOCATION and BIO
    conv_handler = ConversationHandler(
        entry_points=[CommandHandler('settings', settings)],

        states={
            PARAMETER: [MessageHandler(Filters.regex('^(vk_subs|vk_post|fb_msg)$'), choose_param)],

            VK_SUBS: [MessageHandler(Filters.regex('^(Enable|Disable)$'), set_vk_subs)],
            VK_POST: [MessageHandler(Filters.regex('^(Enable|Disable)$'), set_vk_post)],
            FB_MSG:  [MessageHandler(Filters.regex('^(Enable|Disable)$'), set_fb_msg)]
        },

        fallbacks=[
            CommandHandler('cancel', cancel),
            MessageHandler((Filters.text | Filters.command), unknown_response)
        ]
    )

    dp.add_handler(conv_handler)

    dp.add_handler(MessageHandler(Filters.status_update.new_chat_members, start))
    dp.add_handler(MessageHandler(Filters.status_update.left_chat_member, stop))
    dp.add_handler(MessageHandler((Filters.text | Filters.command), random_salem_sticker))

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
