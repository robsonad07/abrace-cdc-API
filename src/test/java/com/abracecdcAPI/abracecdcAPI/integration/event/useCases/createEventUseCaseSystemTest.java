package com.abracecdcAPI.abracecdcAPI.integration.event.useCases;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class createEventUseCaseSystemTest {
        
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
                // browser.quit();
            }
        }
    
        @Test
        @DisplayName("Create a new valid event")
        public void testCreateANewValidEvent() { 
            // Open localhost abrace-cdc
            browser.get("http://localhost:5173/admin/");
    
            // Clink on the Create button
            browser.findElement(By.xpath("//*[@id='root']/div/header/a[2]")).click();

            // Click on the New Event button
            browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/a[1]")).click();

            // Set the event title
            browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/div[1]/input")).sendKeys("Test Event");

            // Set the event subtitle
            browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/div[2]/input")).sendKeys("Test Subtitle");

            // Select the category in the dropdown
            WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
            WebElement categoryDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/div/form/select")));
            Select categorySelect = new Select(categoryDropdown);
            categorySelect.selectByVisibleText("Test Category");
            
            // browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/button[1]")).click();
            // browser.findElement(By.xpath("//*[@id='root']/div/div/form/select/optiona[1]")).click();

            // Select the organizer in the dropdown
            // browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/button[2]")).click();
            // browser.findElement(By.xpath("//*[@id='root']/div/div/form/select/optiona[1]")).click();

            // Set the event street
            browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/div[4]/div[1]/div[1]/input")).sendKeys("Test Street");

            // Set the event number
            browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/div[4]/div[1]/div[2]/input")).sendKeys("123");

            // Set the event city
            browser.findElement(By.xpath("//*[@id='root']/div/div/form/div[4]/div[2]/div[1]/input")).sendKeys("Test City");

            // Set the event cep
            browser.findElement(By.xpath("//*[@id='root']/div/div/form/div[4]/div[2]/div[2]/input")).sendKeys("12345678");

            // set complement
            browser.findElement(By.xpath("//*[@id='root']/div/div/form/div[4]/div[3]/input")).sendKeys("Test Complement");

            // Set the event date
            browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/div[5]/div[1]/div/input")).sendKeys("12/12/2024");

            // Set the event time
            browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/div[5]/div[2]/div/input")).sendKeys("12:00");

            // Set the event description
            browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/div[6]/textarea")).sendKeys("Test Description");

            // Click on the create button
            browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/button[3]")).click();

        }
}
