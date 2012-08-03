package com.zenika.blog.mybatis.impl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.Test;

import com.zenika.blog.mybatis.AbstractSessionDaoTest;


public class HibernateSessionDaoTest extends AbstractSessionDaoTest {
	
	@Test
	public void shouldFindFormationsWithHibernate() throws Exception {
		EntityManager entityManager = Persistence.createEntityManagerFactory("test").createEntityManager();
		checkSessions( new HibernateSessionDao(entityManager).findAll() );
	}
	
}
