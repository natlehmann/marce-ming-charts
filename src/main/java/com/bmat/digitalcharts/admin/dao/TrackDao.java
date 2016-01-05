package com.bmat.digitalcharts.admin.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bmat.digitalcharts.admin.model.Track;


@Repository
public class TrackDao extends AbstractEntityDao<Track> {

	@Autowired
	private SessionFactory sessionFactory;
	
	public TrackDao() {
		super(Track.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<Track> getAllPaginatedAndFiltered(int inicio, int cantidadResultados,
			String filtro, String campoOrdenamiento, String direccionOrdenamiento) {
		
		if (StringUtils.isEmpty(filtro)) {
			return super.getAllPaginated(
					inicio, cantidadResultados, campoOrdenamiento, direccionOrdenamiento);
			
		} else {
			
			Session session = sessionFactory.getCurrentSession();
			
			String query = "from Track where name like :filtro OR isrc like :filtro "
					+ "OR song.name like :filtro OR performer.name like :filtro";
			
			if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
				query += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
			}
			
			return session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%")
					.setFirstResult(inicio)
					.setMaxResults(cantidadResultados).list();
			
		}
	}

	@Transactional(value="transactionManager")
	public Long getCount(String filtro) {
		
		if (StringUtils.isEmpty(filtro)) {
			return super.getCount();
			
		} else {
			
			Session session = sessionFactory.getCurrentSession();
			
			String query = "select count(e) from Track e where e.name like :filtro OR e.isrc like :filtro "
					+ "OR e.song.name like :filtro OR performer.name like :filtro";
			
			Long resultado = (Long) session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%").uniqueResult();
			
			return resultado != null ? resultado.longValue() : 0;
		}
	}

}
