package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthE2ETests {

    @LocalServerPort
    private int port;
    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() throws InterruptedException {
        this.driver = new ChromeDriver();
        driver.get("http://localhost:" + this.port + "/login");
        WebElement signupButton = driver.findElement(By.id("signup-link"));
        signupButton.click();
        Thread.sleep(2000);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldSignupUser() throws InterruptedException {
        this.createUser("testUser123");
        Thread.sleep(3000);
        Assertions.assertEquals("Login", driver.getTitle());
        WebElement alertField = driver.findElement(By.id("alertSuccess"));
        Assertions.assertEquals("You successfully signed up! Please continue to the login page.", alertField.getText());
    }

    @Test
    public void shouldNotSignupUser() throws InterruptedException {
        this.createUser("glastra");
        Thread.sleep(3000);
        this.goToSignupPage();
        Thread.sleep(3000);
        this.createUser("glastra");
        Assertions.assertEquals("Sign Up", driver.getTitle());
        WebElement alertField = driver.findElement(By.id("alertError"));
        Assertions.assertEquals("The username already exists.", alertField.getText());
    }

    public void createUser(String userName) {
        WebElement fistNameField = driver.findElement(By.id("inputFirstName"));
        WebElement lastNameField = driver.findElement(By.id("inputLastName"));
        WebElement usernameField = driver.findElement(By.id("inputUsername"));
        WebElement passwordField = driver.findElement(By.id("inputPassword"));
        WebElement signupButton = driver.findElement(By.tagName("button"));
        fistNameField.sendKeys("Gustavo");
        lastNameField.sendKeys("Lastra");
        usernameField.sendKeys(userName);
        passwordField.sendKeys("Test123$");
        signupButton.click();
    }

    public void goToSignupPage() {
        WebElement signupButton = driver.findElement(By.id("signup-link"));
        signupButton.click();
    }
}
