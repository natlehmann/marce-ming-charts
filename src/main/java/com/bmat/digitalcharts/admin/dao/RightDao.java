package com.bmat.digitalcharts.admin.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bmat.digitalcharts.admin.model.Right;

@Repository
public class RightDao extends AbstractEntityDao<Right> {
	
	@Autowired
	private SessionFactory sessionFactory;

	public RightDao() {
		super(Right.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
