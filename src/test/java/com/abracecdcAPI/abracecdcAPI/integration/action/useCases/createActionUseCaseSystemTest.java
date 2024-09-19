package com.abracecdcAPI.abracecdcAPI.integration.action.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

@DisplayName("Automated test of the create action function")
public class createActionUseCaseSystemTest {
    @Test
    @DisplayName("Create a new valid action")
    public void testCreateANewValidAction() {
        // Open Chrome
        WebDriverManager.chromedriver().setup();
        WebDriver browser = new ChromeDriver();

        // Open localhost abrace-cdc
        browser.get("http://localhost:5173/admin/");

        // Clink on the Create button
        browser.findElement(By.xpath("//*[@id='root']/div/header/a[2]")).click();

        // Click on the New Action button
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/div/a[2]")).click();
        
        // Set the action title
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/form/div[1]/input")).sendKeys("Test Action");
   
        // Set the action subtitle
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/form/div[2]/input")).sendKeys("Test Subtitle");

        // Set the action value (integer)
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/form/div[3]/input")).sendKeys("100");

        // Create new category
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/form/div[4]/div[1]/button[2]")).click();

        // Set the category title
        browser.findElement(By.xpath("//*[@id=\'new-category-form\']/input[1]"))
            .sendKeys("Test Category");

        // Set the category subtitle
        browser.findElement(By.xpath("//*[@id=\'new-category-form\']/input[2]"))
            .sendKeys("Test Subtitle");

        // Save the new category
        browser.findElement(By.xpath("//*[@id=\'new-category-form\']/button")).click();

        // Select the new category
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/form/div[4]/div[1]/button[1]")).click();

    }
}
