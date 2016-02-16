package com.bmat.digitalcharts.admin.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
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
		
		Session session = sessionFactory.getCurrentSession();
		
		String queryStr = "SELECT t FROM Track t ";
		
		if (!StringUtils.isEmpty(filtro)) {
			queryStr += "where t.name like :filtro OR t.isrc like :filtro "
					+ "OR t.song.name like :filtro OR t.performer.name like :filtro "
					+ "OR t.release.licensor.name like :filtro "
					+ "OR t.release.labelCompany.name like :filtro ";
		}
		
		queryStr += "GROUP BY t.isrc, t.release.id, t.performer.id, t.song.id ";
		
		if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
			queryStr += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
		}
		
		
		Query query = session.createQuery(queryStr);
		
		if (!StringUtils.isEmpty(filtro)) {
			query.setParameter("filtro", "%" + filtro + "%");
		}
			
		return query.setFirstResult(inicio)
					.setMaxResults(cantidadResultados).list();
			
	}

	@Transactional(value="transactionManager")
	public Long getCount(String filtro) {
		
		Session session = sessionFactory.getCurrentSession();


		String queryStr = "select count(1) FROM ("
				+ "select 1 from Track t, Song s, Performer p, BMATDigitalChartsDB.Release r, "
				+ "LabelCompany l, Licensor ls "
				+ "WHERE t.songId = s.id AND t.performerId = p.id "
				+ "AND t.releaseId = r.id AND r.labelCompanyId = l.id "
				+ "AND r.licensorId = ls.id ";
		
		if (!StringUtils.isEmpty(filtro)) {
			queryStr += "AND (t.name like :filtro OR t.isrc like :filtro "
					+ "OR s.name like :filtro OR p.name like :filtro "
					+ "OR l.name like :filtro OR ls.name like :filtro) ";
		}
		
		queryStr += "group by t.isrc, t.releaseId, t.performerId, t.songId) as tmp";
		
		Query query = session.createSQLQuery(queryStr);
		
		if (!StringUtils.isEmpty(filtro)) {
			query.setParameter("filtro", "%" + filtro + "%");
		}
			
		BigInteger resultado = (BigInteger) query.uniqueResult();
			
		return resultado != null ? resultado.longValue() : 0;
	}

}
