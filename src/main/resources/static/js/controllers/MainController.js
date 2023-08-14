"use strict";
app.controller("MainController", function ($scope, SocketClientService) {
  $scope.onlineUsers = [];
  (function init() {
    alert(1);
    SocketClientService.connect()
      .then(connectedToSocket)
      .catch(handleFailureSocketConnection);
  })();
  function connectedToSocket() {
    SocketClientService.subscribe("/app/chat.users-online").then((message) => {
      $scope.onlineUsers = message.body;
      console.log($scope.onlineUsers);
    });
  }
  function handleFailureSocketConnection(err) {
    alert(err.message);
  }
});
