package com.example.hw1_88739.selenium;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
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
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SeleniumExtension.class)
public class AirQualityNotExist {
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
    public void airqualityNotExist() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1536, 824));
        driver.findElement(By.linkText("Air Quality Search")).click();
        driver.findElement(By.id("placeName")).click();
        driver.findElement(By.id("placeName")).sendKeys("placerandom");
        driver.findElement(By.id("searchButton")).click();
        myDynamicElement = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("placeAirquality")));
        myDynamicElement = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("stateAir")));
        myDynamicElement = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("mainPollutant")));
        driver.findElement(By.id("placeAirquality")).click();
        assertThat(driver.findElement(By.id("placeAirquality")).getText(), is("Place: undefined"));
        driver.findElement(By.id("stateAir")).click();
        assertThat(driver.findElement(By.id("stateAir")).getText(), is("Air quality state: undefined"));
        driver.findElement(By.id("mainPollutant")).click();
        assertThat(driver.findElement(By.id("mainPollutant")).getText(), is("Main polluant: undefined"));
    }
}
