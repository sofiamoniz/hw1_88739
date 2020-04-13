package com.example.hw1_88739.controller;

import com.example.hw1_88739.cache.Cache;
import com.example.hw1_88739.cache.Statistics;
import com.example.hw1_88739.entities.AirQuality;
import com.example.hw1_88739.entities.Weather;
import com.example.hw1_88739.service.AirQualityService;
import com.example.hw1_88739.service.CoordinateService;
import com.example.hw1_88739.entities.Coordinate;
import com.example.hw1_88739.service.WeatherService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@RestController
public class PlaceController {
    //https://api.opencagedata.com/geocode/v1/json?q=Viseu&key=78ff705db7084e11a44cf27de9d2bd4e
    String coordApiKey = "78ff705db7084e11a44cf27de9d2bd4e";

    String breezoApiKey = "ddef08e638fa4ec298309afb27d68125"; //valido ate 27 de abril

    String language = "&language=en&pretty=1";
    String key = "&key=";

    Statistics statistics = new Statistics(0,0);
    String coordApi = "https://api.opencagedata.com/geocode/v1/json?q=";
    String undefinedStr = "undefined";

    @Autowired
    CoordinateService coordinateService;

    @Autowired
    AirQualityService airQualityService;

    @Autowired
    WeatherService weatherService;

    public PlaceController(CoordinateService coordinateService, AirQualityService airQualityService, WeatherService weatherService){
        this.coordinateService=coordinateService;
        this.airQualityService = airQualityService;
        this.weatherService = weatherService;
    }

    @RequestMapping(value = "/coordinates/{place}")
    public Object getCoordinate(@PathVariable String place) throws ParseException {
        String coordUrl = coordApi + place + key + coordApiKey + language;
        String returnVal = "";
        Double lat = null;
        Double lng = null;
        Coordinate lookUp = new Coordinate();
        Object finalReturn = null;
        Object mapCont = Cache.cacheMap.get(coordUrl);
        if(mapCont == null){
            returnVal = httpRequest(coordUrl);
            JSONParser parse = new JSONParser();
            JSONObject jobj = (JSONObject)parse.parse(returnVal);
            JSONArray jsonarr_1 = (JSONArray) jobj.get("results");
            for(int i=0;i<jsonarr_1.size();i++) {
                JSONObject jsonobj_1 = (JSONObject) jsonarr_1.get(i);
                JSONObject jsonarr_2 = (JSONObject) jsonobj_1.get("geometry");
                lat = (Double) jsonarr_2.get("lat");
                lng = (Double) jsonarr_2.get("lng");
            }
            if(lat != null && lng != null) {
                lookUp.setLatitude(lat);
                lookUp.setLongitude(lng);
                lookUp.setPlace(place);

                coordinateService.saveCoordinate(lookUp);

                finalReturn = coordinateService.getPlaceCoordinate(place);

                Cache.cacheMap.put(coordUrl, coordinateService.getPlaceCoordinate(place));
                statistics.setMiss();
                ClockRemoveFromCache(coordUrl);
            }
            else{
                lookUp.setPlace(place);
                lookUp.setLongitude(0);
                lookUp.setLatitude(0);

                coordinateService.saveCoordinate(lookUp);
                finalReturn = coordinateService.getPlaceCoordinate(place) ;
                Cache.cacheMap.put(coordUrl,coordinateService.getPlaceCoordinate(place));
                statistics.setMiss();
                //quando é um  lugar errado pode ficar na cache para sempre pois nunca vai passar a existir
            }

        }
        else {
            finalReturn = mapCont;
            statistics.setHit();
        }

        return finalReturn;

    }

