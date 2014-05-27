package com.zenika.blog.mybatis.impl.jdbctemplate;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zenika.blog.mybatis.Session;
import com.zenika.blog.mybatis.SessionDao;

public class JdbcTemplateSessionDao implements SessionDao {

	private static final String FIND_ALL = 
			"select fo.id as id_formation, co.nom as nom_cours, fe.nom as nom_formateur, st.id as id_stagiaire, st.nom as nom_stagiaire " +
			"from formation fo, cours co, formateur fe, stagiaire st " +
			"where co.id = fo.id_cours and fe.id = fo.id_formateur and fo.id = st.id_formation " +
			"order by id_formation";
	
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Session> findAll() {
		SessionRowCallbackHandler handler = new SessionRowCallbackHandler();
		jdbcTemplate.query(FIND_ALL, handler);
		return handler.getSessions();
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
