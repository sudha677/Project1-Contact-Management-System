package contactmanagementsystem.com.sudha.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import contactmanagementsystem.com.sudha.store.TestDataStore;

public class LoginPage {

    private WebDriver driver;

    public By passwordField = By.id("password");
    private By loginButton = By.id("submit");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openLoginPage() {
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
    }

    public void login(String email, String password) {
    	
        driver.findElement( By.id("email")).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
    public String  getHeaderText() {
    	return driver.findElement(By.tagName("h1")).getText();
    }

	public void clickSignUpButton() {
		driver.findElement(By.id("signup")).click();
		
	}
	
	public void openAndLoginWithValidUser() {
		openLoginPage();
		if(TestDataStore.getEmail() == null || TestDataStore.getEmail().trim().length() == 0) {
			TestDataStore.setEmail("ppsMyil8DpO@gmail.com");
		}
		if(TestDataStore.getPassword() == null || TestDataStore.getPassword().trim().length() == 0) {
			TestDataStore.setPassword("Test@12345");
		}
		login(TestDataStore.getEmail(), TestDataStore.getPassword());
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
		
	}
}
