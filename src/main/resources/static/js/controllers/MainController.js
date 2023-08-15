"use strict";
app.controller("MainController", function ($scope, SocketClientService) {
  $scope.onlineUsers = [];
  (function init() {
    SocketClientService.connect()
      .then(connectedToSocket)
      .catch(handleFailureSocketConnection);
  })();
  function connectedToSocket() {
    SocketClientService.subscribe("/app/chat.users-online").then((message) => {
      $scope.$apply(function () {
        $scope.onlineUsers = JSON.parse(message.body);
        console.log(JSON.parse(message.body));
      });
    });
    SocketClientService.subscribe("/topic/chat.login").then((loginEvent) => {
      $scope.$apply(function () {
        $scope.onlineUsers.push(JSON.parse(loginEvent.body));
      });
    });
    SocketClientService.subscribe("/topic/chat.logout", (logoutEvent) => {
      $scope.$apply(function () {
        const username = JSON.parse(logoutEvent.body).username;
        $scope.onlineUsers = $scope.onlineUsers.filter(
          (user) => user.user.username !== username
        );
      });
    });
  }
  function handleFailureSocketConnection(err) {
    alert(err.message);
  }
});
