package fotius.example.todo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TodoListWebPageTest {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void cleanup() {

    }

    @Test
    void testAddTodo() {
        goToHtmlPage();

        final WebElement todoInput = driver.findElement(By.id("todo"));
        final WebElement addButton = driver.findElement(By.id("add"));

        // Add a new task
        todoInput.sendKeys("Buy groceries");
        addButton.click();

        // Verify that the task is added to the list
        WebElement todoItem = driver.findElement(By.xpath("//li[contains(text(), 'Buy groceries')]"));
        assertEquals("Buy groceries", todoItem.getText().trim());
    }

    @Test
    void testRemoveTodo() {
        goToHtmlPage();

        final WebElement todoInput = driver.findElement(By.id("todo"));
        final WebElement addButton = driver.findElement(By.id("add"));

        // Add a new task
        todoInput.sendKeys("Buy groceries");
        addButton.click();

        // Remove the task
        WebElement todoItem = driver.findElement(By.xpath("//li[contains(text(), 'Buy groceries')]"));
        WebElement removeButton = todoItem.findElement(By.cssSelector("button.btn-danger"));
        removeButton.click();

        // Verify that the task is removed from the list
        assertEquals(0, driver.findElements(By.xpath("//li[contains(text(), 'Buy groceries')]")).size());
    }


    void goToHtmlPage() {
        driver.get(
                "file://" +
                        Paths.get(System.getProperty("user.dir"))
                                .resolve("src")
                                .resolve("main")
                                .resolve("resources")
                                .resolve("todo.html")
                                .toAbsolutePath()
        );
    }
}
