package com.zenika.blog.mybatis.impl.jdbc;

import org.junit.Test;

import com.zenika.blog.mybatis.AbstractSessionDaoTest;


public class JdbcSessionDaoTest extends AbstractSessionDaoTest {
	
	@Test
	public void shouldFindFormationsWithPlainJdbc() throws Exception {
		checkSessions( new JdbcSessionDao().findAll() );
	}
	
}
