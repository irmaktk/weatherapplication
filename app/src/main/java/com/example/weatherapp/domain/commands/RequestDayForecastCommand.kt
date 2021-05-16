package com.example.weatherapp.domain.commands

import com.example.weatherapp.domain.ForecastProvider
import com.example.weatherapp.domain.Forecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RequestDayForecastCommand(
    val id: Long,
    private val forecastProvider: ForecastProvider = ForecastProvider()) :
    Command<Forecast> {

    override suspend fun execute() = withContext(Dispatchers.IO) {
        forecastProvider.requestForecast(id)
    }
}