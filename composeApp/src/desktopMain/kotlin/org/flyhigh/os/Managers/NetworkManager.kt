package org.flyhigh.os.Managers

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.serialization.json.Json
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.encodeToString

//class NetworkManager private constructor() {
//    private var socket: Socket? = null
//    private var writer: PrintWriter? = null
//    private var reader: BufferedReader? = null
//    private val isConnected = AtomicBoolean(false)
//    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
//
//    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
//    val connectionState: StateFlow<ConnectionState> = _connectionState
//
//    private val _receivedMessages = MutableSharedFlow<String>()
//    val receivedMessages: SharedFlow<String> = _receivedMessages
//
//    companion object {
//        private const val HOST = "192.168.17.200"
//        private const val PORT = 65432
//
//        @Volatile
//        private var instance: NetworkManager? = null
//
//        fun getInstance(): NetworkManager {
//            return instance ?: synchronized(this) {
//                instance ?: NetworkManager().also { instance = it }
//            }
//        }
//    }
//
//    fun connect() {
//        try {
//            socket = Socket(HOST, PORT)
//            writer = PrintWriter(socket!!.getOutputStream(), true)
//            reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
//            isConnected.set(true)
//            _connectionState.value = ConnectionState.Connected
//        } catch (e: Exception) {
//            _connectionState.value = ConnectionState.Error(e.message ?: "Unknown error")
//            cleanup()
//        }
//    }
//
//    suspend fun sendMessage(message: String): String = withContext(Dispatchers.IO) {
//        try {
//            if (!isConnected.get()) throw IllegalStateException("Not connected to server")
//
//            // Send the message to the server
//            writer?.println(message)
//
//            // Immediately wait for the response
//            val response = reader?.readLine() ?: throw Exception("No response from server")
//            println("Received response: $response") // Debug print
//            return@withContext response
//        } catch (e: Exception) {
//            _connectionState.value = ConnectionState.Error(e.message ?: "Unknown error")
//            throw e
//        }
//    }
//
//    private fun cleanup() {
//        writer?.close()
//        reader?.close()
//        socket?.close()
//        isConnected.set(false)
//        _connectionState.value = ConnectionState.Disconnected
//    }
//
//    fun disconnect() {
//        cleanup()
//        scope.cancel()
//    }
//}
//
//sealed class ConnectionState {
//    object Connected : ConnectionState()
//    object Disconnected : ConnectionState()
//    data class Error(val message: String) : ConnectionState()
//}


import kotlinx.coroutines.*
import java.io.*
import java.net.*

class NetworkManager private constructor() {

    private var socket: Socket? = null
    private var input: BufferedReader? = null
    private var output: PrintWriter? = null

    companion object {
        var ip: String = "192.168.17.200"
        var port: Int = 8101

        @Volatile
        private var INSTANCE: NetworkManager? = null

        fun getInstance(): NetworkManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NetworkManager().also { INSTANCE = it }
            }
        }
    }

    suspend fun connect() = withContext(Dispatchers.IO) {
        try {
            socket = Socket(ip, port)
            input = BufferedReader(InputStreamReader(socket!!.getInputStream()))
            output = PrintWriter(socket!!.getOutputStream(), true)
            println("Connected to $ip:$port")
        } catch (e: Exception) {
            println("Connection failed: ${e.message}")
        }
    }

    suspend fun sendMessage(message: String) = withContext(Dispatchers.IO) {
        try {
            output?.println(message)
        } catch (e: Exception) {
            println("Error sending message: ${e.message}")
        }
    }

    suspend fun readMessage(): String? = withContext(Dispatchers.IO) {
        println("Reading message")
        return@withContext try {
            input?.readLine()
        } catch (e: IOException) {
            println("Error reading message: ${e.message}")
            null
        }
    }

    fun close() {
        try {
            socket?.close()
            println("Connection closed")
        } catch (e: Exception) {
            println("Error closing connection: ${e.message}")
        }
    }
}