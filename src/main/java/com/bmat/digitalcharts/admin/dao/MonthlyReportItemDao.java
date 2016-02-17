package com.bmat.digitalcharts.admin.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bmat.digitalcharts.admin.model.MonthlyReportItem;

@Repository
public class MonthlyReportItemDao extends SummaryReportItemDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	protected MonthlyReportItemDao() {
		super(MonthlyReportItem.class);
	}

	@Override
	protected String getEntityName() {
		return MonthlyReportItem.class.getName();
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	protected String getReportName() {
		return "monthlyReport";
	}

}
