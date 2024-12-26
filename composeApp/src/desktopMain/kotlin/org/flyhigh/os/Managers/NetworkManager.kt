package org.flyhigh.os.Managers

// NetworkManager.kt


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.DataInputStream
import java.io.IOException
import java.io.PrintWriter
import java.net.Socket


class NetworkManager private constructor() {
    private var socket: Socket? = null
    private var input: DataInputStream? = null
    private var output: PrintWriter? = null

    companion object {
        var ip: String = "192.168.17.85"
        var port: Int = 8111
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
            socket = Socket(ip, port).apply {
                soTimeout = 5000
                tcpNoDelay = true
                keepAlive = true
            }
            input = DataInputStream(socket!!.getInputStream())
            output = PrintWriter(socket!!.getOutputStream(), true)
            println("Connected to $ip:$port")
        } catch (e: Exception) {
            println("Connection failed: ${e.message}")
            throw e
        }
    }

    suspend fun sendMessage(message: String) = withContext(Dispatchers.IO) {
        try {
            output?.println("$message\n")
            output?.flush()
            println("Sent: $message")
        } catch (e: Exception) {
            println("Error sending message: ${e.message}")
            throw e
        }
    }

    suspend fun readMessage(): String? = withContext(Dispatchers.IO) {
        try {
            if (socket?.isConnected == true && !socket?.isClosed!!) {
                val bytes = ByteArray(8192)
                val count = input?.read(bytes)
                if (count != null && count > 0) {
                    val message = String(bytes, 0, count)
                    println("Received: $message")
                    return@withContext message
                }
            }
            null
        } catch (e: Exception) {
            println("Error reading: ${e.message}")
            if (e is IOException) socket?.close()
            null
        }
    }

    fun close() {
        socket?.close()
        input?.close()
        output?.close()
    }
}