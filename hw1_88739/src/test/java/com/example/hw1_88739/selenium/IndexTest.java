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
public class IndexTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
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
    public void index() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1536, 824));
        driver.findElement(By.id("welcome")).click();
        assertThat(driver.findElement(By.id("welcome")).getText(), is("Welcome to Air Quality!"));
        driver.findElement(By.id("explain")).click();
        assertThat(driver.findElement(By.id("explain")).getText(), is("You can use the top navbar to navigate to where you want. The navbar options are:"));
        driver.findElement(By.id("coordinatesh4")).click();
        assertThat(driver.findElement(By.id("coordinatesh4")).getText(), is("Coordinates"));
        driver.findElement(By.id("coordinatesExplain")).click();
        assertThat(driver.findElement(By.id("coordinatesExplain")).getText(), is("In case you want to know your place coordinates. Just for fun!"));
        driver.findElement(By.id("airqualityh4")).click();
        assertThat(driver.findElement(By.id("airqualityh4")).getText(), is("Air Quality Search"));
        driver.findElement(By.id("airqualityExplain")).click();
        assertThat(driver.findElement(By.id("airqualityExplain")).getText(), is("You can search by place. You should indicate the country where you want to search."));
        driver.findElement(By.id("weatherh4")).click();
        assertThat(driver.findElement(By.id("weatherh4")).getText(), is("Weather Search"));
        driver.findElement(By.id("weatherExplain")).click();
        assertThat(driver.findElement(By.id("weatherExplain")).getText(), is("As before, you can search by place. You should indicate the country where you want to search."));
        driver.findElement(By.id("ps")).click();
        assertThat(driver.findElement(By.id("ps")).getText(), is("Ps: You should always indicate the country. Example: if you want to search in Aveiro , you should look for Aveiro,Portugal"));
    }
}

