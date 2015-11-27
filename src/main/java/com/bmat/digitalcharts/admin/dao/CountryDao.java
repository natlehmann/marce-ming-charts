package com.bmat.digitalcharts.admin.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bmat.digitalcharts.admin.model.Country;

@Repository
public class CountryDao extends AbstractEntityDao<Country> {
	
	protected CountryDao() {
		super(Country.class);
	}

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
