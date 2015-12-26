package com.bmat.digitalcharts.admin.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.model.Country;
import com.bmat.digitalcharts.admin.model.RestSource;
import com.bmat.digitalcharts.admin.model.Right;

@Repository
public class RestSourceDao {
	
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(RestSourceDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;


	@SuppressWarnings("unchecked")
	@Transactional
	public List<RestSource> getSources(List<Long> ids, Date dateFrom,
			Date dateTo, Country country, Right right) {

		return sessionFactory.getCurrentSession().createQuery(
				"SELECT DISTINCT(u.restSource) FROM Usage u WHERE u.chartDate between :dateFrom and :dateTo "
				+ "and u.right.id = :rightId "
				+ "and u.country.id = :countryId "
				+ "AND u.track.song.id IN (:songIds)")
				.setParameter("dateFrom", dateFrom)
				.setParameter("dateTo", dateTo)
				.setParameter("rightId", right.getId())
				.setParameter("countryId", country.getId())
				.setParameterList("songIds", ids)
				.list();
	}

}
