function establishSSEConnection(callback) {
    const eventSource = new EventSource('http://localhost:8101/ws/ws1');
    eventSource.onmessage = function (event) {
        // Handle the incoming SSE event
        callback(event.data);
    };
}
