package com.udacity.jwdnd.course1.cloudstorage;

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
        driver.get("http://localhost:" + this.port + "/login");
        this.goToSignupPage();
        Thread.sleep(3000);
        this.createUser("kaku", "Test123$");
        Thread.sleep(3000);
        this.login("kaku", "Test123$");
        Thread.sleep(3000);
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(4000);
        this.goToNotes();
        Thread.sleep(3000);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldAddNote() throws InterruptedException {
        this.createNote();
        WebElement table = driver.findElement(By.id("notesTBody"));
        WebElement row = table.findElement(By.tagName("tr"));
        WebElement titleCol = row.findElement(By.tagName("th"));
        WebElement descriptionCol = row.findElements(By.tagName("td")).get(1);

        Assertions.assertEquals("title", titleCol.getText());
        Assertions.assertEquals("description", descriptionCol.getText());
    }

    @Test
    public void shouldDeleteNote() throws InterruptedException {
        this.createNote();

        WebElement deleteNoteBtn = driver.findElement(By.id("deleteNoteBtn"));
        deleteNoteBtn.click();

        assertThrows(NoSuchElementException.class, () -> {
            WebElement table = driver.findElement(By.id("notesTBody"));
            table.findElement(By.tagName("tr"));
        });
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

    public void createNote() throws InterruptedException {
        WebElement addNoteBtn = driver.findElement(By.id("addNoteBtn"));
        addNoteBtn.click();
        Thread.sleep(3000);
        WebElement titleField = driver.findElement(By.id("note-title"));
        WebElement descriptionField = driver.findElement(By.id("note-description"));
        WebElement saveNoteBtn = driver.findElement(By.id("saveNoteBtn"));
        titleField.sendKeys("title");
        descriptionField.sendKeys("description");
        saveNoteBtn.click();
        Thread.sleep(3000);
    }

    public void login(String userName, String password) {
        WebElement usernameField = driver.findElement(By.id("inputUsername"));
        WebElement passwordField = driver.findElement(By.id("inputPassword"));
        usernameField.sendKeys(userName);
        passwordField.sendKeys(password);
        WebElement loginButton = driver.findElement(By.tagName("button"));
        loginButton.click();
    }

    public void goToNotes() {
        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        notesTab.click();
    }

    public void goToSignupPage() {
        WebElement signupButton = driver.findElement(By.id("signup-link"));
        signupButton.click();
    }
}
