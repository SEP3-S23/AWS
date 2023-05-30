window.sse = {
    eventSource: null,
    setupSseConnection: function (dotNetRef, url) {
        window.sse.disconnectConnection()
        
        window.sse.eventSource = new EventSource(url);
        
        window.sse.eventSource.onmessage = function (event) {
            dotNetRef.invokeMethodAsync('HandleSseMessage', event.data);
        };
        window.sse.eventSource.onerror = function () {
            dotNetRef.invokeMethodAsync('HandleSseError');
        };
        
    },
    disconnectConnection: function () {
        if (window.sse.eventSource !== null) {
            window.sse.eventSource.close();
            window.sse.eventSource = null;
        }
    },
};