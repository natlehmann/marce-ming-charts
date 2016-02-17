package com.bmat.digitalcharts.admin.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bmat.digitalcharts.admin.model.WeeklyReportItem;

@Repository
public class WeeklyReportItemDao extends SummaryReportItemDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	protected WeeklyReportItemDao() {
		super(WeeklyReportItem.class);
	}

	@Override
	protected String getEntityName() {
		return WeeklyReportItem.class.getName();
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	protected String getReportName() {
		return "weeklyReport";
	}

}
