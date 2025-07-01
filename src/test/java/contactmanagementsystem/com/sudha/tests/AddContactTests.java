package contactmanagementsystem.com.sudha.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import contactmanagementsystem.com.sudha.base.BaseTest;
import contactmanagementsystem.com.sudha.dto.Contact;
import contactmanagementsystem.com.sudha.pages.ContactPage;
import contactmanagementsystem.com.sudha.pages.LoginPage;

@Test(dependsOnMethods = { "contactmanagementsystem.com.sudha.tests.LoginTest.validLoginTest" })
public class AddContactTests extends BaseTest {

	@Test(description = "Verify adding contact with all valid details", groups = "ui")
	public void validAddContactTest() {
		ContactPage contactPage = new ContactPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		WebDriverWait wait = contactPage.clickAddContactButton();
		Contact contact = contactPage.fillAndSubmitContactForm();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myTable")));
		assertTrue(contactPage.isContactPresentInTable(contact));

	}

	@Test(description = "Verify adding contact with missing required fields", groups = "ui")
	public void invalidAddContactTest() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		driver.findElement(By.id("add-contact")).click();
		WebElement submitBtn = driver.findElement(By.id("submit"));
		assertEquals(submitBtn.getText(), "Submit");
		submitBtn.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
		Assert.assertEquals(errorElement.getText(),
				"Contact validation failed: firstName: Path `firstName` is required., lastName: Path `lastName` is required.");

	}

	@Test(description = "Verify phone field accepts only numeric input", groups = "ui")
	public void invalidPhoneNumberTest() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		driver.findElement(By.id("add-contact")).click();

		driver.findElement(By.id("firstName")).sendKeys("FName");
		driver.findElement(By.id("lastName")).sendKeys("LName");
		driver.findElement(By.id("phone")).sendKeys("PHONE");

		WebElement submitBtn = driver.findElement(By.id("submit"));
		assertEquals(submitBtn.getText(), "Submit");
		submitBtn.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
		Assert.assertEquals(errorElement.getText(), "Contact validation failed: phone: Phone number is invalid");

	}
	
	@Test(description = "Verify adding duplicate contact details", groups = "ui")
	public void duplicateContactDetails() {
		ContactPage contactPage = new ContactPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		WebDriverWait wait = contactPage.clickAddContactButton();
		Contact contact = contactPage.fillAndSubmitContactForm();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myTable")));
		assertTrue(contactPage.isContactPresentInTable(contact));
		
		
		ContactPage contactPage2 = new ContactPage(driver);
		LoginPage loginPage2 = new LoginPage(driver);
		loginPage2.openAndLoginWithValidUser();
		WebDriverWait wait2 = contactPage2.clickAddContactButton();
		Contact contact2 = contactPage2.fillAndSubmitContactForm();

		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("myTable")));
		assertTrue(contactPage.isContactPresentInTable(contact2));
		
		if(contact.equals(contact2)){
			
			System.out.println("Duplicate Contact Allowed");
		}
		
		else {
			System.out.println("Duplicate Contact not allowed");
		}

	}

	@Test(description = "Verify form resets after contact is added", groups = "ui")
	public void validateContactFormResetTest() {
		ContactPage contactPage = new ContactPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		contactPage.clickAddContactButton();
		assertTrue(contactPage.isContactFormIsEmpty(), "Contact form is not Empty!");

	}
}
