package com.abracecdcAPI.abracecdcAPI.integration.action.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

@DisplayName("Automated test of the create action function")
public class createActionUseCaseSystemTest {
    @Test
    @DisplayName("Create a new valid action")
    public void testCreateANewValidAction() {
        // Open Chrome
        WebDriverManager.chromedriver().setup();
        WebDriver browser = new ChromeDriver();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Ajuste o tempo conforme necessário

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
        String categoryName = "Test Category";
        browser.findElement(By.xpath("//*[@id=\'new-category-form\']/input[1]")).sendKeys(categoryName);

        // Set the category subtitle
        browser.findElement(By.xpath("//*[@id=\'new-category-form\']/input[2]")).sendKeys("Test Subtitle");

        // Save the new category
        browser.findElement(By.xpath("//*[@id=\'new-category-form\']/button")).click();

        // Wait for the category to be added to the select
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10)); // Ajuste o tempo conforme necessário
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//*[@id='root']/div/div/form/div[4]/div[1]/select"), categoryName));

        // Select the category in the dropdown
        WebElement categoryDropdown = browser
                .findElement(By.xpath("//*[@id='root']/div/div/form/div[4]/div[1]/select"));
        Select select = new Select(categoryDropdown);
        select.selectByVisibleText(categoryName); // Seleciona a categoria criada pelo nome

        // Criar novo organizador
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/form/div[4]/div[2]/button[2]")).click();

        // Setar o nome do organizador
        String organizerName = "Test Organizer";
        browser.findElement(By.xpath("//*[@id=\'new-organizer-form\']/input[1]")).sendKeys(organizerName);

        // Setar o email do organizador
        browser.findElement(By.xpath("//*[@id=\'new-organizer-form\']/input[2]")).sendKeys("test.organizer@email.com");

        // Setar o telefone do organizador
        browser.findElement(By.xpath("//*[@id=\'new-organizer-form\']/input[3]")).sendKeys("777777777");

        // Salvar o novo organizador
        browser.findElement(By.xpath("//*[@id=\'new-organizer-form\']/button")).click();

        // Aguarde até que o organizador seja adicionado ao select
        WebDriverWait waitOrganizer = new WebDriverWait(browser, Duration.ofSeconds(10)); // Ajuste o tempo conforme
                                                                                          // necessário
        waitOrganizer.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//*[@id='root']/div/div/form/div[4]/div[2]/select"), organizerName));

        // Localizar o dropdown de organizadores
        WebElement organizerDropdown = browser
                .findElement(By.xpath("//*[@id='root']/div/div/form/div[4]/div[2]/select"));
        Select selectOrganizer = new Select(organizerDropdown);

        // Selecionar o organizador pelo nome visível
        selectOrganizer.selectByVisibleText(organizerName); // Seleciona o organizador criado pelo nome

        // Set the action description
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/form/div[5]/textarea"))
                .sendKeys("Test Description");

        // Save the action
        browser.findElement(By.xpath("//*[@id=\'root\']/div/div/form/button")).click();
    }
}
