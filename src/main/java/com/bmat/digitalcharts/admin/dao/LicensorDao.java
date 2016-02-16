package com.bmat.digitalcharts.admin.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Licensor> getLikeName(String name) {
		return sessionFactory.getCurrentSession().createQuery(
				"SELECT l FROM Licensor l WHERE l.name like :filtro")
				.setParameter("filtro", "%" + name + "%").list();
	}

}
