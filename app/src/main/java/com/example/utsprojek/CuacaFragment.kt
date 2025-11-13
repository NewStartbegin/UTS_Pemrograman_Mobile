package com.example.utsprojek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CuacaFragment : Fragment() {

    private lateinit var tvTemperature: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWindSpeed: TextView
    private lateinit var tvPressure: TextView
    private lateinit var tvFeelsLike: TextView

    private lateinit var tvLocation: TextView
    private lateinit var tvUvIndex: TextView
    private lateinit var btnNext: ImageButton
    private lateinit var hourlyForecastContainer: LinearLayout
    private lateinit var ivWeatherIcon: ImageView

    private var currentLocationIndex = 0

    data class WeatherLocation(
        val city: String,
        val country: String,
        val temperature: String,
        val condition: String,
        val conditionType: String,
        val humidity: String,
        val windSpeed: String,
        val pressure: String,
        val feelsLike: String,
        val uvIndex: String,
        val hourlyForecast: List<HourlyWeather>
    )
    data class HourlyWeather(
        val time: String,
        val temperature: String,
        val conditionType: String
    )

    private val weatherData = listOf(
        WeatherLocation(
            city = "Bandung",
            country = "Indonesia",
            temperature = "22°",
            condition = "Hujan",
            conditionType = "rain",
            humidity = "85%",
            windSpeed = "15 km/h",
            pressure = "1010 hPa",
            feelsLike = "23°C",
            uvIndex = "2 (Rendah)",
            hourlyForecast = listOf(
                HourlyWeather("08:00", "22°", "rain"),
                HourlyWeather("09:00", "23°", "rain"),
                HourlyWeather("10:00", "24°", "cloudy"),
                HourlyWeather("11:00", "25°", "cloudy"),
                HourlyWeather("12:00", "26°", "cloudy"),
                HourlyWeather("13:00", "26°", "rain"),
                HourlyWeather("14:00", "25°", "rain"),
                HourlyWeather("15:00", "24°", "rain"),
                HourlyWeather("16:00", "23°", "cloudy"),
                HourlyWeather("17:00", "22°", "cloudy"),
                HourlyWeather("18:00", "21°", "rain")
            )
        ),
        WeatherLocation(
            city = "Tokyo",
            country = "Jepang",
            temperature = "18°",
            condition = "Cerah",
            conditionType = "sunny",
            humidity = "45%",
            windSpeed = "8 km/h",
            pressure = "1015 hPa",
            feelsLike = "17°C",
            uvIndex = "6 (Tinggi)",
            hourlyForecast = listOf(
                HourlyWeather("08:00", "16°", "sunny"),
                HourlyWeather("09:00", "17°", "sunny"),
                HourlyWeather("10:00", "18°", "sunny"),
                HourlyWeather("11:00", "19°", "sunny"),
                HourlyWeather("12:00", "20°", "sunny"),
                HourlyWeather("13:00", "21°", "sunny"),
                HourlyWeather("14:00", "21°", "cloudy"),
                HourlyWeather("15:00", "20°", "cloudy"),
                HourlyWeather("16:00", "19°", "cloudy"),
                HourlyWeather("17:00", "18°", "cloudy"),
                HourlyWeather("18:00", "17°", "cloudy")
            )
        ),
        WeatherLocation(
            city = "Beijing",
            country = "China",
            temperature = "14°",
            condition = "Hujan Badai",
            conditionType = "stormrain",
            humidity = "90%",
            windSpeed = "35 km/h",
            pressure = "1005 hPa",
            feelsLike = "11°C",
            uvIndex = "1 (Rendah)",
            hourlyForecast = listOf(
                HourlyWeather("08:00", "12°", "stormrain"),
                HourlyWeather("09:00", "13°", "stormrain"),
                HourlyWeather("10:00", "14°", "stormrain"),
                HourlyWeather("11:00", "14°", "stormrain"),
                HourlyWeather("12:00", "15°", "rain"),
                HourlyWeather("13:00", "15°", "rain"),
                HourlyWeather("14:00", "15°", "rain"),
                HourlyWeather("15:00", "14°", "stormrain"),
                HourlyWeather("16:00", "13°", "stormrain"),
                HourlyWeather("17:00", "12°", "stormrain"),
                HourlyWeather("18:00", "11°", "stormrain")
            )
        ),
        WeatherLocation(
            city = "Bern",
            country = "Swiss",
            temperature = "-2°",
            condition = "Bersalju",
            conditionType = "snow",
            humidity = "75%",
            windSpeed = "18 km/h",
            pressure = "1020 hPa",
            feelsLike = "-5°C",
            uvIndex = "1 (Rendah)",
            hourlyForecast = listOf(
                HourlyWeather("08:00", "-4°", "snow"),
                HourlyWeather("09:00", "-3°", "snow"),
                HourlyWeather("10:00", "-2°", "snow"),
                HourlyWeather("11:00", "-1°", "snow"),
                HourlyWeather("12:00", "0°", "snow"),
                HourlyWeather("13:00", "0°", "snow"),
                HourlyWeather("14:00", "1°", "snow"),
                HourlyWeather("15:00", "0°", "snow"),
                HourlyWeather("16:00", "-1°", "snow"),
                HourlyWeather("17:00", "-2°", "snow"),
                HourlyWeather("18:00", "-3°", "snow")
            )
        ),
        WeatherLocation(
            city = "Berlin",
            country = "Jerman",
            temperature = "12°",
            condition = "Berawan",
            conditionType = "cloudy",
            humidity = "60%",
            windSpeed = "10 km/h",
            pressure = "1013 hPa",
            feelsLike = "11°C",
            uvIndex = "3 (Sedang)",
            hourlyForecast = listOf(
                HourlyWeather("08:00", "10°", "cloudy"),
                HourlyWeather("09:00", "11°", "cloudy"),
                HourlyWeather("10:00", "12°", "cloudy"),
                HourlyWeather("11:00", "13°", "sunny"),
                HourlyWeather("12:00", "14°", "sunny"),
                HourlyWeather("13:00", "14°", "sunny"),
                HourlyWeather("14:00", "14°", "cloudy"),
                HourlyWeather("15:00", "13°", "cloudy"),
                HourlyWeather("16:00", "12°", "cloudy"),
                HourlyWeather("17:00", "11°", "cloudy"),
                HourlyWeather("18:00", "10°", "cloudy")
            )
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cuaca_layout, container, false)
        initializeViews(view)
        setupBottomNavigation(view)
        setupWeatherData()
        setupNextButton()
        return view
    }

    private fun initializeViews(view: View) {
        tvTemperature = view.findViewById(R.id.tvTemperature)
        tvDate = view.findViewById(R.id.tvDate)
        tvCondition = view.findViewById(R.id.tvCondition)
        tvHumidity = view.findViewById(R.id.tvHumidity)
        tvWindSpeed = view.findViewById(R.id.tvWindSpeed)
        tvPressure = view.findViewById(R.id.tvPressure)
        tvFeelsLike = view.findViewById(R.id.tvFeelsLike)
        tvUvIndex = view.findViewById(R.id.tvUvIndex)
        tvLocation = view.findViewById(R.id.tvLocation)
        btnNext = view.findViewById(R.id.btnNext)
        ivWeatherIcon = view.findViewById(R.id.ivWeatherIcon)
        hourlyForecastContainer = view.findViewById(R.id.hourlyForecastContainer)
    }

    private fun setupWeatherData() {
        val weather = weatherData[currentLocationIndex]
        tvTemperature.text = weather.temperature
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
        tvDate.text = dateFormat.format(Date())
        tvCondition.text = weather.condition
        tvLocation.text = "${weather.city}, ${weather.country}"
        tvHumidity.text = weather.humidity
        tvWindSpeed.text = weather.windSpeed
        tvPressure.text = weather.pressure
        tvFeelsLike.text = weather.feelsLike
        tvUvIndex.text = weather.uvIndex

        setWeatherIcon(weather.conditionType)
        updateHourlyForecast(weather.hourlyForecast)
    }

    private fun setWeatherIcon(conditionType: String) {
        val iconResId = when (conditionType) {
            "rain" -> R.drawable.rain
            "sunny" -> R.drawable.sunny
            "stormrain" -> R.drawable.stromrain
            "snow" -> R.drawable.snow
            "cloudy" -> R.drawable.cloudy
            else -> R.drawable.cloudy // default
        }
        ivWeatherIcon.setImageResource(iconResId)
    }

    private fun setupNextButton() {
        btnNext.setOnClickListener {
            currentLocationIndex = (currentLocationIndex + 1) % weatherData.size
            setupWeatherData()
        }
    }

    private fun updateHourlyForecast(hourlyData: List<HourlyWeather>) {
        hourlyForecastContainer.removeAllViews()

        hourlyData.forEach { hourly ->
            val cardView = layoutInflater.inflate(
                R.layout.cuaca_perjam,
                hourlyForecastContainer,
                false
            )

            cardView.findViewById<TextView>(R.id.tvHourlyTime).text = hourly.time
            cardView.findViewById<TextView>(R.id.tvHourlyTemp).text = hourly.temperature

            val iconResId = when (hourly.conditionType) {
                "rain" -> R.drawable.rain
                "sunny" -> R.drawable.sunny
                "stormrain" -> R.drawable.stromrain
                "snow" -> R.drawable.snow
                "cloudy" -> R.drawable.cloudy
                else -> R.drawable.cloudy
            }
            cardView.findViewById<ImageView>(R.id.ivHourlyIcon).setImageResource(iconResId)

            hourlyForecastContainer.addView(cardView)
        }
    }

    private fun setupBottomNavigation(view: View) {
        view.findViewById<View>(R.id.nav_contact)?.setOnClickListener {
            navigateToFragment(ContactFragment())
        }
        view.findViewById<View>(R.id.nav_news)?.setOnClickListener {
            navigateToFragment(NewsFragment())
        }
        view.findViewById<View>(R.id.nav_biodata)?.setOnClickListener {
            navigateToFragment(BiodataFragment())
        }
        view.findViewById<View>(R.id.nav_calculator)?.setOnClickListener {
            navigateToFragment(CalculatorFragment())
        }
        highlightCurrentNav(view, R.id.nav_cuaca)
    }

    private fun highlightCurrentNav(view: View, activeNavId: Int) {
        val navItems = listOf(
            R.id.nav_contact,
            R.id.nav_news,
            R.id.nav_biodata,
            R.id.nav_cuaca,
            R.id.nav_calculator
        )
        navItems.forEach { id ->
            val item = view.findViewById<View>(id)
            item?.alpha = if (id == activeNavId) 1.0f else 0.5f
        }
    }
    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() = CuacaFragment()
    }
}