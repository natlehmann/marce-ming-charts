package com.bmat.digitalcharts.admin.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.type.BigDecimalType;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.model.Country;
import com.bmat.digitalcharts.admin.model.RestSource;
import com.bmat.digitalcharts.admin.model.Right;
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
				.append("sum(u.units) as currentAmount, u.track.release.licensor.id, ")
				.append("u.track.release.licensor.name) ")
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
	
	
	@Transactional
	public BigDecimal getAggregateUnits(Country country, Right right,
			Date dateFrom, RestSource filteredBySource, Long songId, Long performerId) {
		
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("SELECT SUM(i.currentAmount) as result FROM ")
				.append(getEntityName().substring(getEntityName().lastIndexOf(".") + 1)).append(" i, ")
				.append(getReportName().toUpperCase().charAt(0)).append(getReportName().substring(1)).append(" r ")
				.append("WHERE i.").append(getReportName()).append("_id = r.id ")
				.append("AND r.country_id = :countryId ")
				.append("AND r.right_id = :rightId ")
				.append("AND r.dateFrom < :dateFrom ")
				.append("AND i.songId = :songId ")
				.append("AND i.performerId = :performerId ");
		
		if (filteredBySource != null) {
			queryStr.append("AND r.filteredBySource_id = :sourceId ");
		}
		
		SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery(queryStr.toString());
		
		query.setParameter("countryId", country.getId())
			.setParameter("rightId", right.getId())
			.setParameter("dateFrom", dateFrom)
			.setParameter("songId", songId)
			.setParameter("performerId", performerId);
		
		if (filteredBySource != null) {
			query.setParameter("sourceId", filteredBySource.getId());
		}
		
		query.addScalar("result", BigDecimalType.INSTANCE);
		
		return (BigDecimal) query.uniqueResult();
	}


	protected abstract String getReportName();

	protected abstract String getEntityName();

}
