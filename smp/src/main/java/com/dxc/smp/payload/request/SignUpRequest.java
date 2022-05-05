package com.dxc.smp.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class SignUpRequest {

	@NotBlank
	@Length(min = 3, max = 8, message = "min 3 and max 8")
	@Pattern(regexp = "[a-z0-9]", message = "only lower case letter, digits")
	private String userName;

	@NotBlank
	@Length(min = 3, max = 8, message = "min 3 and max 8")
	@Pattern(regexp = "[a-z0-9]", message = "lower/uppder case letter")
	private String userFirstName;

	@NotBlank
	@Length(min = 3, max = 8, message = "min 3 and max 8")
	@Pattern(regexp = "[a-z0-9]", message = "lower/uppder case letter")
	private String userLastName;

	@NotBlank
	@Length(min = 5, max = 10, message = "min 5 and max 10")
	@Pattern(regexp = "(?=\\D*\\d)(?!.* )(?=[^a-z]*[a-z])(?=[^A-Z]*[A-Z]).{5,10}", message = "Invalid pattern")
	// At least one digit
	// At least one upper case ASCII letter
	// At least one lower case ASCII letter
	// 5-10 chars
	// no white space
	private String userPassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
