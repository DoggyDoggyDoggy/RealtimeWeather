package denys.diomaxius.realtimeweather.api

data class WeatherModel(
    val current: Current,
    val location: Location
)