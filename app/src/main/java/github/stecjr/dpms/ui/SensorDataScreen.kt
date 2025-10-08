package github.stecjr.dpms.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import github.stecjr.dpms.R
import github.stecjr.dpms.model.ConnectionState
import github.stecjr.dpms.ui.theme.DPMSTheme
import github.stecjr.dpms.viewModel.SensorViewModel
import github.stecjr.dpms.viewModel.SensorViewModelFactory

@Composable
fun SensorDataScreen(
    uiState: SensorViewModel.UIState
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val connectionState = uiState.connectionState
        val sensorData = uiState.sensorData

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "DPMS Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = "DPMS",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (connectionState != ConnectionState.Connected) {
            Spacer(Modifier.height(10.dp))

            Surface(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "Connection Status: Disconnected",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onError,
                    modifier = Modifier
                        .wrapContentSize()
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(10.dp),
                )
            }
        }

        Spacer(Modifier.height(50.dp))

        SensorDataItem(
            title = "Temperature",
            value = sensorData.temperature?.toString() ?: "--.-",
            unit = "°C",
            icon = painterResource(id = R.drawable.temperature),
            iconTint = MaterialTheme.colorScheme.onTertiaryContainer
        )

        SensorDataItem(
            title = "Humidity",
            value = sensorData.humidity?.toString() ?: "--.-",
            unit = "%",
            icon = painterResource(id = R.drawable.humidity),
            iconTint = MaterialTheme.colorScheme.onTertiaryContainer
        )

        SensorDataItem(
            title = "Moisture",
            value = sensorData.moisture?.toString() ?: "--",
            unit = "%",
            icon = painterResource(id = R.drawable.moisture),
            iconTint = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

@Composable
fun SensorDataItem(
    title: String,
    value: String,
    unit: String,
    icon: Painter,
    iconTint: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.35f),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier
                        .size(48.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.65f)
                    .padding(start = 30.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    fontSize = 16.sp,
                )

                Spacer(Modifier.height(5.dp))

                Row {
                    Text(
                        text = value,
                        color = iconTint,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 32.sp,
                    )

                    Spacer(Modifier.width(15.dp))

                    Text(
                        text = unit,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SensorDataScreenPreview() {
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