    @RequestMapping(value = "/airquality/{place}")
    public Object getConditions(@PathVariable String place) throws ParseException {
        String coordUrl = coordApi + place + key + coordApiKey + language;
        Coordinate lookUp = getPlaceCoordinate(coordUrl,place);
        AirQuality air = new AirQuality();
        String airQualUrl = "https://api.breezometer.com/air-quality/v2/current-conditions"+ "?lat=" + lookUp.getLatitude() + "&lon=" + lookUp.getLongitude() + key + breezoApiKey + "&features=breezometer_aqi";
        String returnVal = "";
        Object finalReturn = null;

        Object mapCont = Cache.cacheMap.get(airQualUrl);

        if (mapCont == null){
            if(lookUp.getLongitude() == 0 && lookUp.getLatitude()==0){
                air.setPlace(undefinedStr);
                air.setCategory(undefinedStr);
                air.setMainPollutant(undefinedStr);

                airQualityService.saveAirQuality(air);
                finalReturn = airQualityService.getAirQualityByPlace(undefinedStr);

                Cache.cacheMap.put(airQualUrl,airQualityService.getAirQualityByPlace(undefinedStr));
                //não é preciso apagar da cache pois um place inexistente nunca irá existir
                statistics.setMiss();
            }
            else{
                returnVal= httpRequest(airQualUrl);
                if (returnVal != ""){
                    JSONParser parse = new JSONParser();
                    JSONObject jobj = (JSONObject)parse.parse(returnVal);
                    JSONObject jsonarr_1 = (JSONObject) jobj.get("data");
                    JSONObject indexes = (JSONObject) jsonarr_1.get("indexes");
                    JSONObject baqui = (JSONObject) indexes.get("baqi");
                    String category = (String) baqui.get("category");
                    String pollutant = (String) baqui.get("dominant_pollutant");
                    air.setPlace(place);
                    air.setCategory(category);
                    air.setMainPollutant(pollutant);

                    airQualityService.saveAirQuality(air);
                    finalReturn = airQualityService.getAirQualityByPlace(place);

                    Cache.cacheMap.put(airQualUrl,airQualityService.getAirQualityByPlace(place));
                    statistics.setMiss();

                    ClockRemoveFromCache(airQualUrl);
                }
                else{
                    air.setPlace(place);
                    air.setCategory(undefinedStr);
                    air.setMainPollutant(undefinedStr);

                    airQualityService.saveAirQuality(air);
                    finalReturn = air;

                    Cache.cacheMap.put(airQualUrl,airQualityService.getAirQualityByPlace(place));
                    statistics.setMiss();

                    ClockRemoveFromCache(airQualUrl);

                }
            }

        }
        else{
            finalReturn = mapCont;
            statistics.setHit();

        }
        return finalReturn;
    }

