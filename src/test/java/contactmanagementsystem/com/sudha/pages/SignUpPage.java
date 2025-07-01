package contactmanagementsystem.com.sudha.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage {

	 private WebDriver driver;

	 public SignUpPage(WebDriver driver) {
	        this.driver = driver;
	    }
	 
	 public void openSignUpPage() {
	        driver.get("https://thinking-tester-contact-list.herokuapp.com/addUser");
	    }
	 
	  public String  getHeaderText() {
	    	return driver.findElement(By.tagName("h1")).getText();
	    }
}
