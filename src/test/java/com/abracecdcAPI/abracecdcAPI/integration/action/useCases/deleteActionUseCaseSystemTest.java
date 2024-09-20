package com.abracecdcAPI.abracecdcAPI.integration.action.useCases;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

@DisplayName("Automated test of the delete action function")
public class deleteActionUseCaseSystemTest {
    
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
    @DisplayName("Delete an action")
    public void testDeleteAnAction() { 
        // Open localhost abrace-cdc
        browser.get("http://localhost:5173/admin/actions");

        // Click on the Delete button
        browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/div/div[3]/button[2]")).click();

        // Click on the Delete Action button
        browser.findElement(By.xpath("//*[@id=\"radix-:rh:\"]/div/button[2]")).click();
    }
    
}