    @RequestMapping(value = "/weather/{place}")
    public Object getWeather(@PathVariable String place)throws ParseException{
        String coordUrl = coordApi + place + key + coordApiKey + language;
        Coordinate lookUp = getPlaceCoordinate(coordUrl,place);
        String weatherUrl = "https://api.breezometer.com/weather/v1/current-conditions"+ "?lat=" + lookUp.getLatitude() + "&lon=" + lookUp.getLongitude() + key + breezoApiKey;
        String returnVal = "";
        Object finalReturn = null;
        Weather weather = new Weather();
        Object mapCont = Cache.cacheMap.get(weatherUrl);
        if (mapCont == null) {
            if(lookUp.getLongitude() == 0 && lookUp.getLatitude()==0){
                weather.setPlace(undefinedStr);
                weather.setWeather_text(undefinedStr);
                weather.setRelative_humidity(null);
                weather.setTempVal(null);
                weather.setTempUnits(undefinedStr);

                weatherService.saveWeather(weather);
                finalReturn = weatherService.getWeatherByPlace(undefinedStr);

                Cache.cacheMap.put(weatherUrl, weatherService.getWeatherByPlace(undefinedStr));
                statistics.setMiss();
                //não é preciso apagar da cache pois um place inexistente nunca irá existir
            }
            else{
                returnVal=httpRequest(weatherUrl);
                JSONParser parse = new JSONParser();
                JSONObject jobj = (JSONObject) parse.parse(returnVal);
                JSONObject jsonarr_1 = (JSONObject) jobj.get("data");
                if(jsonarr_1 != null){
                    String weather_text = (String) jsonarr_1.get("weather_text");
                    Long relative_humidity = (Long) jsonarr_1.get("relative_humidity");
                    JSONObject temperature = (JSONObject) jsonarr_1.get("temperature");
                    double tempVal = (double) temperature.get("value");
                    String tempUnit = (String) temperature.get("units");


                    weather.setPlace(place);
                    weather.setWeather_text(weather_text);
                    weather.setRelative_humidity(relative_humidity);
                    weather.setTempVal(tempVal);
                    weather.setTempUnits(tempUnit);

                    weatherService.saveWeather(weather);
                    finalReturn = weatherService.getWeatherByPlace(place);

                    Cache.cacheMap.put(weatherUrl, weatherService.getWeatherByPlace(place));
                    statistics.setMiss();

                    ClockRemoveFromCache(weatherUrl);
                }


                else{
                    weather.setPlace(place);
                    weather.setWeather_text(undefinedStr);
                    weather.setRelative_humidity(null);
                    weather.setTempVal(null);
                    weather.setTempUnits(undefinedStr);

                    weatherService.saveWeather(weather);
                    finalReturn = weather;

                    Cache.cacheMap.put(weatherUrl, weatherService.getWeatherByPlace(place));
                    statistics.setMiss();

                    ClockRemoveFromCache(weatherUrl);
                }


            }

        }
        else{
            finalReturn = mapCont;
            statistics.setHit();

        }
        return finalReturn;
    }

    @RequestMapping(value="/cachestatistics")
    public Object getCacheStatistics(){
        return statistics.toString();
    }

    public Coordinate getPlaceCoordinate(String url, String place) throws ParseException {
        String returnVal = "";
        Double lat = null;
        Double lng = null;
        Coordinate lookUp = new Coordinate();
        returnVal=httpRequest(url);
        JSONParser parse = new JSONParser();
        JSONObject jobj = (JSONObject)parse.parse(returnVal);
        JSONArray jsonarr_1 = (JSONArray) jobj.get("results");
        for(int i=0;i<jsonarr_1.size();i++) {
            JSONObject jsonobj_1 = (JSONObject) jsonarr_1.get(i);
            JSONObject jsonarr_2 = (JSONObject) jsonobj_1.get("geometry");
            lat = (Double) jsonarr_2.get("lat");
            lng = (Double) jsonarr_2.get("lng");
        }
        if(lat != null && lng != null){
            lookUp.setLatitude(lat);
            lookUp.setLongitude(lng);
            lookUp.setPlace(place);
            coordinateService.saveCoordinate(lookUp);

        }
        else{
            lookUp.setPlace(place);
            lookUp.setLongitude(0);
            lookUp.setLatitude(0);
            coordinateService.saveCoordinate(lookUp);

        }

        return lookUp;
    }

    public String httpRequest(String url){
        String returnVal = "";
        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int responsecode = con.getResponseCode();
            if (responsecode != 200){
                //throw new RuntimeException("HttpResponseCode"+responsecode);
                returnVal="";
            }
            else{
                Scanner sc = new Scanner(obj.openStream());
                while(sc.hasNext()){
                    returnVal += sc.nextLine(); //returnVal = inline
                }
                sc.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            //returnVal="";
        } catch (IOException e) {
            e.printStackTrace();
            //returnVal="";
        }
        return returnVal;
    }

    public void ClockRemoveFromCache(String url){
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Cache.cacheMap.remove(url);
                    }
                },
                //5000
                900000 //o objeto fica 15 minutos na cache, se quiser testar mais rapidamente pode descomentar a linha
                //acima e passará para 5 segundos
        );
    }
}
