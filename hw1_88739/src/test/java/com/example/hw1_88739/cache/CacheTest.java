package com.example.hw1_88739.cache;

import com.example.hw1_88739.entities.AirQuality;
import com.example.hw1_88739.entities.Coordinate;
import com.example.hw1_88739.entities.Weather;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class CacheTest {
    @Test
    public void testAssertMap() throws InterruptedException {

        HashMap<String, Object> cacheMapExpected = new HashMap<>(); //hashmap que servirá como termo de comparação

        Coordinate viseuCoor = new Coordinate("Viseu,Portugal",40.6652423, -7.9161281);
        Coordinate aveiroCoor = new Coordinate("Aveiro,Portugal",40.6977662, -8.4909302);
        Coordinate coimbraCoor = new Coordinate("Coimbra,Portugal",40.2090193, -8.4322842);
        Coordinate portoCoor = new Coordinate("Porto,Portugal",37.5960739, -8.3313824);

        Cache.cacheMap.put("www.viseu.pt", viseuCoor); //dados inventados para testes
        Cache.cacheMap.put("www.aveiro.pt", aveiroCoor);
        Cache.cacheMap.put("www.coimbra.pt", coimbraCoor);


        cacheMapExpected.put("www.viseu.pt", viseuCoor);
        cacheMapExpected.put("www.aveiro.pt", aveiroCoor);
        cacheMapExpected.put("www.coimbra.pt", coimbraCoor);


        AirQuality viseuQuality = new AirQuality("Viseu,Portugal","Average air quality", "o3");
        AirQuality aveiroQuality = new AirQuality("Aveiro,Portugal","Good air quality", "o3");
        AirQuality coimbraQuality = new AirQuality("Coimbra,Portugal","Average air quality", "o3");
        AirQuality portoQuality = new AirQuality("Porto,Portugal","Good air quality", "o3");

        Cache.cacheMap.put("www.viseuquality.com",viseuQuality);
        Cache.cacheMap.put("www.aveiroquality.com",aveiroQuality);
        Cache.cacheMap.put("www.coimbraquality.com",coimbraQuality);

        cacheMapExpected.put("www.viseuquality.com",viseuQuality);
        cacheMapExpected.put("www.aveiroquality.com",aveiroQuality);
        cacheMapExpected.put("www.coimbraquality.com",coimbraQuality);

        Weather aveiroWeather = new Weather("Aveiro,Portugal","Overcast",17.86,"C", (long) 56);
        Weather viseuWeather = new Weather("Viseu,Portugal","Clouds",18.86,"C", (long) 57);
        Weather coimbraWeather = new Weather("Coimbra,Portugal","Overcast",19.86,"C", (long) 58);
        Weather portoWeather = new Weather("Porto,Portugal","Clouds",20.86,"C", (long) 59);

        Cache.cacheMap.put("www.viseuweather.com",viseuWeather);
        Cache.cacheMap.put("www.aveiroweather.com",aveiroWeather);
        Cache.cacheMap.put("www.coimbraweather.com",coimbraWeather);

        cacheMapExpected.put("www.viseuweather.com",viseuWeather);
        cacheMapExpected.put("www.aveiroweather.com",aveiroWeather);
        cacheMapExpected.put("www.coimbraweather.com",coimbraWeather);


        //A cache contém o mesmo que o cacheMapExpected, mostrando assim que funcionou corretamente
        assertThat(Cache.cacheMap, is(cacheMapExpected));

        //A cache tem o tamanho esperado
        assertThat(Cache.cacheMap.size(), is(9));

        //A cache tem as entradas esperadas e não tem as que não foram adicionadas
        assertThat(Cache.cacheMap, IsMapContaining.hasEntry("www.viseu.pt", viseuCoor));
        assertThat(Cache.cacheMap, IsMapContaining.hasEntry("www.viseuquality.com",viseuQuality));
        assertThat(Cache.cacheMap, IsMapContaining.hasEntry("www.viseuweather.com",viseuWeather));
        assertThat(Cache.cacheMap, not(IsMapContaining.hasEntry("www.porto.pt", portoCoor)));
        assertThat(Cache.cacheMap, not(IsMapContaining.hasEntry("www.portoquality.pt", portoQuality)));
        assertThat(Cache.cacheMap, not(IsMapContaining.hasEntry("www.portoweather.pt", portoWeather)));

        //A cache é capaz de guardar coordinate, airquality e weather
        assertThat(Cache.cacheMap, IsMapContaining.hasEntry("www.aveiro.pt", aveiroCoor));
        assertThat(Cache.cacheMap, IsMapContaining.hasEntry("www.aveiroquality.com",aveiroQuality));
        assertThat(Cache.cacheMap, IsMapContaining.hasEntry("www.aveiroweather.com",aveiroWeather));

        //A cache tem keys esperadas
        assertThat(Cache.cacheMap, IsMapContaining.hasKey("www.coimbra.pt"));
        assertThat(Cache.cacheMap, IsMapContaining.hasKey("www.coimbraquality.com"));
        assertThat(Cache.cacheMap, IsMapContaining.hasKey("www.coimbraweather.com"));

        //A cache não tem as keys que não foram adicionadas
        assertThat(Cache.cacheMap, not(IsMapContaining.hasValue(portoCoor)));
        assertThat(Cache.cacheMap, not(IsMapContaining.hasValue(portoQuality)));
        assertThat(Cache.cacheMap, not(IsMapContaining.hasValue(portoWeather)));

        //A cache tem values esperado
        assertThat(Cache.cacheMap, IsMapContaining.hasValue(coimbraCoor));
        assertThat(Cache.cacheMap, IsMapContaining.hasValue(viseuCoor));
        assertThat(Cache.cacheMap, IsMapContaining.hasValue(aveiroCoor));


        //Ao fim de 5 segundos é removido um objeto da cache
        ClockRemoveFromCache("www.coimbra.pt");
        TimeUnit.SECONDS.sleep(5); //esperar 5 segundos para que o objeto seja removido
                                            //apesar de a remoção levar apenas um segundo, dou 5 segundos para ter uma margem de segurança
        assertThat(Cache.cacheMap, not(IsMapContaining.hasKey("www.coimbra.pt")));

        //os hits e os misses funcionam corretamente - espera-se, deste teste, um hit e um miss
        Object mapCont = Cache.cacheMap.get("www.viseu.pt");
        Object mapCont2 = Cache.cacheMap.get("www.china.pt");
        Statistics statistics = new Statistics(0,0);
        if (mapCont == null){
            statistics.setMiss();
        }
        else if (mapCont != null){
            statistics.setHit();
        }
        if (mapCont2 == null){
            statistics.setMiss();
        }
        else if (mapCont2 != null){
            statistics.setHit();
        }
        assertThat(statistics.getHit(),is(1));
        assertThat(statistics.getMiss(),is(1));


    }
    public void ClockRemoveFromCache(String url){
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Cache.cacheMap.remove(url);
                    }
                },
                1000
        );
    }
}
