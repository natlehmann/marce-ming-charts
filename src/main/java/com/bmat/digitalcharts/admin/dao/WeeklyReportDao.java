package com.bmat.digitalcharts.admin.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}
