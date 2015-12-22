package com.bmat.digitalcharts.admin.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	public WeeklyReport getReport(Integer year, Integer week) {
		
		// se busca por menor o igual para permitir que la semana sea 52 o 53
		List<WeeklyReport> reports = sessionFactory.getCurrentSession().createQuery(
				"SELECT m FROM WeeklyReport m WHERE m.weekFrom <= :week AND m.year = :year "
				+ "AND m.enabled = true ORDER BY m.weekFrom desc, m.id desc")
				.setParameter("week", week)
				.setParameter("year", year)
				.setMaxResults(2)
				.list();
		
		if (!reports.isEmpty()) {
			return reports.get(0);
		}
		
		return null;
	}

}