/*
 * Copyright 2011 Colin Hebert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.company.contactbook.service;

import com.company.contactbook.bean.Contact;
import com.company.contactbook.dao.ContactDao;

import java.util.Collection;

/**
 * @author Colin Hebert
 */
public class ContactServiceImpl implements ContactService {
    private final ContactDao contactDao;

    public ContactServiceImpl(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public Contact createContact(String firstname, String lastname) {
        Contact contact = new Contact();
        contact.setFirstname(firstname);
        contact.setLastname(lastname);
        contactDao.createContact(contact);
        return contact;
    }

    public Collection<Contact> searchContactByName(String firstname) {
        return contactDao.searchContactsByFirstname(firstname);
    }
}
