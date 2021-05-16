package com.example.weatherapp.domain

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode:Long, date:Long): ForecastList?
    fun requestDayForecast(id: Long): Forecast?
}