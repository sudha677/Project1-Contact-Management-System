package contactmanagementsystem.com.sudha.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import contactmanagementsystem.com.sudha.base.BaseTest;
import contactmanagementsystem.com.sudha.pages.SignUpPage;
import contactmanagementsystem.com.sudha.store.TestDataStore;
import contactmanagementsystem.com.sudha.util.UsernameGenerator;

public class SignUpTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(SignUpTest.class);
	@BeforeClass
	public void beforeEachBrowser() {
		TestDataStore.setEmail(UsernameGenerator.generateUsername() + "@gmail.com");
		TestDataStore.setPassword("Test@12345");
	}

	@Test(description = "Verify sign-up with valid inputs", priority = 1, groups = "ui")
	public void validSignUp() {
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.openSignUpPage();
		driver.findElement(By.id("firstName")).sendKeys("Sudha");
		driver.findElement(By.id("lastName")).sendKeys("Suresh");
		driver.findElement(By.id("email")).sendKeys(TestDataStore.getEmail());
		driver.findElement(By.id("password")).sendKeys(TestDataStore.getPassword());
		logger.info("Email: "+ TestDataStore.getEmail());
		String currentUrl = driver.getCurrentUrl();

		driver.findElement(By.id("submit")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
		WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
		Assert.assertEquals(heading.getText(), "Contact List");

		WebElement logoutBtn = driver.findElement(By.id("logout"));
		Assert.assertEquals(logoutBtn.getText(), "Logout");
		logoutBtn.click();
		
		
		
	

	}

	@Test(description = "Verify registration with already registered email", priority = 2, groups = "ui")
	public void inValidSignUp() {
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.openSignUpPage();
		driver.findElement(By.id("firstName")).sendKeys("Sudha");
		driver.findElement(By.id("lastName")).sendKeys("Suresh");
		driver.findElement(By.id("email")).sendKeys("sudhasuresh@gmail.com");
		driver.findElement(By.id("password")).sendKeys(TestDataStore.getPassword());

		driver.findElement(By.id("submit")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));

		String headerText = signUpPage.getHeaderText();
		Assert.assertEquals(headerText, "Add User");

		Assert.assertEquals(errorElement.getText(), "Email address is already in use");

	}

	@Test(description = "Verify registration with blank fields", priority = 3, groups = "ui")
	public void blankSignUp() {
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.openSignUpPage();

		driver.findElement(By.id("submit")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));

		String headerText = signUpPage.getHeaderText();
		Assert.assertEquals(headerText, "Add User");

		Assert.assertEquals(errorElement.getText(),
				"User validation failed: firstName: Path `firstName` is required., lastName: Path `lastName` is required., email: Email is invalid, password: Path `password` is required.");

	}

	@Test(description = "Verify password and confirm password mismatch", priority = 4, groups = "ui")
	public void invalidSignUpPasswordDoNotMatch() {
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.openSignUpPage();
		driver.findElement(By.id("firstName")).sendKeys("Sudha");
		driver.findElement(By.id("lastName")).sendKeys("Suresh");
		driver.findElement(By.id("email")).sendKeys(TestDataStore.getEmail());
		driver.findElement(By.id("password")).sendKeys("asdf");
		driver.findElement(By.id("submit")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));

		String headerText = signUpPage.getHeaderText();
		Assert.assertEquals(headerText, "Add User");

		Assert.assertEquals(errorElement.getText(),
				"User validation failed: password: Path `password` (`asdf`) is shorter than the minimum allowed length (7).");

	}

	@Test(description = "Verify email format validation during sign-up", priority = 5, groups = "ui")
	public void invalidSignUpWrongEmailId() {
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.openSignUpPage();
		driver.findElement(By.id("firstName")).sendKeys("Sudha");
		driver.findElement(By.id("lastName")).sendKeys("Suresh");
		driver.findElement(By.id("email")).sendKeys("name@name");
		driver.findElement(By.id("password")).sendKeys(TestDataStore.getPassword());
		driver.findElement(By.id("submit")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));

		String headerText = signUpPage.getHeaderText();
		Assert.assertEquals(headerText, "Add User");

		Assert.assertEquals(errorElement.getText(), "User validation failed: email: Email is invalid");

	}

}
