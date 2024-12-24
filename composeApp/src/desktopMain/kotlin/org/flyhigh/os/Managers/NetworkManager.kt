package org.flyhigh.os.Managers

// NetworkManager.kt

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean

class NetworkManager private constructor() {
    private var socket: Socket? = null
    private var writer: PrintWriter? = null
    private var reader: BufferedReader? = null
    private val isConnected = AtomicBoolean(false)
    private var connectionJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState

    companion object {
        private const val HOST = "localhost"
        private const val PORT = 8192
        private const val RECONNECT_DELAY = 5000L // 5 seconds

        @Volatile
        private var instance: NetworkManager? = null

        fun getInstance(): NetworkManager {
            return instance ?: synchronized(this) {
                instance ?: NetworkManager().also { instance = it }
            }
        }
    }

    fun startConnection() {
        if (connectionJob?.isActive == true) return

        connectionJob = scope.launch {
            while (isActive) {
                try {
                    if (!isConnected.get()) {
                        _connectionState.value = ConnectionState.Connecting
                        socket = Socket(HOST, PORT)
                        writer = PrintWriter(socket!!.getOutputStream(), true)
                        reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                        isConnected.set(true)
                        _connectionState.value = ConnectionState.Connected

                        // Start heartbeat
                        startHeartbeat()
                    }
                    // Keep the connection alive
                    print("Connected")
                    maintainConnection()
                } catch (e: Exception) {
                    _connectionState.value = ConnectionState.Error(e.message ?: "Unknown error")
                    isConnected.set(false)
                    cleanup()
                    delay(RECONNECT_DELAY)
                }
            }
        }
    }

    private suspend fun maintainConnection() {
        try {
            while (isConnected.get() && socket?.isConnected == true) {
                // Wait for any incoming messages
                reader?.readLine()?.let { message ->
                    handleIncomingMessage(message)
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private fun startHeartbeat() {
        scope.launch {
            while (isConnected.get()) {
                try {
                    sendMessage("HEARTBEAT")
                    delay(30000) // 30 seconds
                } catch (e: Exception) {
                    break
                }
            }
        }
    }

    private fun handleIncomingMessage(message: String) {
        // Handle different types of messages
        when {
            message.startsWith("HEARTBEAT_ACK") -> { /* Handle heartbeat */ }
            message.startsWith("SERVER_MESSAGE") -> { /* Handle server messages */ }
            // Add more message handlers as needed
        }
    }

    suspend fun sendMessage(message: String): String = withContext(Dispatchers.IO) {
        try {
            if (!isConnected.get()) {
                throw IllegalStateException("Not connected to server")
            }

            writer?.println(message)
            return@withContext reader?.readLine() ?: throw Exception("No response from server")
        } catch (e: Exception) {
            _connectionState.value = ConnectionState.Error(e.message ?: "Unknown error")
            throw e
        }
    }
    private fun cleanup() {
        try {
            writer?.close()
            reader?.close()
            socket?.close()
        } catch (e: Exception) {
            println("Cleanup error: ${e.message}")
        }
    }

    fun disconnect() {
        connectionJob?.cancel()
        cleanup()
        isConnected.set(false)
        _connectionState.value = ConnectionState.Disconnected
        scope.cancel()
    }
}

sealed class ConnectionState {
    object Connected : ConnectionState()
    object Connecting : ConnectionState()
    object Disconnected : ConnectionState()
    data class Error(val message: String) : ConnectionState()
}