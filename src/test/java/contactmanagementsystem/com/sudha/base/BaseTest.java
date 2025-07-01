                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          package contactmanagementsystem.com.sudha.base;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import contactmanagementsystem.com.sudha.util.DriverFactory;
import contactmanagementsystem.com.sudha.util.ExtentListener;
import contactmanagementsystem.com.sudha.util.ReportCleaner;

public class BaseTest {

	protected WebDriver driver;
	private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

	private static ThreadLocal<String> browserName = new ThreadLocal<>();

	@BeforeMethod
	@org.testng.annotations.Parameters("browser")
	public void setUp(ITestResult result, @org.testng.annotations.Optional("chrome") String browser) {
		browserName.set(browser);
		String[] groups = result.getMethod().getGroups();
		if (groups != null && Arrays.asList(groups).contains("api")) {
			logger.info("Skipping browser setup for API test: {}", result.getMethod().getMethodName());
		} else {
			logger.info("Starting test on browser: {}", browser);
			driver = DriverFactory.getDriver(browser);
			ExtentListener.driver = driver;
		}
	}

	public static String getCurrentBrowser() {
		return browserName.get();
	}

	@BeforeSuite
	public void cleanReports() {
		ReportCleaner.cleanDirectory("test-output/screenshots");
		ReportCleaner.cleanDirectory("test-output");
		System.out.println("Old reports and screenshots deleted.");
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			logger.info("Closing browser");
			driver.quit();
		}
	}
}
