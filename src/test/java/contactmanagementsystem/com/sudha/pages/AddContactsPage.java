package contactmanagementsystem.com.sudha.pages;

import contactmanagementsystem.com.sudha.dto.Contact;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class AddContactsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public AddContactsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // === Locators ===
    private By addContactButton = By.id("add-contact");
    private By header = By.tagName("h1");
    private By submitButton = By.id("submit");
    private By table = By.id("myTable");

    // Form field locators
    private By firstName = By.id("firstName");
    private By lastName = By.id("lastName");
    private By birthdate = By.id("birthdate");
    private By email = By.id("email");
    private By phone = By.id("phone");
    private By street1 = By.id("street1");
    private By street2 = By.id("street2");
    private By city = By.id("city");
    private By stateProvince = By.id("stateProvince");
    private By postalCode = By.id("postalCode");
    private By country = By.id("country");

    // === Actions ===

    public WebDriverWait clickAddContact() {
        driver.findElement(addContactButton).click();
        wait.until(ExpectedConditions.textToBe(header, "Add Contact"));
		return wait;
    }

    public boolean isFormEmpty() {
        return getFieldValue(firstName).isEmpty() &&
               getFieldValue(lastName).isEmpty() &&
               getFieldValue(birthdate).isEmpty() &&
               getFieldValue(email).isEmpty() &&
               getFieldValue(phone).isEmpty() &&
               getFieldValue(street1).isEmpty() &&
               getFieldValue(street2).isEmpty() &&
               getFieldValue(city).isEmpty() &&
               getFieldValue(stateProvince).isEmpty() &&
               getFieldValue(postalCode).isEmpty() &&
               getFieldValue(country).isEmpty();
    }

    public void fillContactForm(Contact contact) {
        type(firstName, contact.getFirstName());
        type(lastName, contact.getLastName());
        type(birthdate, contact.getBirthdate());
        type(email, contact.getEmail());
        type(phone, contact.getPhone());
        type(street1, contact.getStreet1());
        type(street2, contact.getStreet2());
        type(city, contact.getCity());
        type(stateProvince, contact.getStateProvince());
        type(postalCode, contact.getPostalCode());
        type(country, contact.getCountry());
    }

    public void submitForm() {
        WebElement btn = driver.findElement(submitButton);
        if (!btn.getText().equals("Submit")) throw new IllegalStateException("Submit button text mismatch");
        btn.click();
    }

    public boolean isContactInTable(Contact contact) {
        String fullName = contact.getFirstName() + " " + contact.getLastName();
        String fullAddress = contact.getStreet1() + " " + contact.getStreet2();
        String cityInfo = contact.getCity() + " " + contact.getStateProvince() + " " + contact.getPostalCode();

        String xpath = String.format("//table[@id='myTable']//tr[td[text()='%s'] and td[text()='%s'] and td[text()='%s'] and td[text()='%s'] and td[text()='%s'] and td[text()='%s']]",
                fullName, contact.getBirthdate(), contact.getEmail(), contact.getPhone(), fullAddress, cityInfo);

        return driver.findElements(By.xpath(xpath)).size() > 0;
    }

    public void verifyContactDetailsOnViewPage(Contact contact) {
        wait.until(ExpectedConditions.textToBe(header, "Edit Contact"));
        assertEquals(getText(firstName), contact.getFirstName(), "First Name mismatch");
        assertEquals(getText(lastName), contact.getLastName(), "Last Name mismatch");
        // ... repeat for all fields using getText(...)
    }

    public void verifyContactDetailsOnEditPage(Contact contact) {
        wait.until(ExpectedConditions.textToBe(header, "Edit Contact"));
        assertEquals(getFieldValue(firstName), contact.getFirstName(), "First Name mismatch");
        assertEquals(getFieldValue(lastName), contact.getLastName(), "Last Name mismatch");
        // ... repeat for all fields using getFieldValue(...)
    }

    // === Helper methods ===

    private void type(By locator, String value) {
        WebElement el = driver.findElement(locator);
        el.clear();
        el.sendKeys(value);
    }

    private String getText(By locator) {
        return driver.findElement(locator).getText().trim();
    }

    private String getFieldValue(By locator) {
        return driver.findElement(locator).getAttribute("value").trim();
    }

    private void assertEquals(String actual, String expected, String message) {
        if (!actual.equals(expected)) {
            throw new AssertionError(message + ": expected [" + expected + "], but found [" + actual + "]");
        }
    }
    
    private By toastMessage = By.className("toast-message"); // or update based on actual class/id

    public boolean isToastMessageDisplayed() {
        try {
            WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(toastMessage));
            return toast.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    public int getFieldX(String fieldId) {
        return driver.findElement(By.id(fieldId)).getLocation().getX();
    }
}