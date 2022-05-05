package com.dxc.smp.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class UpdateUserRequest {

	@NotBlank
	@Length(min = 3, max = 8, message = "min 3 and max 8")
	@Pattern(regexp = "[a-z0-9]*", message = "only lower case letter and digits")
	private String userName;

	@NotBlank
	@Length(min = 3, max = 8, message = "min 3 and max 8")
	@Pattern(regexp = "[a-zA-Z]*", message = "lower/uppder case letter")
	private String userFirstName;

	@NotBlank
	@Length(min = 3, max = 8, message = "min 3 and max 8")
	@Pattern(regexp = "[a-zA-Z]*", message = "lower/uppder case letter")
	private String userLastName;

	@Length(min = 0, max = 8, message = "min 0 and max 8")
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

	@Override
	public String toString() {
		return "updateUserRequest [userName=" + userName + ", userFirstName=" + userFirstName + ", userLastName="
				+ userLastName + ", userPassword=" + userPassword + "]";
	}
	
	
}
