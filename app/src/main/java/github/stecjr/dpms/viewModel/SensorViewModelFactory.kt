package github.stecjr.dpms.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import github.stecjr.dpms.utils.NetworkManager

class SensorViewModelFactory(
    private val ip: String,
    private val port: Int
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SensorViewModel::class.java)) {
            val networkManager = NetworkManager(ip, port)
            return SensorViewModel(networkManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
