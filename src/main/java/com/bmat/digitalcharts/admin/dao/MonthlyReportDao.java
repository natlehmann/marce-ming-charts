package com.bmat.digitalcharts.admin.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bmat.digitalcharts.admin.model.MonthlyReport;

@Repository
public class MonthlyReportDao extends SummaryReportDao {
	
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

}
