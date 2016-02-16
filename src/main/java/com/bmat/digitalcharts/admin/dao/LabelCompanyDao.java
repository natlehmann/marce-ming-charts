package com.bmat.digitalcharts.admin.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bmat.digitalcharts.admin.model.LabelCompany;

@Repository
public class LabelCompanyDao extends AbstractEntityDao<LabelCompany> {

	protected LabelCompanyDao() {
		super(LabelCompany.class);
	}

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
