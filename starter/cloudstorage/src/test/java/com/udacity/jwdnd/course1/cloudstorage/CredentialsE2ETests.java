package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.CredentialTab;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialsE2ETests {

    @LocalServerPort
    private int port;
    private WebDriver driver;
    private CredentialTab credentialTab;

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
    public void shouldAddCredential() throws InterruptedException {
        signupOnce();
        login();
        goToCredentialTab();
        CredentialTab credentialTab = new CredentialTab(driver);
        credentialTab.addCredential("url", "username", "password");
        Assertions.assertEquals("url", credentialTab.getTableUrl());
        Assertions.assertEquals("username", credentialTab.getTableUsername());
    }

    @Test
    @Order(2)
    public void shouldEditCredential() throws InterruptedException {
        login();
        goToCredentialTab();
        CredentialTab credentialTab = new CredentialTab(driver);
        credentialTab.editCredential("Awesome url", "Awesome username", "Awesome password");

        Assertions.assertEquals("Awesome url", credentialTab.getTableUrl());
        Assertions.assertEquals("Awesome username", credentialTab.getTableUsername());
    }

    @Test
    @Order(3)
    public void shouldDeleteCredential() throws InterruptedException {
        login();
        goToCredentialTab();
        CredentialTab credentialTab = new CredentialTab(driver);
        credentialTab.deleteCredential();
        assertThrows(NoSuchElementException.class, () -> {
            WebElement table = driver.findElement(By.id("credentialsTBody"));
            table.findElement(By.tagName("tr"));
        });
    }


    private void goToCredentialTab() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(3000);
        HomePage homePage = new HomePage(driver);
        homePage.goToCredentialsTab();
        Thread.sleep(3000);
    }

    private void login() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        driver.get("http://localhost:" + this.port + "/login");
        loginPage.login("CredentialFirst", "Test123$");
        Thread.sleep(3000);
    }

    private void signupOnce() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("CredentialFirst", "authL", "CredentialFirst", "Test123$");
        Thread.sleep(3000);
    }
}
