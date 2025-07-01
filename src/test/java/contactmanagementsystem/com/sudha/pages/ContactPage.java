package contactmanagementsystem.com.sudha.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import contactmanagementsystem.com.sudha.dto.Contact;

public class ContactPage {

	private WebDriver driver;
	
	public String[] fields = { "firstName", "lastName", "birthdate", "email", "phone", "street1", "street2", "city",
			"stateProvince", "postalCode", "country" };

	public ContactPage(WebDriver driver) {
		this.driver = driver;
	}

	public Contact contact = new Contact("Sudha", "Suresh", "1989-05-23", "sudhasuresh5190@gmail.com", "8989898989",
			"3 Headington Drive", "Cherryhinton", "Cambridge", "Cambridgeshire", "CB1 9HE", "United Kingdom");

	public Contact fillAndSubmitContactForm() {
		assertTrue(isContactFormIsEmpty(), "Contact form is not Empty!");

		driver.findElement(By.id("firstName")).sendKeys(contact.getFirstName());
		driver.findElement(By.id("lastName")).sendKeys(contact.getLastName());
		driver.findElement(By.id("birthdate")).sendKeys(contact.getBirthdate());
		driver.findElement(By.id("email")).sendKeys(contact.getEmail());
		driver.findElement(By.id("phone")).sendKeys(contact.getPhone());
		driver.findElement(By.id("street1")).sendKeys(contact.getStreet1());
		driver.findElement(By.id("street2")).sendKeys(contact.getStreet2());
		driver.findElement(By.id("city")).sendKeys(contact.getCity());
		driver.findElement(By.id("stateProvince")).sendKeys(contact.getStateProvince());
		driver.findElement(By.id("postalCode")).sendKeys(contact.getPostalCode());
		driver.findElement(By.id("country")).sendKeys(contact.getCountry());
		WebElement submitBtn = driver.findElement(By.id("submit"));
		assertEquals(submitBtn.getText(), "Submit");
		submitBtn.click();
		return contact;
	}

	public boolean isContactFormIsEmpty() {
		return driver.findElement(By.id("firstName")).getAttribute("value").isEmpty()
				&& driver.findElement(By.id("lastName")).getAttribute("value").isEmpty()
				&& driver.findElement(By.id("birthdate")).getAttribute("value").isEmpty()
				&& driver.findElement(By.id("email")).getAttribute("value").isEmpty()
				&& driver.findElement(By.id("phone")).getAttribute("value").isEmpty()
				&& driver.findElement(By.id("street1")).getAttribute("value").isEmpty()
				&& driver.findElement(By.id("street2")).getAttribute("value").isEmpty()
				&& driver.findElement(By.id("city")).getAttribute("value").isEmpty()
				&& driver.findElement(By.id("stateProvince")).getAttribute("value").isEmpty()
				&& driver.findElement(By.id("postalCode")).getAttribute("value").isEmpty()
				&& driver.findElement(By.id("country")).getAttribute("value").isEmpty();

	}

	public WebDriverWait clickAddContactButton() {
		driver.findElement(By.id("add-contact")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
		String headerText = driver.findElement(By.tagName("h1")).getText();
		assertEquals(headerText, "Add Contact");
		return wait;
	}

	public boolean isContactPresentInTable(Contact contact) {
		String xpath = "//table[@id='myTable']//tr" + "[td[text()='" + contact.getFirstName() + " "
				+ contact.getLastName() + "']" + " and td[text()='" + contact.getBirthdate() + "']" + " and td[text()='"
				+ contact.getEmail() + "']" + " and td[text()='" + contact.getPhone() + "']" + " and td[text()='"
				+ contact.getStreet1() + " " + contact.getStreet2() + "']" + " and td[text()='" + contact.getCity()
				+ " " + contact.getStateProvince() + " " + contact.getPostalCode() + "']" + " and td[text()='"
				+ contact.getCountry() + "']]";

		List<WebElement> rows = driver.findElements(By.xpath(xpath));
		return rows.size() > 0;
	}
	
	public void waitAndValidateTableData(ContactPage contactPage, Contact contact) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myTable")));
		assertTrue(contactPage.isContactPresentInTable(contact));

		WebElement cell = driver.findElement(By.xpath("//table[@id='myTable']//tr[1]/td[2]"));
		cell.click();

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		for (String id : fields) {
			wait.until(driver -> !driver.findElement(By.id(id)).getText().trim().isEmpty());
		}
		verifyContactDetails(contact);
	}

