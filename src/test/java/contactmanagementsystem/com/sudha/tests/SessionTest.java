package contactmanagementsystem.com.sudha.tests;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import contactmanagementsystem.com.sudha.base.BaseTest;
import contactmanagementsystem.com.sudha.pages.LoginPage;

@Test(dependsOnMethods = { "contactmanagementsystem.com.sudha.tests.LoginTest.validLoginTest" })
public class SessionTest extends BaseTest {

	@Test(description = "Verify logout redirects to login page", groups = "ui")
	public void verifyLogoutRedirectToLogin() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		WebElement logoutBtn = driver.findElement(By.id("logout"));
		assertEquals(logoutBtn.getText(), "Logout");
		logoutBtn.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup")));
		WebElement signUpBtn = driver.findElement(By.id("signup"));
		assertEquals(signUpBtn.getText(), "Sign up");
		assertEquals(loginPage.getHeaderText(), "Contact List App");
	}

	@Test(description = "Verify Login State on Refresh", groups = "ui")
	public void verifyLoginStateOnRefresh() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openAndLoginWithValidUser();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-contact")));
		String headerText = driver.findElement(By.tagName("h1")).getText();
		assertEquals(headerText, "Contact List");

		driver.navigate().refresh();

		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-contact")));
		WebElement addContactBtn = driver.findElement(By.id("add-contact"));
		assertEquals(addContactBtn.getText(), "Add a New Contact");
		headerText = driver.findElement(By.tagName("h1")).getText();
		assertEquals(headerText, "Contact List");
	}
}
