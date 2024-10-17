package net.thebookofcode.websocketimplementation

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketClient : WebSocketListener() {
    private lateinit var webSocket: WebSocket

    fun connect() {
        val client = OkHttpClient()

        val request = Request.Builder()
            //.url("ws://your-websocket-url")
            .url("wss://demo.piesocket.com/v3/$CHANNEL_ID?api_key=$DEMO_API_KEY&notify_self")
            .build()

        webSocket = client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        // Connection established
        webSocket.send("Hello, WebSocket!")
        Log.d(TAG, "CONNECTED")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        // Message received from the server
        Log.d(TAG, "Received: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        // Handle binary data
        Log.d(TAG, "Received: $bytes")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        webSocket.close(NORMAL_CLOSING_STATUS, null)
        // WebSocket is closing
        Log.d(TAG, "Closing : $code / $reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d(TAG, "Closed : $code / $reason")
    }


    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        // Error handling
        Log.d(TAG, "Failure : ${t.message}")
    }

    companion object {
        private const val TAG = "WEBSOCKET"

        private const val NORMAL_CLOSING_STATUS = 1000

        // THIS IS A FAKE KEY AND CHANNEL ID

        private const val DEMO_API_KEY = "VCXCEuvhGcBDP7XhiJJUDvR1e1D3eiVjgZ9VRiaV"

        private const val CHANNEL_ID = "channel_123"
    }
}