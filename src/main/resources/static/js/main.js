const stompClient = Stomp.over(new SockJS("/chat-websocket"))
console.log(stompClient);
console.log(currentUser)
stompClient.connect({}, (frame) => {
    alert(`Welcome ${currentUser.fullName} to chat!`);
    stompClient.subscribe("/app/chat.users-online", (message) => {
        const onlineUsers = message.body
        console.log(onlineUsers)
    })
},(error) => {
    alert(error.message)
})