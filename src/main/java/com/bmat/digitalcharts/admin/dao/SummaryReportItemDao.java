package com.bmat.digitalcharts.admin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.model.SummaryReportItem;

public abstract class SummaryReportItemDao extends AbstractEntityDao<SummaryReportItem> {
	
	@SuppressWarnings("rawtypes")
	protected SummaryReportItemDao(Class entidad) {
		super(entidad);
	}
	
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public List<SummaryReportItem> getItems(Long countryId, Long rightId, Date dateFrom, Date dateTo) {
		
		return getSessionFactory().getCurrentSession().createQuery(
				"SELECT new " + getEntityName() 
				+ "(u.track.song.id, u.track.song.name, u.track.performer.id, u.track.performer.name, "
				+ "sum(u.units) as currentAmount, u.track.release.labelCompany.id, "
				+ "u.track.release.labelCompany.name) "
				+ "FROM Usage u " 
				+ "WHERE u.chartDate between :dateFrom and :dateTo "
				+ "and u.right.id = :rightId "
				+ "and u.country.id = :countryId "
				+ "group by u.track.song.id "
				+ "order by currentAmount desc"
				)
				.setParameter("dateFrom", dateFrom)
				.setParameter("dateTo", dateTo)
				.setParameter("rightId", rightId)
				.setParameter("countryId", countryId)
				
				.setMaxResults(200)
				.list();
	}


	protected abstract String getEntityName();

}
