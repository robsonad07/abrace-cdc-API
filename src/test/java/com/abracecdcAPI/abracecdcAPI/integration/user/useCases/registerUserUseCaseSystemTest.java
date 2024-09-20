package com.abracecdcAPI.abracecdcAPI.integration.user.useCases;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

@DisplayName("Automated test of the register user function")
public class registerUserUseCaseSystemTest {
    WebDriver browser;

    @BeforeEach
    public void setUp() {
        // Setup do WebDriver (abre o navegador)
        WebDriverManager.chromedriver().setup();
        browser = new ChromeDriver();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
    }

    @AfterEach
    public void tearDown() {
        // Fecha o navegador após o teste
        if (browser != null) {
            browser.quit();
        }
    }

    @Test
    @DisplayName("register with valid credentials")
    public void testregisterWithValidCredentials() { 
        // Open localhost abrace-cdc
        browser.get("http://localhost:5173/");

        // Click on the login button
        browser.findElement(By.xpath("//*[@id='root']/div/header/nav/a[5]")).click();
        
        // Click on the register button
        browser.findElement(By.xpath("//*[@id='root']/div/div/div[1]/span[2]/a")).click();

        // Fill the form

        // Name
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/div[1]/form/input[1]")).sendKeys("Teste 1");

        // Cellphone
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/div[1]/form/input[2]")).sendKeys("999999999");

        // Email
        String email = "userTest4e@email.com";
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/div[1]/form/input[3]")).sendKeys(email);

        // Password
        String password = "teste12345";
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/div[1]/form/input[4]")).sendKeys(password);

        // Click on the register button
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/div[1]/form/button")).click();

        // wait
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the user was registered
        // Login
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/div[1]/form/input[1]")).sendKeys(email);

        // Password
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/div[1]/form/input[2]")).sendKeys(password);

        // Click on the login button
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/div[1]/form/button")).click();
    }
        
}
