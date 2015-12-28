package com.bmat.digitalcharts.admin.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.model.RestSource;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;

public abstract class SummaryReportItemDao extends AbstractEntityDao<SummaryReportItem> {
	
	@SuppressWarnings("rawtypes")
	protected SummaryReportItemDao(Class entidad) {
		super(entidad);
	}
	
	
	
	@Transactional(value="transactionManager")
	public List<SummaryReportItem> getItems(Long countryId, Long rightId, Date dateFrom, Date dateTo) {
		
		return getItems(countryId, rightId, dateFrom, dateTo, null, null);
	}
	
	
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public List<SummaryReportItem> getItems(Long countryId, Long rightId, Date dateFrom, 
			Date dateTo, RestSource filteredBySource, List<Long> songsIds) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT new ").append(getEntityName() )
				.append("(u.track.song.id, u.track.song.name, u.track.performer.id, u.track.performer.name, ")
				.append("sum(u.units) as currentAmount, u.track.release.labelCompany.id, ")
				.append("u.track.release.labelCompany.name) ")
				.append("FROM Usage u " )
				.append("WHERE u.chartDate between :dateFrom and :dateTo ")
				.append("and u.right.id = :rightId ")
				.append("and u.country.id = :countryId ");
		
		if (filteredBySource != null) {
			buffer.append("AND u.restSource.id = :sourceId ");
		}
		
		if (songsIds != null && !songsIds.isEmpty()) {
			buffer.append("AND u.track.song.id IN (:songsIds) ");
		}
		
		buffer.append("group by u.track.song.id ");
		
		if (songsIds != null && !songsIds.isEmpty()) {
			buffer.append("ORDER BY u.track.song.id ");
			
		} else {
			buffer.append("order by currentAmount desc");
		}
		
		Query query = getSessionFactory().getCurrentSession().createQuery(buffer.toString());
		
		query.setParameter("dateFrom", dateFrom)
				.setParameter("dateTo", dateTo)
				.setParameter("rightId", rightId)
				.setParameter("countryId", countryId);
		
		if (filteredBySource != null) {
			query.setParameter("sourceId", filteredBySource.getId());
		}
		
		if (songsIds != null && !songsIds.isEmpty()) {
			query.setParameterList("songsIds", songsIds);
			
		} else {
			query.setMaxResults(200);
		}
		
		return query.list();
	}


	protected abstract String getEntityName();

}
