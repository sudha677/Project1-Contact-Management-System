package contactmanagementsystem.com.sudha.util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserManagementUtil {

	private static final String BASE_URL = "https://thinking-tester-contact-list.herokuapp.com";

	/**
	 * Logs in with username and password, then deletes that user.
	 *
	 * @param username the user’s username
	 * @param password the user’s password
	 * @return delete API response
	 */
	public static Response deleteUser(String username, String password) {
		// 1. Login to get token
		LoginPayload payload = new LoginPayload(username, password);
		Response loginResp = RestAssured.given().baseUri(BASE_URL).basePath("/users/login").contentType(ContentType.JSON)
				.body(payload).when().post();

		if (loginResp.statusCode() != 200) {
			throw new RuntimeException("Login failed: " + loginResp.statusLine());
		}

		String token = loginResp.jsonPath().getString("token");

		// 2. Delete user using token
		Response deleteResp = RestAssured.given().baseUri(BASE_URL).basePath("/user/delete")
				.contentType(ContentType.JSON).header("Authorization", "Bearer " + token).when().delete();

		return deleteResp;
	}

	public static class LoginPayload {
		private String email;
		private String password;

		public LoginPayload(String email, String password) {
			this.email = email;
			this.password = password;
		}

		public String getEmail() {
			return email;
		}

		public String getPassword() {
			return password;
		}
	}
}