package com.bmat.digitalcharts.admin.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

	@SuppressWarnings("unchecked")
	@Transactional
	public List<LabelCompany> getLikeName(String labelCompanyName) {
		return sessionFactory.getCurrentSession().createQuery(
				"SELECT l FROM LabelCompany l WHERE l.name like :filtro")
				.setParameter("filtro", "%" + labelCompanyName + "%").list();
	}

}
