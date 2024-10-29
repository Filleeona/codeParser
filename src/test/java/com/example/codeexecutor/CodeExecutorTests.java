package com.example.codeexecutor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CodeExecutorTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Укажите путь к ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080"); // Запустите ваше Spring Boot приложение перед тестами
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSaveCode() {
        // Введите название и код
        WebElement nameInput = driver.findElement(By.id("codeName"));
        nameInput.sendKeys("Test Code");

        WebElement codeInput = driver.findElement(By.id("codeContent"));
        codeInput.sendKeys("console.log('Hello World');");

        // Нажмите кнопку "Сохранить"
        WebElement saveButton = driver.findElement(By.xpath("//button[text()='Сохранить']"));
        saveButton.click();

        // Проверьте, что код был сохранен
        WebElement savedCode = driver.findElement(By.xpath("//td/a[text()='Test Code']"));
        assertTrue(savedCode.isDisplayed(), "Код не был сохранен");
    }

    @Test
    public void testExecuteCode() {
        // Введите код и нажмите "Выполнить"
        WebElement codeInput = driver.findElement(By.id("codeContent"));
        codeInput.sendKeys("5 + 5");

        WebElement executeButton = driver.findElement(By.xpath("//button[text()='Выполнить']"));
        executeButton.click();

        // Проверьте вывод результата
        WebElement outputDiv = driver.findElement(By.id("output"));
        assertEquals("10", outputDiv.getText(), "Вывод выполнения кода неверен");
    }

    @Test
    public void testDeleteCode() {
        // Добавьте код, чтобы удалить
        WebElement nameInput = driver.findElement(By.id("codeName"));
        nameInput.sendKeys("Code to Delete");

        WebElement codeInput = driver.findElement(By.id("codeContent"));
        codeInput.sendKeys("console.log('Goodbye');");

        WebElement saveButton = driver.findElement(By.xpath("//button[text()='Сохранить']"));
        saveButton.click();

        // Нажмите кнопку "Удалить"
        WebElement deleteButton = driver.findElement(By.xpath("//tr[td/a[text()='Code to Delete']]/td/button[text()='Удалить']"));
        deleteButton.click();

        // Проверьте, что код больше не отображается
        boolean codeDeleted = driver.findElements(By.xpath("//td/a[text()='Code to Delete']")).isEmpty();
        assertTrue(codeDeleted, "Код не был удален");
    }
}
