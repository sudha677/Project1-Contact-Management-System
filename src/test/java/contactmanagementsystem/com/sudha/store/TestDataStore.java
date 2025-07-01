package contactmanagementsystem.com.sudha.store;

public class TestDataStore {

	private static ThreadLocal<String> email = new ThreadLocal<>();
	private static ThreadLocal<String> password = new ThreadLocal<>();

	public static void setEmail(String value) {
		email.set(value);
	}

	public static String getEmail() {
		return email.get();
	}

	public static void setPassword(String value) {
		password.set(value);
	}

	public static String getPassword() {
		return password.get();
	}
}
