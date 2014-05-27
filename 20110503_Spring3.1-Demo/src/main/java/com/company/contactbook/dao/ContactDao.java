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

import java.util.Collection;

/**
 * @author Colin Hebert
 */
public interface ContactDao {
    /**
     * Creates and store a contact.
     *
     * @param contact Contact to store.
     */
    void createContact(Contact contact);

    /**
     * Finds a contact by its id.
     *
     * @param id Contact's id.
     * @return Contact with the matching id or null if no contact was found.
     */
    Contact findContact(int id);

    /**
     * Finds every contact with the same firstname.
     *
     * @param firstname Contacts' firstname.
     * @return A collection with every contact with the given name. The
     *         collection is empty if no contact was found.
     */
    Collection<Contact> searchContactsByFirstname(String firstname);
}
