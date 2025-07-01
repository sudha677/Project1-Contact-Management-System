package contactmanagementsystem.com.sudha.tests;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.Alert;
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

@Test(dependsOnMethods = { "contactmanagementsystem.com.sudha.tests.EditContactTests.validEditContactTest" })
public class DeleteContactTest extends BaseTest{
	
	@Test(description = "Verify delete confirmation (if applicable)", groups = "ui", priority = 1)
	public void validateAlertText() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		ContactPage contactPage = new ContactPage(driver);
		Contact editedContact = contactPage.contact;
		editedContact.setFirstName("Suresh");
		editedContact.setLastName("Sudha");
		
		contactPage.waitAndValidateTableData(contactPage, editedContact);
		WebElement deleteContactBtn = driver.findElement(By.id("delete"));
		assertEquals(deleteContactBtn.getText(), "Delete Contact");
		deleteContactBtn.click();
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		Assert.assertEquals(alertText, "Are you sure you want to delete this contact?");
		
	}
	
	@Test(description = "Verify contact no longer appears after deletion", groups = "ui", priority = 2)
	public void deleteContactAndRefreshTest() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		ContactPage contactPage = new ContactPage(driver);
		Contact editedContact = contactPage.contact;
		editedContact.setFirstName("Suresh");
		editedContact.setLastName("Sudha");
		
		contactPage.waitAndValidateTableData(contactPage, editedContact);
		WebElement deleteContactBtn = driver.findElement(By.id("delete"));
		assertEquals(deleteContactBtn.getText(), "Delete Contact");
		deleteContactBtn.click();
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		Assert.assertEquals(alertText, "Are you sure you want to delete this contact?");
		alert.accept();
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(driver -> driver.findElements(By.cssSelector("#myTable tbody tr")).isEmpty());
		
		driver.navigate().refresh();
		
		wait.until(driver -> driver.findElements(By.cssSelector("#myTable tbody tr")).isEmpty());
		
		
	}

}
