"use strict";
app.controller("MainController", function ($scope, SocketClientService) {
  $scope.onlineUsers = [];
  $scope.chatWith = null;
  $scope.messages = [];
  $scope.chatInput = "";
  $scope.typings = [];
  $scope.isTyping = false;
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
  $scope.sendMessage = function () {
    let destination = "/app/chat.message";
    if ($scope.chatWith) {
      destination = "/app/chat.private." + $scope.chatWith;
      $scope.messages.push({
        fromUsername: "You",
        timeSent: new Date().toISOString(),
        message: $scope.chatInput,
      });
    }

    SocketClientService.sendMessage(
      destination,
      null,
      JSON.stringify({
        message: $scope.chatInput,
        fromUsername: currentUser.username,
        timeSent: new Date(),
      })
    );
    $scope.chatInput = "";
  };
  $scope.startTyping = function () {
    if ($scope.isTyping) return;
    $scope.isTyping = true;
    const username = currentUser.username;
    console.log(username);
    SocketClientService.sendMessage(
      "/topic/chat.typing",
      null,
      JSON.stringify({ username, time: new Date() })
    );
    setTimeout(() => {
      $scope.stopTyping();
    }, 500);
  };
  $scope.stopTyping = function () {
    $scope.isTyping = false;
    const username = currentUser.username;
    SocketClientService.sendMessage(
      "/topic/chat.typing",
      null,
      JSON.stringify({ username, time: new Date() })
    );
  };
  function connectedToSocket() {
    SocketClientService.subscribe("/app/chat.users-online", (message) => {
      $scope.onlineUsers = JSON.parse(message.body);
      console.log(JSON.parse(message.body));
      $scope.$apply();
    });
    SocketClientService.subscribe("/topic/chat.login", (loginEvent) => {
      $scope.onlineUsers.push(JSON.parse(loginEvent.body));
      $scope.$apply();
    });
    SocketClientService.subscribe("/topic/chat.logout", (logoutEvent) => {
      const username = JSON.parse(logoutEvent.body).username;
      for (let index in $scope.onlineUsers) {
        if ($scope.onlineUsers[index].username == username) {
          $scope.onlineUsers.splice(index, 1);
        }
      }
    });
    SocketClientService.subscribe("/topic/chat.typing", (response) => {
      const username = JSON.parse(response.body).username;
      for (const user of $scope.onlineUsers) {
        if (
          user.user.username === username &&
          user.user.username != currentUser.username
        ) {
          user["isTyping"] = !user?.isTyping;
          const userTyping = $scope.typings.find(
            (ele) => ele.username === user.user.username
          );
          if (userTyping) {
            $scope.typings = $scope.typings.filter(
              (ele) => ele.username !== userTyping.username
            );
          } else {
            $scope.typings.push(user.user);
          }
        }
      }
    });
    SocketClientService.subscribe("/topic/chat.message", (response) => {
      console.log(response);
      $scope.messages.push(JSON.parse(response.body));
    });
    SocketClientService.subscribe(
      "/user/exchange/amq.direct/chat.message",
      (response) => {
        console.log(response);
        $scope.messages = [];
        $scope.messages.push(JSON.parse(response.body));
      }
    );
  }
  function handleFailureSocketConnection(err) {
    alert(err.message);
  }
});
