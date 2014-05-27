package com.zenika.blog.mybatis;

import java.util.List;

public interface SessionDao {
	
	/**
	 * Récupère la liste des sessions de formation auxquelles des
	 * stagiaires se sont inscrits.
	 */
	public List<Session> findAll();
}