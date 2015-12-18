package com.bmat.digitalcharts.admin.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.model.SummaryReport;

@Repository
public class SummaryReportDao extends AbstractEntityDao<SummaryReport> {
	
	private static Log log = LogFactory.getLog(SummaryReportDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	protected SummaryReportDao() {
		super(SummaryReport.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public SummaryReport getReport(Integer year, Integer weekFrom, Integer month) {
		
		String queryStr = "SELECT r FROM SummaryReport r WHERE r.year = :year AND r.enabled = true";
		
		if (month == null) {
			queryStr += " AND r.weekFrom = :weekFrom";
			
		} else {
			queryStr += " AND r.month = :month";
		}
		
		queryStr += " ORDER BY r.id desc";
		
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
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

}
