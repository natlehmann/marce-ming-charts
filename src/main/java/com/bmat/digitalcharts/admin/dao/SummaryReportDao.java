package com.bmat.digitalcharts.admin.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bmat.digitalcharts.admin.model.Country;
import com.bmat.digitalcharts.admin.model.RestSource;
import com.bmat.digitalcharts.admin.model.Right;
import com.bmat.digitalcharts.admin.model.SummaryReport;

public abstract class SummaryReportDao extends AbstractEntityDao<SummaryReport> {

	private static Log log = LogFactory.getLog(SummaryReportDao.class);
	
	
	protected SummaryReportDao(@SuppressWarnings("rawtypes") Class entidad) {
		super(entidad);
	}


	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public SummaryReport getReport(Integer year, Integer weekFrom, 
			Integer month, Country country, Right right, RestSource restSource) {
		
		String queryStr = "SELECT r FROM " + getEntityName() 
				+ " r WHERE r.year = :year AND r.enabled = true AND r.country.id = :countryId AND r.right.id = :rightId ";
		
		if (month == null) {
			queryStr += "AND r.weekFrom = :weekFrom ";
			
		} else {
			queryStr += "AND r.month = :month ";
		}
		
		if (restSource == null) {
			queryStr += "AND r.filteredBySource IS NULL ";
			
		} else {
			queryStr += "AND r.filteredBySource.id = :sourceId ";
		}
		
		queryStr += "ORDER BY r.id desc";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(queryStr);
		query.setParameter("year", year);
		query.setParameter("countryId", country.getId());
		query.setParameter("rightId", right.getId());
		
		if (month == null) {
			query.setParameter("weekFrom", weekFrom);
			
		} else {
			query.setParameter("month", month);
		}
		
		if (restSource != null) {
			query.setParameter("sourceId", restSource.getId());
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
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<SummaryReport> getAllPaginatedAndFiltered(int inicio, int cantidadResultados,
			String filtro, String campoOrdenamiento, String direccionOrdenamiento) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		Query query = null;
		
		if (StringUtils.isEmpty(filtro)) {
			
			String queryStr = "from " + this.getEntityName() + " where enabled = true";
			
			if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
				queryStr += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
			}
			
			query = session.createQuery(queryStr);
			
		} else {
			
			String queryStr = getListQuery();
			
			if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
				queryStr += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
			}
			
			Integer filtroInt = null;
			try {
				filtroInt = Integer.parseInt(filtro);
				
			} catch (NumberFormatException e) {
				// ignorada
			}
			
			query = session.createQuery(queryStr)
					.setParameter("filtroInt", filtroInt)
					.setParameter("filtro", "%" + filtro + "%");
		}
		
		return query.setFirstResult(inicio)
				.setMaxResults(cantidadResultados).list();
	}

	protected abstract String getListQuery();
	
	protected abstract String getListCountQuery();


	@Transactional(value="transactionManager")
	public Long getCount(String filtro) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		Query query = null;

		if (StringUtils.isEmpty(filtro)) {			
			
			String queryStr = "select count(e) from " + this.getEntityName() + " e where e.enabled = true";
			
			query = session.createQuery(queryStr);
			
		} else {
			
			String queryStr = getListCountQuery();
			
			Integer filtroInt = null;
			try {
				filtroInt = Integer.parseInt(filtro);
				
			} catch (NumberFormatException e) {
				// ignorada
			}
			
			query = session.createQuery(queryStr)
					.setParameter("filtroInt", filtroInt)
					.setParameter("filtro", "%" + filtro + "%");
			
		}
		
		Long resultado = (Long) query.uniqueResult();
		
		return resultado != null ? resultado.longValue() : 0;
	}


	protected abstract String getEntityName();

}