	public void waitAndValidateContactData(WebDriverWait wait, Contact contact) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
		WebElement editContactHeader = driver.findElement(By.tagName("h1"));
		assertEquals(editContactHeader.getText(), "Edit Contact");
		verifyEditContactForm(contact);
	}

	public void verifyContactDetails(Contact contact) {

		Assert.assertEquals(driver.findElement(By.id("firstName")).getText(), contact.getFirstName(),
				"First Name mismatch");
		Assert.assertEquals(driver.findElement(By.id("lastName")).getText(), contact.getLastName(),
				"Last Name mismatch");
		Assert.assertEquals(driver.findElement(By.id("birthdate")).getText(), contact.getBirthdate(),
				"Birthdate mismatch");
		Assert.assertEquals(driver.findElement(By.id("email")).getText(), contact.getEmail(), "Email mismatch");
		Assert.assertEquals(driver.findElement(By.id("phone")).getText(), contact.getPhone(), "Phone mismatch");
		Assert.assertEquals(driver.findElement(By.id("street1")).getText(), contact.getStreet1(), "Street1 mismatch");
		Assert.assertEquals(driver.findElement(By.id("street2")).getText(), contact.getStreet2(), "Street2 mismatch");
		Assert.assertEquals(driver.findElement(By.id("city")).getText(), contact.getCity(), "City mismatch");
		Assert.assertEquals(driver.findElement(By.id("stateProvince")).getText(), contact.getStateProvince(),
				"State mismatch");
		Assert.assertEquals(driver.findElement(By.id("postalCode")).getText(), contact.getPostalCode(),
				"Postal Code mismatch");
		Assert.assertEquals(driver.findElement(By.id("country")).getText(), contact.getCountry(), "Country mismatch");
	}

	public void verifyEditContactForm(Contact contact) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		for (String id : fields) {
			wait.until(driver -> !driver.findElement(By.id(id)).getAttribute("value").trim().isEmpty());
		}
		Assert.assertEquals(driver.findElement(By.id("firstName")).getAttribute("value"), contact.getFirstName(),
				"First Name mismatch");
		Assert.assertEquals(driver.findElement(By.id("lastName")).getAttribute("value"), contact.getLastName(),
				"Last Name mismatch");
		Assert.assertEquals(driver.findElement(By.id("birthdate")).getAttribute("value"), contact.getBirthdate(),
				"Birthdate mismatch");
		Assert.assertEquals(driver.findElement(By.id("email")).getAttribute("value"), contact.getEmail(),
				"Email mismatch");
		Assert.assertEquals(driver.findElement(By.id("phone")).getAttribute("value"), contact.getPhone(),
				"Phone mismatch");
		Assert.assertEquals(driver.findElement(By.id("street1")).getAttribute("value"), contact.getStreet1(),
				"Street1 mismatch");
		Assert.assertEquals(driver.findElement(By.id("street2")).getAttribute("value"), contact.getStreet2(),
				"Street2 mismatch");
		Assert.assertEquals(driver.findElement(By.id("city")).getAttribute("value"), contact.getCity(),
				"City mismatch");
		Assert.assertEquals(driver.findElement(By.id("stateProvince")).getAttribute("value"),
				contact.getStateProvince(), "StateProvince mismatch");
		Assert.assertEquals(driver.findElement(By.id("postalCode")).getAttribute("value"), contact.getPostalCode(),
				"Postal Code mismatch");
		Assert.assertEquals(driver.findElement(By.id("country")).getAttribute("value"), contact.getCountry(),
				"Country mismatch");
	}
}
