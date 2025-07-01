package contactmanagementsystem.com.sudha.tests;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import contactmanagementsystem.com.sudha.base.BaseTest;
import contactmanagementsystem.com.sudha.dto.Contact;
import contactmanagementsystem.com.sudha.pages.ContactPage;
import contactmanagementsystem.com.sudha.pages.LoginPage;

@Test(dependsOnMethods = { "contactmanagementsystem.com.sudha.tests.LoginTest.validLoginTest", "contactmanagementsystem.com.sudha.tests.AddContactTests.validAddContactTest" })
public class EditContactTests extends BaseTest {
	
	Contact editedContact = null;

	@Test(description = "Verify user can edit an existing contact", groups = "ui")
	public void validEditContactTest() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		ContactPage contactPage = new ContactPage(driver);
		Contact contact = contactPage.contact;

		contactPage.waitAndValidateTableData(contactPage, contact);
		WebElement editContactBtn = driver.findElement(By.id("edit-contact"));
		assertEquals(editContactBtn.getText(), "Edit Contact");
		editContactBtn.click();

		contactPage.waitAndValidateContactData(wait, contact);
		editedContact = contact;
		editedContact.setFirstName("Suresh");
		editedContact.setLastName("Sudha");

		driver.findElement(By.id("firstName")).clear();
		driver.findElement(By.id("firstName")).sendKeys(editedContact.getFirstName());

		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys(editedContact.getLastName());

		WebElement submitBtn = driver.findElement(By.id("submit"));
		assertEquals(submitBtn.getText(), "Submit");
		submitBtn.click();

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		for (String id : contactPage.fields) {
			wait.until(driver -> !driver.findElement(By.id(id)).getText().trim().isEmpty());
		}
		contactPage.verifyContactDetails(contact);
	}

	@Test(description = "Verify canceling an edit (if implemented)", groups = "ui", dependsOnMethods = { "validEditContactTest" })
	public void validCancelEditTest() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		ContactPage contactPage = new ContactPage(driver);

		contactPage.waitAndValidateTableData(contactPage, editedContact);
		WebElement editContactBtn = driver.findElement(By.id("edit-contact"));
		assertEquals(editContactBtn.getText(), "Edit Contact");
		editContactBtn.click();

		contactPage.waitAndValidateContactData(wait, editedContact);

		WebElement cancelBtn = driver.findElement(By.id("cancel"));
		assertEquals(cancelBtn.getText(), "Cancel");
		cancelBtn.click();

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		for (String id : contactPage.fields) {
			wait.until(driver -> !driver.findElement(By.id(id)).getText().trim().isEmpty());
		}
		contactPage.verifyContactDetails(editedContact);
		WebElement returnToContactBtn = driver.findElement(By.id("return"));
		assertEquals(returnToContactBtn.getText(), "Return to Contact List");
		returnToContactBtn.click();
		contactPage.waitAndValidateTableData(contactPage, editedContact);
	}

	@Test(description = "Validation Check Edit Contact", groups = "ui", dependsOnMethods = { "validEditContactTest" })
	public void valididationCheckEditTest() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		ContactPage contactPage = new ContactPage(driver);

		contactPage.waitAndValidateTableData(contactPage, editedContact);
		WebElement editContactBtn = driver.findElement(By.id("edit-contact"));
		assertEquals(editContactBtn.getText(), "Edit Contact");
		editContactBtn.click();

		contactPage.waitAndValidateContactData(wait, editedContact);

		driver.findElement(By.id("lastName")).clear();

		WebElement submitBtn = driver.findElement(By.id("submit"));
		assertEquals(submitBtn.getText(), "Submit");
		submitBtn.click();

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
		String errorText = driver.findElement(By.id("error")).getText();
		assertEquals(errorText, "Validation failed: lastName: Path `lastName` is required.");
	}

	

}
