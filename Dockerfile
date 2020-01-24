FROM tiangolo/meinheld-gunicorn:python3.7

RUN mkdir app

WORKDIR /app

COPY Pipfile* ./

RUN pip install --upgrade pip && \
    pip install pipenv && \
    pipenv install --deploy --system
    

COPY ./api.py ./config.* ./workers.py ./
