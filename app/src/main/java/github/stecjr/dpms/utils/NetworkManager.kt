package github.stecjr.dpms.utils

import android.util.Log
import github.stecjr.dpms.model.ConnectionState
import github.stecjr.dpms.model.SensorData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.ConnectException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException
import java.util.regex.Pattern

class NetworkManager(private val ip: String, private val port: Int) {
    private var socket: Socket? = null
    private var bufferReader: BufferedReader? = null
    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()
    private val _sensorData = MutableStateFlow(SensorData())
    val sensorData: StateFlow<SensorData> = _sensorData.asStateFlow()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var autoJob: Job? = null

    fun startAutoReconnect(
        pollIntervalMs: Long = 1_500L,
        connectTimeoutMs: Int = 2_000,
        readTimeoutMs: Int = 15_000
    ) {
        if (autoJob?.isActive == true) return

        autoJob = scope.launch {
            while (isActive) {
                if (_connectionState.value == ConnectionState.Connected || _connectionState.value == ConnectionState.Connecting) {
                    delay(pollIntervalMs)
                    continue
                }

                _connectionState.value = ConnectionState.Connecting
                val s = openSocketIfNotRefused(connectTimeoutMs)
                if (s != null) {
                    try {
                        s.soTimeout = readTimeoutMs
                        socket = s
                        bufferReader = BufferedReader(InputStreamReader(s.getInputStream()))
                        _connectionState.value = ConnectionState.Connected
                        readData()
                    } catch (e: Exception) {
                        Log.e("NetworkManager", "Read failed: ${e.message}", e)
                    } finally {
                        close()
                    }
                } else {
                    if (_connectionState.value != ConnectionState.Disconnected) {
                        _connectionState.value = ConnectionState.Disconnected
                    }
                    delay(pollIntervalMs)
                }
            }
        }
    }

    private suspend fun openSocketIfNotRefused(connectTimeoutMs: Int): Socket? =
        withContext(Dispatchers.IO) {
            try {
                val s = Socket()
                s.connect(InetSocketAddress(ip, port), connectTimeoutMs)
                s
            } catch (e: ConnectException) {
                // Connection actively refused by server/OS
                Log.w("NetworkManager", "Connection refused: ${e.message}")
                _connectionState.value = ConnectionState.Disconnected
                null
            } catch (e: SocketTimeoutException) {
                // Not refused, just unreachable/slow -> keep polling
                Log.w("NetworkManager", "Connect timeout: ${e.message}")
                null
            } catch (e: IOException) {
                Log.w("NetworkManager", "Connect error: ${e.message}")
                null
            }
        }

    fun stopAutoReconnect() {
        autoJob?.cancel()
        autoJob = null
        close()
    }

    private fun close() {
        try {
            bufferReader?.close()
            socket?.close()
        } catch (e: IOException) {
            Log.e("NetworkManager", "Close error: ${e.message}", e)
            e.printStackTrace()
        } finally {
            bufferReader = null
            socket = null
            _connectionState.value = ConnectionState.Disconnected
        }
    }

    private suspend fun readData() {
        withContext(Dispatchers.IO) {
            while (true) {
                try {
                    val data = bufferReader?.readLine()
                        ?: throw IOException("Stream closed by peer")
                    parseData(data)
                } catch (e: IOException) {
                    throw e
                    /*e.printStackTrace()
                    close()
                    break*/
                }
            }
        }
    }

    private fun parseData(data: String) {
        val tempHumidPattern = Pattern.compile("t=th,t=(\\d+\\.?\\d+),h=(\\d+\\.?\\d+)")
        val moisturePattern = Pattern.compile("t=m,m=(\\d+)")

        when {
            data.startsWith("t=th") -> {
                val matcher = tempHumidPattern.matcher(data)
                if (matcher.find()) {
                    val temp = matcher.group(1)?.toFloatOrNull()
                    val humid = matcher.group(2)?.toFloatOrNull()
                    _sensorData.value = _sensorData.value.copy(temperature = temp, humidity = humid)
                }
            }

            data.startsWith("t=m") -> {
                val matcher = moisturePattern.matcher(data)
                if (matcher.find()) {
                    val moisture = matcher.group(1)?.toIntOrNull()
                    _sensorData.value = _sensorData.value.copy(moisture = moisture)
                }
            }
        }
    }
}