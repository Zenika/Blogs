/**
 * 
 */
package com.zenika.domain;

/**
 * @author Arnaud Cogoluègnes
 *
 */
public class Contact {

	private Long id;
	
	private String firstname,lastname;
	
	public Contact() { }

	public Contact(Long id, String firstname, String lastname) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
	
}
