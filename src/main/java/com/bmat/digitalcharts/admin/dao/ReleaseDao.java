package com.bmat.digitalcharts.admin.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bmat.digitalcharts.admin.model.Release;

@Repository
public class ReleaseDao extends AbstractEntityDao<Release> {
	
	@Autowired
	private SessionFactory sessionFactory;

	protected ReleaseDao() {
		super(Release.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
