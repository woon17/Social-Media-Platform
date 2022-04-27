package com.dxc.smp.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "user")
public class User {

	@Id
	private String userName;
	@Column(name = "userFirstName")
	private String userFirstName;
	@Column(name = "userLastName")
	private String userLastName;
	@Column(name = "userPassword")
	private String userPassword;

	// many user may have many roles
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	private Set<Role> role; // one user may have many roles

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Post> posts;

	public User(String userName, String userFirstName, String userLastName, String userPassword) {
		super();
		this.userName = userName;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
	}

	public User() {
	}

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

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public void add(Post temPost) {
		if (this.posts == null) {
			this.posts = new ArrayList<>();

		} else {
			this.posts.add(temPost);
			temPost.setUser(this);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			return this.userName.equals(((User) o).getUserName());
		} else {
			return false;
		}

	}

}