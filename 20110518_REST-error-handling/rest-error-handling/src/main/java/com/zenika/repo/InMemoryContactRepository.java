/**
 * 
 */
package com.zenika.repo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.zenika.domain.Contact;


/**
 * @author Arnaud Cogoluègnes
 *
 */
@Repository
public class InMemoryContactRepository implements ContactRepository {

	private final Map<Long, Contact> contacts = new HashMap<Long, Contact>() {{
		put(1L,new Contact(1L,"Arnaud","Cogoluegnes"));
	}};
	
	@Override
	public Contact get(Long id) {
		Contact contact = contacts.get(id);
		if(contact == null) {
			throw new EmptyResultDataAccessException(1);
		} else {
			return contact;
		}
	}
	
}
