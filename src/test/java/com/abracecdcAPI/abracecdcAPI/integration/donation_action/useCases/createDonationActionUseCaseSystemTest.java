package com.abracecdcAPI.abracecdcAPI.integration.donation_action.useCases;

import java.time.Duration;

import org.assertj.core.api.Assertions;
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

@DisplayName("Automated test of the create donation action function")
public class createDonationActionUseCaseSystemTest {
    private WebDriver browser;

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
    @DisplayName("create donation action with valid credentials")
    public void testCreateDonationActionWithValidCredentials() { 
        // Open localhost abrace-cdc
        browser.get("http://127.0.0.1:5173/");

        // Click on the create button
        browser.findElement(By.xpath("//*[@id='root']/div/header/a[2]")).click();

        // Choose the donation action option
        browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/a[1]")).click();

        // Click on the other type of donation button
        browser.findElement(By.xpath("//*[@id=\"r2\"]")).click();

        // Click on continue button
        browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div[2]/form/button")).click();

        // Select the category
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
        WebElement categoryDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div/div[2]/form/div[1]/select")));
        Select categorySelect = new Select(categoryDropdown);
        categorySelect.selectByVisibleText("Alimento");

        // Set date
        browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/form/div[1]/input[1]")).sendKeys("12/12/2024");
    
        // Click on no button
        browser.findElement(By.xpath("//*[@id=\"r2\"]")).click();

        // Set the description
        browser.findElement(By.name("description")).sendKeys("Teste de doação");

        // Click on the continue button
        browser.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/form/button")).click();

        // Confirm the donation
        String donationConfirmation = browser.findElement(By.xpath("//*[@id='root']/div/div/div[2]/div[2]/h1")).getText();
        Assertions.assertThat(donationConfirmation).isEqualTo("Sua doação foi aprovada!");
    }   
}
