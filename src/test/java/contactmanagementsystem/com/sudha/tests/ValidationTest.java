package contactmanagementsystem.com.sudha.tests;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import contactmanagementsystem.com.sudha.base.BaseTest;
import contactmanagementsystem.com.sudha.pages.LoginPage;
import contactmanagementsystem.com.sudha.util.EmojiSafeStringHelper;

@Test(dependsOnMethods = { "contactmanagementsystem.com.sudha.tests.DeleteContactTest.deleteContactAndRefreshTest" })
public class ValidationTest extends BaseTest {

	@Test (description = "Verify Unicode and emojis in address field")
	public void verifyUnicodeAndEmojiInAddress() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();

		driver.findElement(By.id("add-contact")).click();

		// Fill in form fields
		driver.findElement(By.id("firstName")).sendKeys("Sudha");
		driver.findElement(By.id("lastName")).sendKeys("Suresh");
		driver.findElement(By.id("birthdate")).sendKeys("1989-05-23");
		driver.findElement(By.id("email")).sendKeys("sudhasuresh5190+unicode@gmail.com");
		driver.findElement(By.id("phone")).sendKeys("8989898989");

		setInputValueById("street1", "🌟星の道");
		setInputValueById("street2", "🏡 بيت الحي");
		setInputValueById("city", "東京");
		setInputValueById("stateProvince", "Québec");

		driver.findElement(By.id("postalCode")).sendKeys("CB1 9HE");
		setInputValueById("country", "🇬🇧 United Kingdom");

		// Submit
		driver.findElement(By.id("submit")).click();

		// Wait for data to load (wait for Name to appear in table)
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//table[@id='myTable']//td[text()='Sudha Suresh']")));

		// Validate address data in table (assuming it appears concatenated)
		WebElement addressCell = driver.findElement(
				By.xpath("//table[@id='myTable']//tr[td[text()='Sudha Suresh'] and td[text()='1989-05-23']]/td[6]"));

		String expectedAddress = "🌟星の道 🏡 بيت الحي";
		String addressText = addressCell.getText();
		EmojiSafeStringHelper.printCodePoints(addressText);

		Assert.assertTrue(EmojiSafeStringHelper.containsNormalized(addressText, expectedAddress),
				"Address not containing full Unicode value");

	}

	@Test (description="Verify max character limit for contact fields")
	public void verifyFirstNameMaxCharacterLimit() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();

		driver.findElement(By.id("add-contact")).click();

		String longFirstName = "A".repeat(310); // Java 11+ feature
		String longLastName = "B".repeat(310);

		WebElement firstNameField = driver.findElement(By.id("firstName"));
		firstNameField.sendKeys(longFirstName);

		WebElement lastNameField = driver.findElement(By.id("lastName"));
		lastNameField.sendKeys(longLastName);

		driver.findElement(By.id("submit")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
		String errorText = errorElement.getText();
		assertEquals(errorText,
				"Contact validation failed: firstName: Path `firstName` (`AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA`) is longer than the maximum allowed length (20)., lastName: Path `lastName` (`BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB`) is longer than the maximum allowed length (20).");

	}

	private void setInputValueById(String id, String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById(arguments[0]).value = arguments[1];", id, value);
	}

}
