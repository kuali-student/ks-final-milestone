<!DOCTYPE HTML>
<html>
<head>
    <script type="text/javascript">
        function WebSocketTest()
        {
            if ("WebSocket" in window)
            {
                alert("WebSocket is supported by your Browser!");
                // Let us open a web socket
                var ws = new WebSocket("ws://localhost:8081/ks-with-rice-bundled/cr-performance-test/crStatus");
                ws.onopen = function()
                {
                    // Web Socket is connected, send data using send()
                    ws.send("Message to send");
                    alert("Message is sent...");
                };
                ws.onmessage = function (evt)
                {
                    var received_msg = evt.data;
                    alert("Message is received..." + received_msg);
                };
                ws.onclose = function()
                {
                    // websocket is closed.
                    alert("Connection is closed...");
                };
            }
            else
            {
                // The browser doesn't support WebSocket
                alert("WebSocket NOT supported by your Browser!");
            }
        }
    </script>
</head>
<body>
<div id="sse">
    <button onclick="WebSocketTest()">Run WebSocket</button>
</div>
</body>
</html>
