package com.bmat.digitalcharts.admin.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.model.SummaryReportItem;

@Repository
public class SummaryReportItemDao extends AbstractEntityDao<SummaryReportItem> {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SummaryReportItemDao() {
		super(SummaryReportItem.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public List<SummaryReportItem> getItems(Long countryId, Long rightId, Date dateFrom, Date dateTo) {
		
		return sessionFactory.getCurrentSession().createQuery(
				"SELECT new " + SummaryReportItem.class.getName() 
				+ "(u.track.song.id, u.track.song.name, u.track.performer.id, u.track.performer.name, sum(u.units) as currentAmount, u.track.release.labelCompany.id, u.track.release.labelCompany.name) "
				+ "FROM Usage u " 
				+ "WHERE u.chartDate between :dateFrom and :dateTo "
				+ "and u.right.id = :rightId "
				+ "and u.country.id = :countryId "
				+ "group by u.track.id "
				+ "order by currentAmount desc"
				)
				.setParameter("dateFrom", dateFrom)
				.setParameter("dateTo", dateTo)
				.setParameter("rightId", rightId)
				.setParameter("countryId", countryId)
				
				.setMaxResults(200)
				.list();
	}

}
