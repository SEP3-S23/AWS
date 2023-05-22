window.sse = {
    setupSseConnection: function (dotNetRef, url) {
        var eventSource = new EventSource(url);

        eventSource.onmessage = function (event) {
            dotNetRef.invokeMethodAsync('HandleSseMessage', event.data);
        };

        eventSource.onerror = function () {
            dotNetRef.invokeMethodAsync('HandleSseError');
        };
    }
};