"use strict";
app.factory("SocketClientService", function ($rootScope) {
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
    subscribe(destination, callback) {
      stompClient.subscribe(destination, function (message) {
        $rootScope.$apply(function () {
          callback(message);
        });
      });
    },
    sendMessage(destination, headers, message) {
      stompClient.send(destination, headers, message);
    },
  };
});
