package com.singhtwenty2.konix.feature_home.presentation.component

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.singhtwenty2.konix.core.ui.theme.RED
import com.singhtwenty2.konix.core.ui.theme.ZERODHA_DARK
import com.singhtwenty2.konix.feature_home.domain.model.StockPriceResponse
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StockChartComposable(
    modifier: Modifier = Modifier,
    stockPrices: List<StockPriceResponse>
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val uiColor = if (isSystemInDarkTheme()) ZERODHA_DARK else MaterialTheme.colorScheme.background
    val digitColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(uiColor.hashCode())
                description.isEnabled = false
                setDrawGridBackground(false)

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    textColor = digitColor.hashCode()
                    setDrawGridLines(false)
                    setAvoidFirstLastClipping(true)
                    valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            val epochMillis = value.toLong() * 1000
                            val date = Date(epochMillis)
                            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                            return sdf.format(date)
                        }
                    }
                }

                axisLeft.apply {
                    textColor = digitColor.hashCode()
                    setDrawGridLines(false)
                }
                axisRight.isEnabled = false

                legend.textColor = digitColor.hashCode()
            }
        },
        update = { lineChart ->
            if (stockPrices.isEmpty()) {
                Log.d("StockChartComposable", "No stock data available")
                return@AndroidView
            }

            val entries = mutableListOf<Entry>()
            try {
                var previousPrice: Float? = null
                var isIncreasing = true
                stockPrices.forEachIndexed { index, stockPrice ->
                    val dateTime = LocalDateTime.parse(stockPrice.timeStamp, formatter)
                    val timeFloat = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond().toFloat()

                    if (timeFloat >= 0) {
                        entries.add(Entry(timeFloat, stockPrice.price.toFloat()))
                        Log.d("StockChartComposable", "Parsed time: $timeFloat, Price: ${stockPrice.price}")

                        if (previousPrice != null && stockPrice.price.toFloat() < previousPrice!!) {
                            isIncreasing = false
                        }
                        previousPrice = stockPrice.price.toFloat()
                    } else {
                        Log.e("StockChartComposable", "Invalid time value: $timeFloat for timestamp: ${stockPrice.timeStamp}")
                    }
                }

                val lineColor = if (isIncreasing) Color.GREEN else RED.hashCode()
                val dataSet = LineDataSet(entries, "Stock Prices").apply {
                    color = lineColor
                    valueTextColor = Color.BLACK
                    lineWidth = 2f
                    setDrawCircles(true)
                    setDrawValues(true)
                }

                val lineData = LineData(dataSet)
                lineChart.data = lineData
                lineChart.invalidate()
            } catch (e: Exception) {
                Log.e("StockChartComposable", "Error updating chart data", e)
            }
        },
        modifier = modifier.height(770.dp)
    )
}
