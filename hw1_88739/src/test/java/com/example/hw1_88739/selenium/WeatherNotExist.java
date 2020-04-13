package com.example.hw1_88739.selenium;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

@ExtendWith(SeleniumExtension.class)
public class WeatherNotExist {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private WebElement myDynamicElement;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void weatherNotExist() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1536, 824));
        driver.findElement(By.linkText("Weather Search")).click();
        driver.findElement(By.id("placeName")).click();
        driver.findElement(By.id("placeName")).sendKeys("placerandom");
        driver.findElement(By.id("searchButton")).click();
        myDynamicElement = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("placeWeather")));
        myDynamicElement = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("weatherText")));
        myDynamicElement = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("tempVal")));
        myDynamicElement = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("tempUnits")));
        myDynamicElement = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("humidity")));
        driver.findElement(By.id("placeWeather")).click();
        assertThat(driver.findElement(By.id("placeWeather")).getText(), is("Place: undefined"));
        driver.findElement(By.id("weatherText")).click();
        assertThat(driver.findElement(By.id("weatherText")).getText(), is("Weather: undefined"));
        driver.findElement(By.id("tempVal")).click();
        assertThat(driver.findElement(By.id("tempVal")).getText(), is("Temperature: null"));
        driver.findElement(By.id("tempUnits")).click();
        assertThat(driver.findElement(By.id("tempUnits")).getText(), is("Temperature units: undefined"));
        driver.findElement(By.id("humidity")).click();
        assertThat(driver.findElement(By.id("humidity")).getText(), is("Humidity: null"));
    }
}
