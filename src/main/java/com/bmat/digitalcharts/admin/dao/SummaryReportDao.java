package com.bmat.digitalcharts.admin.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.model.SummaryReport;

public abstract class SummaryReportDao extends AbstractEntityDao<SummaryReport> {

	private static Log log = LogFactory.getLog(SummaryReportDao.class);
	
	
	protected SummaryReportDao(@SuppressWarnings("rawtypes") Class entidad) {
		super(entidad);
	}


	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public SummaryReport getReport(Integer year, Integer weekFrom, Integer month) {
		
		String queryStr = "SELECT r FROM " + getEntityName() + " r WHERE r.year = :year AND r.enabled = true";
		
		if (month == null) {
			queryStr += " AND r.weekFrom = :weekFrom";
			
		} else {
			queryStr += " AND r.month = :month";
		}
		
		queryStr += " ORDER BY r.id desc";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(queryStr);
		query.setParameter("year", year);
		
		if (month == null) {
			query.setParameter("weekFrom", weekFrom);
			
		} else {
			query.setParameter("month", month);
		}
		
		
		List<SummaryReport> report = query.list();
		
		if (report.size() > 1) {
			log.error("Se encontro mas de un reporte habilitado.");
			return report.get(0);
		}
		
		if (report.size() == 1) {
			return report.get(0);
		}
		
		return null;
	}


	protected abstract String getEntityName();

}
