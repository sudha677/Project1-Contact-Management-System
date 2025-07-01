package contactmanagementsystem.com.sudha.dto;

public class Contact {

	private String firstName;
	private String lastName;
	private String birthdate;
	private String email;
	private String phone;
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private String street1;
	private String street2;
	private String city;
	private String stateProvince;
	private String postalCode;
	private String country;

	public Contact(String firstName, String lastName, String birthdate, String email, String phone, String street1,
			String street2, String city, String stateProvince, String postalCode, String country) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.email = email;
		this.phone = phone;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.stateProvince = stateProvince;
		this.postalCode = postalCode;
		this.country = country;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getStreet1() {
		return street1;
	}

	public String getStreet2() {
		return street2;
	}

	public String getCity() {
		return city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCountry() {
		return country;
	}
	
	
}
