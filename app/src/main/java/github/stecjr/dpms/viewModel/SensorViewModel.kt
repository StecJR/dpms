package github.stecjr.dpms.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.stecjr.dpms.model.ConnectionState
import github.stecjr.dpms.model.SensorData
import github.stecjr.dpms.utils.NetworkManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SensorViewModel(private val networkManager: NetworkManager) : ViewModel() {
    val uiState: StateFlow<UIState> = combine(
        networkManager.connectionState,
        networkManager.sensorData
    ) { connectionState, sensorData ->
        UIState(connectionState, sensorData)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UIState(ConnectionState.Disconnected, SensorData())
    )

    init {
        networkManager.startAutoReconnect(
            pollIntervalMs = 1_500L,
            connectTimeoutMs = 2_000,
            readTimeoutMs = 15_000
        )
    }

    override fun onCleared() {
        super.onCleared()
        networkManager.stopAutoReconnect()
    }

    data class UIState(
        val connectionState: ConnectionState,
        val sensorData: SensorData
    )
}