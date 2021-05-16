package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import com.example.weatherapp.domain.commands.RequestDayForecastCommand
import com.example.weatherapp.domain.Forecast
import com.example.weatherapp.ui.utils.color
import com.example.weatherapp.ui.utils.textColor
import com.example.weatherapp.ui.utils.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.DateFormat
import android.support.v7.widget.Toolbar
import org.jetbrains.anko.find
import com.example.weatherapp.R
import kotlinx.coroutines.launch
//
class DetailActivity : CoroutineScopeActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object {
        const val ID = "DetailActivity:id"
        const val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()

        toolbarTitle = intent.getStringExtra(CITY_NAME).toString()

        enableHomeAsUp { onBackPressed() }

        launch {
            val id = intent.getLongExtra(ID, -1)
            val result = RequestDayForecastCommand(id).execute()
            bindForecast(result)
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.with(this@DetailActivity).load(iconUrl).into(icon)
        toolbar.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    @SuppressLint("SetTextI18n")
    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}º"
        it.second.textColor = color(when (it.first) {
            in -50..0 -> android.R.color.holo_purple
            in 0..15 -> android.R.color.holo_red_light
            else -> android.R.color.holo_blue_light
        })
    }
}