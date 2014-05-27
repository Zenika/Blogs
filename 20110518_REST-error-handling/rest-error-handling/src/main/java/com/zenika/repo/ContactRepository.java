/**
 * 
 */
package com.zenika.repo;

import com.zenika.domain.Contact;

/**
 * @author Arnaud Cogoluègnes
 *
 */
public interface ContactRepository {

	Contact get(Long id);
	
}
