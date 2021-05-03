package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialTab {

    @FindBy(id = "addCredentialBtn")
    private WebElement addCredentialBtn;

    @FindBy(id = "deleteCredentialBtn")
    private WebElement deleteCredentialBtn;

    @FindBy(id = "editCredentialBtn")
    private WebElement editCredentialBtn;

    @FindBy(id = "credential-url")
    private WebElement urlField;

    @FindBy(id = "credential-username")
    private WebElement usernameField;

    @FindBy(id = "credential-password")
    private WebElement passwordField;

    @FindBy(id = "saveCredentialBtn")
    private WebElement saveCredentialBtn;

    @FindBy(className = "table-url")
    private WebElement tableUrl;

    @FindBy(className = "table-username")
    private WebElement tableUsername;

    @FindBy(className = "hidetext")
    private WebElement tablePassword;

    public CredentialTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addCredential(String url, String username, String password) throws InterruptedException {
        addCredentialBtn.click();
        Thread.sleep(3000);
        urlField.sendKeys(url);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        saveCredentialBtn.click();
        Thread.sleep(3000);
    }


    public void editCredential(String url, String username, String password) throws InterruptedException {
        editCredentialBtn.click();
        Thread.sleep(3000);
        urlField.clear();
        usernameField.clear();
        passwordField.clear();
        urlField.sendKeys(url);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        saveCredentialBtn.click();
        Thread.sleep(3000);
    }

    public void deleteCredential() throws InterruptedException {
        deleteCredentialBtn.click();
    }

    public String getTableUrl() {
        return tableUrl.getText();
    }

    public String getTableUsername() {
        return tableUsername.getText();
    }

    public String getTablePassword() {
        return tablePassword.getText();
    }

    public WebElement getTablePasswordElement() {
        return tablePassword;
    }

}
