package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void shouldRedirectToLogin() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(3000);
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(2)
    public void shouldSignupUser() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        LoginPage loginPage = new LoginPage(driver);
        SignupPage signupPage = new SignupPage(driver);

        signupPage.signup("authFirst", "authL", "authFirst", "Test123$");
        Thread.sleep(3000);
        Assertions.assertEquals("Login", driver.getTitle());
        Assertions.assertEquals("You successfully signed up! Please continue to the login page.", loginPage.getAlertText());
    }

    @Test
    @Order(3)
    public void shouldNotSignupUser() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("authFirst", "authL", "authFirst", "Test123$");
        Thread.sleep(3000);
        Assertions.assertEquals("Sign Up", driver.getTitle());
        Assertions.assertEquals("The username already exists.", signupPage.getAlertText());
    }

    @Test
    @Order(4)
    public void shouldRedirectToLoginAfterLogout() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/login");
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("authFirst", "Test123$");
        Thread.sleep(3000);
        Assertions.assertEquals("Home", driver.getTitle());
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(3000);
        homePage.logout();
        Thread.sleep(3000);
        Assertions.assertEquals("Login", driver.getTitle());
    }
}
