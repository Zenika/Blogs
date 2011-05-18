/**
 * 
 */
package com.zenika.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.zenika.domain.Contact;
import com.zenika.repo.ContactRepository;

/**
 * @author Arnaud Cogoluègnes
 *
 */
@Controller
public class ContactController {
	
	@Autowired
	private ContactRepository contactRepository;

	@RequestMapping(value="/contacts/{id}",method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Contact get(@PathVariable Long id) {
		return contactRepository.get(id); 
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="Contact not found")
	public void notFound() { }
	
	
}
