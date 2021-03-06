package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NoteTab;
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
class NotesE2ETests {

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
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void shouldLoginAndAddNote() throws InterruptedException {

        signupOnce();
        login();
        goToNotesTab();
        NoteTab noteTab = new NoteTab(driver);
        noteTab.addNote("title", "description");
        Thread.sleep(3000);
        Assertions.assertEquals("title", noteTab.getTableTitle());
        Assertions.assertEquals("description", noteTab.getTableDescription());
    }

    @Test
    @Order(2)
    public void shouldEditNote() throws InterruptedException {
        login();
        goToNotesTab();
        NoteTab noteTab = new NoteTab(driver);
        noteTab.editNote("Amazing title", "Amazing description");
        Assertions.assertEquals("Amazing title", noteTab.getTableTitle());
        Assertions.assertEquals("Amazing description", noteTab.getTableDescription());
    }

    @Test
    @Order(3)
    public void shouldDeleteNote() throws InterruptedException {
        login();
        goToNotesTab();
        NoteTab noteTab = new NoteTab(driver);
        noteTab.deleteNote();

        assertThrows(NoSuchElementException.class, () -> {
            WebElement table = driver.findElement(By.id("notesTBody"));
            table.findElement(By.tagName("tr"));
        });
    }

    private void goToNotesTab() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(3000);
        HomePage homePage = new HomePage(driver);
        homePage.goToNotesTab();
        Thread.sleep(3000);
    }

    private void login() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        driver.get("http://localhost:" + this.port + "/login");
        loginPage.login("NoteFirst", "Test123$");
        Thread.sleep(3000);
    }

    private void signupOnce() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("NoteFirst", "authL", "NoteFirst", "Test123$");
        Thread.sleep(3000);
    }
}
