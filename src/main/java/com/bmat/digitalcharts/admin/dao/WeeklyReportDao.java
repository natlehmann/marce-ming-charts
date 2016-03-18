package com.bmat.digitalcharts.admin.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;
import com.bmat.digitalcharts.admin.model.WeeklyReport;

@Repository
public class WeeklyReportDao extends SummaryReportDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public WeeklyReportDao() {
		super(WeeklyReport.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	protected String getEntityName() {
		return "WeeklyReport";
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public WeeklyReport getBaseReport(Integer year, Integer week) {
		
		// se busca por menor o igual para permitir que la semana sea 52 o 53
		List<WeeklyReport> reports = sessionFactory.getCurrentSession().createQuery(
				"SELECT m FROM WeeklyReport m WHERE m.weekFrom <= :week AND m.year = :year "
				+ "AND m.filteredBySource = null AND m.enabled = true ORDER BY m.weekFrom desc, m.id desc")
				.setParameter("week", week)
				.setParameter("year", year)
				.setMaxResults(2)
				.list();
		
		if (!reports.isEmpty()) {
			return reports.get(0);
		}
		
		return null;
	}

	@Override
	protected String getListQuery() {
		return "SELECT e from WeeklyReport e " + getCondition();
	}

	private String getCondition() {
		return "where e.enabled = true AND (e.year = :filtroInt OR e.weekFrom = :filtroInt "
				+ "OR e.country.name like :filtro OR e.right.name like :filtro) ";
	}

	@Override
	protected String getListCountQuery() {
		return "select count(e) from WeeklyReport e " + getCondition();
	}

	
	@Transactional
	public SummaryReport getWithItems(Long id) {
		 
		SummaryReport report = super.search(id);
		if (report != null) {
			report.getItems().size();
		}
		
		return report;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getBmatSourceUri(SummaryReport report,
			SummaryReportItem item, Long sourceId) {
		
		Session session = getSessionFactory().getCurrentSession();
		SQLQuery query = (SQLQuery) session.getNamedQuery("Get_SourceUris");
		
		query.setParameter("dateFrom", report.getDateFrom());
		query.setParameter("dateTo", report.getDateTo());
		query.setParameter("rightId", report.getRight().getId());
		query.setParameter("countryId", report.getCountry().getId());
		query.setParameter("songId", item.getSongId());
		query.setParameter("performerId", item.getPerformerId());
		query.setParameter("sourceId", sourceId);
		query.addScalar("sourceuri", StringType.INSTANCE);
		
		return query.list();
		
	}
}
