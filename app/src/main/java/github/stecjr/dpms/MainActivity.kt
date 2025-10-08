package github.stecjr.dpms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import github.stecjr.dpms.ui.SensorDataScreen
import github.stecjr.dpms.ui.theme.DPMSTheme
import github.stecjr.dpms.viewModel.SensorViewModel
import github.stecjr.dpms.viewModel.SensorViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: SensorViewModel = viewModel(
                factory = SensorViewModelFactory("192.168.5.1", 80)
            )
            val uiState by viewModel.uiState.collectAsState()

            DPMSTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SensorDataScreen(uiState = uiState)
                }
            }
        }
    }
}