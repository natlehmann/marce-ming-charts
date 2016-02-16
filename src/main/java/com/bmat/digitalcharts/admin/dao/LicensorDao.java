package com.bmat.digitalcharts.admin.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bmat.digitalcharts.admin.model.Licensor;

@Repository
public class LicensorDao extends AbstractEntityDao<Licensor> {

	protected LicensorDao() {
		super(Licensor.class);
	}

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
