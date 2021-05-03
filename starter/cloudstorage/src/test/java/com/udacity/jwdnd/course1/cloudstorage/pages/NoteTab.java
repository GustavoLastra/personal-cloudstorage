package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NoteTab {

    @FindBy(id = "addNoteBtn")
    private WebElement addNoteBtn;

    @FindBy(id = "deleteNoteBtn")
    private WebElement deleteNoteBtn;

    @FindBy(id = "editNoteBtn")
    private WebElement editNoteBtn;

    @FindBy(id = "note-title")
    private WebElement titleField;

    @FindBy(id = "note-description")
    private WebElement descriptionField;

    @FindBy(id = "saveNoteBtn")
    private WebElement saveNoteBtn;

    @FindBy(className = "table-description")
    private WebElement tableDescription;

    @FindBy(className = "table-title")
    private WebElement tableTitle;

    public NoteTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addNote(String title, String description) throws InterruptedException {
        addNoteBtn.click();
        Thread.sleep(3000);
        titleField.sendKeys(title);
        descriptionField.sendKeys(description);
        saveNoteBtn.click();
        Thread.sleep(3000);
    }


    public void editNote(String title, String description) throws InterruptedException {
        editNoteBtn.click();
        Thread.sleep(3000);
        titleField.clear();
        descriptionField.clear();
        titleField.sendKeys(title);
        descriptionField.sendKeys(description);
        saveNoteBtn.click();
        Thread.sleep(3000);
    }

    public void deleteNote() throws InterruptedException {
        deleteNoteBtn.click();
    }

    public String getTableTitle() {
        return tableTitle.getText();
    }

    public String getTableDescription() {
        return tableDescription.getText();
    }

}
