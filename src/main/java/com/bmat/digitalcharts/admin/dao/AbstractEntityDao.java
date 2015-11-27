package com.bmat.digitalcharts.admin.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public abstract class AbstractEntityDao<T> {
	
	@SuppressWarnings("rawtypes")
	private Class claseEntidad;
	
	private String nombreEntidad;
	
	private String criterioOrdenamiento;
	
	@SuppressWarnings("rawtypes")
	protected AbstractEntityDao(Class entidad) {
		this.claseEntidad = entidad;
		this.nombreEntidad = this.claseEntidad.getName();
	}
	
	@SuppressWarnings("rawtypes")
	protected AbstractEntityDao(Class entidad, String criterioOrdenamiento) {
		this(entidad);
		this.criterioOrdenamiento = criterioOrdenamiento;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(value="transactionManager")
	public List<T> getAll() {
		
		Session session = getSessionFactory().getCurrentSession();
		
		String query = "from " + this.nombreEntidad;
		if (this.criterioOrdenamiento != null) {
			query += " order by " + this.criterioOrdenamiento;
		}
		
		List resultado = session.createQuery(query).list();
		return resultado;
	}
	
	@Transactional(value="transactionManager")
	public T save(T entidad) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entidad);
		
		return entidad;
	}
	
	@Transactional(value="transactionManager")
	public void deleteAll() {
		
		Session session = getSessionFactory().getCurrentSession();		
		session.createSQLQuery("delete from " + this.nombreEntidad).executeUpdate();		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public T search(Long id) {
		Session session = getSessionFactory().getCurrentSession();
		return (T) session.get(this.claseEntidad, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<T> getAllPaginated(int inicio,
			int cantidadResultados, String campoOrdenamiento,
			String direccionOrdenamiento) {
		
		Session session = getSessionFactory().getCurrentSession();
		
		String query = "from " + this.nombreEntidad;
		
		if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
			query += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
		}
		
		return session.createQuery(query)
				.setFirstResult(inicio).setMaxResults(cantidadResultados).list();
	}

	@Transactional(value="transactionManager")
	public long getCount() {
		
		Session session = getSessionFactory().getCurrentSession();
		
		String query = "select count(e) from " + this.nombreEntidad + " e";
		
		Long resultado = (Long) session.createQuery(query).uniqueResult();
		
		return resultado != null ? resultado.longValue() : 0;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public void delete(Long id) {
		
		Session session = getSessionFactory().getCurrentSession();
		T entidad = (T) session.get(claseEntidad, id);
		session.delete(entidad);		
	}
	
	protected abstract SessionFactory getSessionFactory();
}
