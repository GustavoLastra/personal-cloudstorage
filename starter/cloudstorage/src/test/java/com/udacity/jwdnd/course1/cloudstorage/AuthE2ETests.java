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
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldRedirectToLogin() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(3000);
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void shouldSignupUser() throws InterruptedException {
        this.goToSignupPage();
        Thread.sleep(3000);
        this.createUser("testUser123", "Test123$");
        Thread.sleep(3000);
        Assertions.assertEquals("Login", driver.getTitle());
        WebElement alertField = driver.findElement(By.id("alertSuccess"));
        Assertions.assertEquals("You successfully signed up! Please continue to the login page.", alertField.getText());
    }

    @Test
    public void shouldNotSignupUser() throws InterruptedException {
        this.goToSignupPage();
        Thread.sleep(3000);
        this.createUser("lastra", "Test123$");
        Thread.sleep(3000);
        this.goToSignupPage();
        Thread.sleep(3000);
        this.createUser("lastra", "Test123$");
        Assertions.assertEquals("Sign Up", driver.getTitle());
        WebElement alertField = driver.findElement(By.id("alertError"));
        Assertions.assertEquals("The username already exists.", alertField.getText());
    }

    @Test
    public void shouldRedirectToLoginAfterLogout() throws InterruptedException {
        this.goToSignupPage();
        Thread.sleep(3000);
        this.createUser("kaku", "Test123$");
        Thread.sleep(3000);
        this.login("kaku", "Test123$");
        Thread.sleep(3000);
        Assertions.assertEquals("Home", driver.getTitle());
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(3000);
        WebElement logoutLint = driver.findElement(By.id("logout"));
        logoutLint.click();
        Thread.sleep(3000);
        Assertions.assertEquals("Login", driver.getTitle());
    }

    public void createUser(String userName, String password) {
        WebElement fistNameField = driver.findElement(By.id("inputFirstName"));
        WebElement lastNameField = driver.findElement(By.id("inputLastName"));
        WebElement usernameField = driver.findElement(By.id("inputUsername"));
        WebElement passwordField = driver.findElement(By.id("inputPassword"));
        WebElement signupButton = driver.findElement(By.tagName("button"));
        fistNameField.sendKeys("Gustavo");
        lastNameField.sendKeys("Lastra");
        usernameField.sendKeys(userName);
        passwordField.sendKeys(password);
        signupButton.click();
    }

    public void login(String userName, String password) {
        WebElement usernameField = driver.findElement(By.id("inputUsername"));
        WebElement passwordField = driver.findElement(By.id("inputPassword"));
        usernameField.sendKeys(userName);
        passwordField.sendKeys(password);
        WebElement loginButton = driver.findElement(By.tagName("button"));
        loginButton.click();
    }

    public void goToSignupPage() {
        WebElement signupButton = driver.findElement(By.id("signup-link"));
        signupButton.click();
    }
}
