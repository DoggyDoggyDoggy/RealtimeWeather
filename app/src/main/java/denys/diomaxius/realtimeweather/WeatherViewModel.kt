package denys.diomaxius.realtimeweather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import denys.diomaxius.realtimeweather.api.Constant
import denys.diomaxius.realtimeweather.api.NetworkResponse
import denys.diomaxius.realtimeweather.api.RetrofitInstance
import denys.diomaxius.realtimeweather.api.WeatherModel
import kotlinx.coroutines.launch
import java.lang.Exception

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try{
                val response = weatherApi.getWeather(
                    apikey = Constant.apiKey,
                    city = city
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }
}