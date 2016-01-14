package com.bmat.digitalcharts.admin.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bmat.digitalcharts.admin.model.Song;


@Repository
public class SongDao extends AbstractEntityDao<Song> {

	@Autowired
	private SessionFactory sessionFactory;
	
	public SongDao() {
		super(Song.class);
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<Song> getAllPaginatedAndFiltered(int inicio, int cantidadResultados,
			String filtro, String campoOrdenamiento, String direccionOrdenamiento) {
		
		if (StringUtils.isEmpty(filtro)) {
			return super.getAllPaginated(
					inicio, cantidadResultados, campoOrdenamiento, direccionOrdenamiento);
			
		} else {
			
			Session session = sessionFactory.getCurrentSession();
			
			String query = "from Song where name like :filtro OR composers like :filtro OR performer.name like :filtro";
			
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
			
			String query = "select count(e) from Song e where e.name like :filtro OR e.composers like :filtro OR e.performer.name like :filtro";
			
			Long resultado = (Long) session.createQuery(query)
					.setParameter("filtro", "%" + filtro + "%").uniqueResult();
			
			return resultado != null ? resultado.longValue() : 0;
		}
	}
	
	
	@Transactional(value="transactionManager")
	public void merge(Long currentId, Long newId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.getNamedQuery("Merge_Song_ChartDetail")
			.setParameter("newId", newId)
			.setParameter("oldId", currentId).executeUpdate();
		
		session.getNamedQuery("Merge_Song_TopChartDetail")
			.setParameter("newId", newId)
			.setParameter("oldId", currentId).executeUpdate();
		
		session.getNamedQuery("Merge_Song_SongAKA")
			.setParameter("newId", newId)
			.setParameter("oldId", currentId).executeUpdate();
		
		session.getNamedQuery("Merge_Song_Track")
			.setParameter("newId", newId)
			.setParameter("oldId", currentId).executeUpdate();
		
		super.delete(currentId);
		
	}

}
