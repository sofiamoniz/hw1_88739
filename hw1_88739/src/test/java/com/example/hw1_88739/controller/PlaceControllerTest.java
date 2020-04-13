package com.example.hw1_88739.controller;

import com.example.hw1_88739.entities.AirQuality;
import com.example.hw1_88739.entities.Coordinate;
import com.example.hw1_88739.entities.Weather;
import com.example.hw1_88739.service.AirQualityService;
import com.example.hw1_88739.service.CoordinateService;
import com.example.hw1_88739.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PlaceControllerTest {

    @Autowired
    MockMvc servlet;

    @MockBean
    CoordinateService coordinateService;

    @MockBean
    AirQualityService airQualityService;

    @MockBean
    WeatherService weatherService;

    @Test
    public void whenGetPlace_thenReturnLatitude() throws Exception{
        given(coordinateService.getPlaceCoordinate("Viseu,Portugal")).willReturn(new Coordinate(40.6652423,-7.9161281));
        servlet.perform(MockMvcRequestBuilders.get("/coordinates/Viseu,Portugal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("latitude").value(40.6652423));
    }

    @Test
    public void whenGetPlace_thenReturnLongitude() throws Exception{
        given(coordinateService.getPlaceCoordinate("Viseu,Portugal")).willReturn(new Coordinate(40.6652423,-7.9161281));
        servlet.perform(MockMvcRequestBuilders.get("/coordinates/Viseu,Portugal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("longitude").value(-7.9161281));
    }


    @Test
    public void whenGetPlaceAirQuality_thenReturnPlaceName() throws Exception{
        given(airQualityService.getAirQualityByPlace("Viseu,Portugal")).willReturn(new AirQuality("Viseu,Portugal","Good air quality", "o3"));
        servlet.perform(MockMvcRequestBuilders.get("/airquality/Viseu,Portugal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("place").value("Viseu,Portugal"));
    }

    @Test
    public void whenGetPlaceAirQuality_thenReturnCategory() throws Exception{
        given(airQualityService.getAirQualityByPlace("Viseu,Portugal")).willReturn(new AirQuality("Viseu,Portugal","Good air quality", "o3"));
        servlet.perform(MockMvcRequestBuilders.get("/airquality/Viseu,Portugal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("category").value("Good air quality"));
    }

    @Test
    public void whenGetPlaceAirQuality_thenReturnPollutant() throws Exception{
        given(airQualityService.getAirQualityByPlace("Viseu,Portugal")).willReturn(new AirQuality("Viseu,Portugal","Good air quality", "o3"));
        servlet.perform(MockMvcRequestBuilders.get("/airquality/Viseu,Portugal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("mainPollutant").value("o3"));
    }

    @Test
    public void whenGetPlaceWeather_thenReturnPlaceName() throws Exception{
        //{"place":"Aveiro,Portugal","weather_text":"Overcast","tempVal":17.86,"tempUnits":"C","relative_humidity":55}
        given(weatherService.getWeatherByPlace("Aveiro,Portugal")).willReturn(new Weather("Aveiro,Portugal","Overcast",17.86,"C", (long) 55));
        servlet.perform(MockMvcRequestBuilders.get("/weather/Aveiro,Portugal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("place").value("Aveiro,Portugal"));
    }

    @Test
    public void whenGetPlaceWeather_thenReturnWeatherText() throws Exception{
        //{"place":"Aveiro,Portugal","weather_text":"Overcast","tempVal":17.86,"tempUnits":"C","relative_humidity":55}
        given(weatherService.getWeatherByPlace("Aveiro,Portugal")).willReturn(new Weather("Aveiro,Portugal","Overcast",17.86,"C", (long) 55));
        servlet.perform(MockMvcRequestBuilders.get("/weather/Aveiro,Portugal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("weather_text").value("Overcast"));
    }

    @Test
    public void whenGetPlaceWeather_thenReturntempVal() throws Exception{
        //{"place":"Aveiro,Portugal","weather_text":"Overcast","tempVal":17.86,"tempUnits":"C","relative_humidity":55}
        given(weatherService.getWeatherByPlace("Aveiro,Portugal")).willReturn(new Weather("Aveiro,Portugal","Overcast",17.86,"C", (long) 55));
        servlet.perform(MockMvcRequestBuilders.get("/weather/Aveiro,Portugal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("tempVal").value(17.86));
    }

    @Test
    public void whenGetPlaceWeather_thenReturntempUnit() throws Exception{
        //{"place":"Aveiro,Portugal","weather_text":"Overcast","tempVal":17.86,"tempUnits":"C","relative_humidity":55}
        given(weatherService.getWeatherByPlace("Aveiro,Portugal")).willReturn(new Weather("Aveiro,Portugal","Overcast",17.86,"C", (long) 55));
        servlet.perform(MockMvcRequestBuilders.get("/weather/Aveiro,Portugal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("tempUnits").value("C"));
    }

    @Test
    public void whenGetPlaceWeather_thenReturnRelHum() throws Exception{
        //{"place":"Aveiro,Portugal","weather_text":"Overcast","tempVal":17.86,"tempUnits":"C","relative_humidity":55}
        given(weatherService.getWeatherByPlace("Aveiro,Portugal")).willReturn(new Weather("Aveiro,Portugal","Overcast",17.86,"C", (long) 55));
        servlet.perform(MockMvcRequestBuilders.get("/weather/Aveiro,Portugal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("relative_humidity").value(55));
    }


}