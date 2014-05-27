package com.zenika.blog.mybatis.impl.jdbctemplate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zenika.blog.mybatis.AbstractSessionDaoTest;
import com.zenika.blog.mybatis.SessionDao;


public class JdbcTemplateSessionDaoTest extends AbstractSessionDaoTest {
	@Autowired
	SessionDao formationDao;
	
	@Test
	public void shouldFindFormationsWithJdbcTemplate() throws Exception {
		checkSessions( formationDao.findAll() );
	}
}
