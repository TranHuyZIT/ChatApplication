"use strict";
app.factory("SocketClientService", function () {
  const stompClient = Stomp.over(new SockJS("/chat-websocket"));
  return {
    connect() {
      const connectionPromise = new Promise((resolve, reject) =>
        stompClient.connect(
          {},
          (frame) => {
            resolve(frame);
          },
          (error) => {
            reject(error);
          }
        )
      );
      return connectionPromise;
    },
    subscribe(destination) {
      return new Promise((resolve, reject) =>
        stompClient.subscribe(
          destination,
          (message) => {
            resolve(message);
          },
          (error) => reject(error)
        )
      );
    },
    sendMessage(destination, headers, message) {
      stompClient.send(destination, headers, message);
    },
  };
});
