package com.zenika.blog.mybatis.impl.mybatis;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.h2.util.IOUtils;
import org.junit.Test;

import com.zenika.blog.mybatis.AbstractSessionDaoTest;
import com.zenika.blog.mybatis.SessionDao;


public class MyBatisSessionDaoTest extends AbstractSessionDaoTest {
	
	@Test
	public void shouldFindFormationsWithMyBatis() throws Exception {
		
		InputStream stream = null;
		
		try {
			stream = Resources.getResourceAsStream("mybatis.xml");
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(stream);
			SqlSession session = sessionFactory.openSession();
			checkSessions( session.getMapper(SessionDao.class).findAll() );
		}
		finally {
			IOUtils.closeSilently(stream);
		}
	}
	
}
