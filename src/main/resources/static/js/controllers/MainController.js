"use strict";
app.controller("MainController", function ($scope, SocketClientService) {
  $scope.onlineUsers = [];
  $scope.chatWith = null;
  (function init() {
    SocketClientService.connect()
      .then(connectedToSocket)
      .catch(handleFailureSocketConnection);
  })();
  $scope.privateChat = function (user) {
    console.log(user);
    $scope.chatWith = user.user.username;
  };
  $scope.publicChat = function () {
    $scope.chatWith = null;
  };
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
      const username = JSON.parse(logoutEvent.body).username;
      const filtered = $scope.onlineUsers.filter((user) => {
        return user.user.username !== username;
      });
      $scope.$apply(function () {
        $scope.onlineUsers = filtered;
      });
    });
  }
  function handleFailureSocketConnection(err) {
    alert(err.message);
  }
});
