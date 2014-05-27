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

package com.company.contactbook.dao.impl;

import com.company.contactbook.bean.Contact;
import com.company.contactbook.dao.ContactDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * JDBC implementation of ContactDao
 *
 * @author Colin Hebert
 */
public class JdbcContactDao implements ContactDao {
    private final DataSource dataSource;

    public JdbcContactDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createContact(Contact contact) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO contacts (firstname, lastname, phonenumber) "
                            + "VALUES ( ?, ?, ?)");
            statement.setString(1, contact.getFirstname());
            statement.setString(2, contact.getLastname());
            statement.setString(3, contact.getPhoneNumber());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);
            contact.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Contact findContact(int id) {
        Contact foundContact = null;

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM contacts WHERE id = ?");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null) {
                resultSet.next();
                foundContact = new Contact();
                foundContact.setId(resultSet.getInt("id"));
                foundContact.setFirstname(resultSet.getString("firstname"));
                foundContact.setLastname(resultSet.getString("lastname"));
                foundContact.setPhoneNumber(resultSet.getString("phonenumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return foundContact;
    }

    public Collection<Contact> searchContactsByFirstname(String firstname) {
        Collection<Contact> foundContacts = new ArrayList<Contact>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM contacts WHERE firstname = ?");
            statement.setString(1, firstname);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Contact foundContact = new Contact();
                    foundContact.setId(resultSet.getInt("id"));
                    foundContact.setFirstname(resultSet.getString("firstname"));
                    foundContact.setLastname(resultSet.getString("lastname"));
                    foundContact.setPhoneNumber(
                            resultSet.getString("phoneNumber"));
                    foundContacts.add(foundContact);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return foundContacts;
    }
}
