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

package com.company.contactbook.dao;

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
public class JdbcContactDaoTest {
    private ContactDao contactDao;

    @Before
    public void setUp() throws Exception {
        GenericXmlApplicationContext context =
                new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles("jdbc");
        context.load("classpath:/applicationContext.xml");
        context.refresh();
        contactDao = context.getBean(ContactDao.class);
    }

    @Test
    public void testCreateContact() {
        Contact johnDoe = new Contact();
        johnDoe.setFirstname("John");
        johnDoe.setLastname("Doe");

        contactDao.createContact(johnDoe);

        assertNotNull(johnDoe);
        assertNotNull(johnDoe.getId());
    }

    @Test
    public void testSearchContactsByName() {
        Collection<Contact> foundContacts =
                contactDao.searchContactsByFirstname("John");
        assertEquals(0, foundContacts.size());

        Contact johnDoe = new Contact();
        johnDoe.setFirstname("John");
        johnDoe.setLastname("Doe");
        contactDao.createContact(johnDoe);

        foundContacts = contactDao.searchContactsByFirstname("John");
        assertEquals(1, foundContacts.size());

        Contact johnSmith = new Contact();
        johnSmith.setFirstname("John");
        johnSmith.setLastname("Smith");
        contactDao.createContact(johnSmith);

        foundContacts = contactDao.searchContactsByFirstname("John");
        assertEquals(2, foundContacts.size());
    }
}
