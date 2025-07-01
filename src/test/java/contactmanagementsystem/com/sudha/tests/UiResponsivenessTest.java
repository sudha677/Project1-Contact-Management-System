package contactmanagementsystem.com.sudha.tests;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import contactmanagementsystem.com.sudha.base.BaseTest;
import contactmanagementsystem.com.sudha.dto.Contact;
import contactmanagementsystem.com.sudha.pages.AddContactsPage;
import contactmanagementsystem.com.sudha.pages.LoginPage;

@Test(dependsOnMethods = { "contactmanagementsystem.com.sudha.tests.LoginTest.validLoginTest" })
public class UiResponsivenessTest extends BaseTest {
	
	@Test (description = "Verify alignment of fields on contact form", groups = "ui")
    public void testFormFieldAlignmentOnDesktop() {
		AddContactsPage addContactsPage = new AddContactsPage(driver);
		addContactsPage.clickAddContact();

        // Check horizontal alignment of text fields (sample check between first and last name)
        int firstNameX = addContactsPage.getFieldX("firstName");
        int lastNameX = addContactsPage.getFieldX("lastName");

        Assert.assertEquals(firstNameX, lastNameX, "Fields are not properly aligned on desktop!");
    }

    @Test (description = "Verify toast messages or success indicators", groups = "ui")
    public void testToastMessageOnAddContact() {
    	AddContactsPage addContactsPage = new AddContactsPage(driver);
		addContactsPage.clickAddContact();


        Contact contact = new Contact("Toast", "Test", "1990-01-01", "toast@test.com", "9876543210",
                "Street 1", "Street 2", "City", "State", "12345", "Country");

        addContactsPage.fillContactForm(contact);
        addContactsPage.submitForm();

        Assert.assertTrue(addContactsPage.isToastMessageDisplayed(), "Success toast not displayed after adding contact.");
    }
	
}
