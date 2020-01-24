FROM tiangolo/meinheld-gunicorn:python3.7

RUN pip install --upgrade pip && \
    pip install flask redis rq python-telegram-bot requests

COPY ./*.py /app/
