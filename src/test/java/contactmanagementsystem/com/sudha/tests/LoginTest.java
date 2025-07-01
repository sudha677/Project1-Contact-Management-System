package contactmanagementsystem.com.sudha.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import contactmanagementsystem.com.sudha.base.BaseTest;
import contactmanagementsystem.com.sudha.pages.LoginPage;
import contactmanagementsystem.com.sudha.pages.SignUpPage;

@Test(dependsOnMethods = {"contactmanagementsystem.com.sudha.tests.SignUpTest.validSignUp"})
public class LoginTest extends BaseTest {

	@Test(description = "Validate Header Text", groups = "ui")
	public void validateHeaderText() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openLoginPage();
		String headerText = loginPage.getHeaderText();
		Assert.assertEquals(headerText, "Contact List App");
	}

	@Test(description = "Open SignUp Page")
	public void openSignupPage() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openLoginPage();
		loginPage.clickSignUpButton();
		SignUpPage signUpPage = new SignUpPage(driver);
		String headerText = signUpPage.getHeaderText();
		Assert.assertEquals(headerText, "Add User");

	}

	@Test(description = "Verify login with valid credentials", groups = "ui",   dependsOnMethods = {"contactmanagementsystem.com.sudha.tests.SignUpTest.validSignUp"})
	public void validLoginTest() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();

		Assert.assertTrue(driver.getCurrentUrl().contains("/contactList"),
				"Expected URL to contain /contactList after successful login.");
	}

	@Test(description = "Verify login with incorrect passwor", groups = "ui")
	public void invalidLoginTest() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openLoginPage();

		loginPage.login("invalid@example.com", "wrongpassword");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
		Assert.assertEquals(errorElement.getText(), "Incorrect username or password");
	}
	
	@Test(description = "Verify login with empty fields", groups = "ui")
	public void emptyLoginTest() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openLoginPage();

		loginPage.login("", "");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
		Assert.assertEquals(errorElement.getText(), "Incorrect username or password");
	}
	
	@Test(description = "Verify login with invalid email format", groups = "ui")
	public void invalidEmailLoginTest() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openLoginPage();

		loginPage.login("user.com", "");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
		Assert.assertEquals(errorElement.getText(), "Incorrect username or password");
	}
	
	@Test(description = "Verify password field masks input", groups = "ui")
	public void verifyPasswordFieldMasksInput() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openLoginPage();

		loginPage.login("RandomEmail@example.com", "CheckMaskedPasswordField");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String fieldType = ((WebElement) loginPage.passwordField).getAttribute("type");
        Assert.assertEquals(fieldType, "password", "Password input is not masked!");
	}

}
