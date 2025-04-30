FROM openjdk:11-jdk-slim

WORKDIR /app

COPY Minesweeper.java /app/
COPY entrypoint.sh /app/

RUN javac Minesweeper.java && \
    chmod +x /app/entrypoint.sh

ENTRYPOINT ["/app/entrypoint.sh"] 