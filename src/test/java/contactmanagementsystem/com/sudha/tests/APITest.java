package contactmanagementsystem.com.sudha.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import contactmanagementsystem.com.sudha.base.BaseTest;
import contactmanagementsystem.com.sudha.store.TestDataStore;
import contactmanagementsystem.com.sudha.util.UserManagementUtil;
import io.restassured.response.Response;

public class APITest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(APITest.class);

	@Test(description = "Delete User API Test", groups = "api", dependsOnGroups = { "ui" })
	public void testDeleteUserAPI() {

		Response response = UserManagementUtil.deleteUser(TestDataStore.getEmail(), TestDataStore.getPassword());
		if (response != null && response.statusCode() == 200) {
			logger.info("User Deleted Successfully!");
		} else {
			logger.error("User Deletion Failed!");
		}

	}
}
