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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.util.*;

@ExtendWith(SeleniumExtension.class)
public class AirQualityTest {
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
    public void airQuality() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1536, 824));
        driver.findElement(By.linkText("Air Quality Search")).click();
        driver.findElement(By.id("airqualityname")).click();
        assertThat(driver.findElement(By.id("airqualityname")).getText(), is("Air Quality"));
        driver.findElement(By.id("placeName")).click();
        driver.findElement(By.id("placeName")).sendKeys("Viseu,Portugal");
        driver.findElement(By.id("searchButton")).click();
        myDynamicElement = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("placeAirquality")));
        myDynamicElement = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("stateAir")));
        myDynamicElement = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("mainPollutant")));
        driver.findElement(By.id("placeAirquality")).click();
        assertThat(driver.findElement(By.id("placeAirquality")).getText(), is("Place: Viseu,Portugal"));
        driver.findElement(By.id("stateAir")).click();
        //assertThat(driver.findElement(By.id("stateAir")).getText(), is("Air quality state: Good air quality"));//suscetivel a mudanças
        driver.findElement(By.id("mainPollutant")).click();
        //assertThat(driver.findElement(By.id("mainPollutant")).getText(), is("Main polluant: o3")); //suscetivel a mudanças
    }
}

