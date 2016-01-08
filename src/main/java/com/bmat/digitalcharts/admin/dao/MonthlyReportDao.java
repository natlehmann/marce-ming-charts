package com.bmat.digitalcharts.admin.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.model.MonthlyReport;

@Repository
public class MonthlyReportDao extends SummaryReportDao {
	
	private static Log log = LogFactory.getLog(MonthlyReportDao.class);
	
	protected MonthlyReportDao() {
		super(MonthlyReport.class);
	}

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	protected String getEntityName() {
		return "MonthlyReport";
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Returns a report without any filters by source
	 * @param year
	 * @param month
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public MonthlyReport getBaseReport(Integer year, Integer month) {
		
		List<MonthlyReport> reports = sessionFactory.getCurrentSession().createQuery(
				"SELECT m FROM MonthlyReport m WHERE m.month = :month AND m.year = :year "
				+ "AND m.filteredBySource = null AND m.enabled = true ORDER BY m.id desc")
				.setParameter("month", month)
				.setParameter("year", year)
				.list();
		
		if (reports.size() > 1) {
			log.error("Se encontro mas de un reporte mensual habilitado para el mes " + month + " y anio " + year);
		}
		
		if (reports.size() == 1) {
			return reports.get(0);
		}
		
		return null;
		
	}

	@Override
	protected String getListQuery() {
		return "SELECT e from MonthlyReport e " + getCondition();
	}

	@Override
	protected String getListCountQuery() {
		return "select count(e) from MonthlyReport e " + getCondition();
	}
	
	private String getCondition() {
		return "where e.enabled = true AND (e.year = :filtroInt OR e.month = :filtroInt "
				+ "OR e.country.name like :filtro OR e.right.name like :filtro) ";
	}

}
