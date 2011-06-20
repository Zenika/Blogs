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
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Colin Hebert
 */
public class ContactServiceImplTest {
    private ContactService contactService;

    @Before
    public void setUp() {
        GenericXmlApplicationContext context =
                new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles("memory");
        context.load("classpath:/applicationContext.xml");
        context.refresh();
        contactService = context.getBean(ContactService.class);
    }

    @Test
    public void testCreateContact() {
        String firstname = "John", lastname = "Doe";

        Contact johnDoe = contactService.createContact(firstname, lastname);
        assertNotNull(johnDoe);
        assertNotNull(johnDoe.getId());
    }

    @Test
    public void testSearchContactsByName() {
        String firstname = "John";
        String[] lastnames = {"Doe", "Smith"};
        for (String lastname : lastnames) {
            contactService.createContact(firstname, lastname);
        }

        Collection<Contact> foundContacts = contactService.searchContactByName(
                firstname);

        assertEquals(2, foundContacts.size());
    }
}
