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
			String filtro, String campoOrdenamiento, String direccionOrdenamiento, Long songId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		String queryStr = "SELECT t FROM Track t where t.song.id = :songId ";
		
		if (!StringUtils.isEmpty(filtro)) {
			queryStr += "AND (t.name like :filtro OR t.isrc like :filtro "
					+ "OR t.song.name like :filtro OR t.performer.name like :filtro "
					+ "OR t.release.licensor.name like :filtro "
					+ "OR t.release.labelCompany.name like :filtro) ";
		}
		
		queryStr += "GROUP BY t.isrc, t.release.id, t.performer.id, t.song.id ";
		
		if ( !StringUtils.isEmpty(campoOrdenamiento) ) {
			queryStr += " order by " + campoOrdenamiento + " " + direccionOrdenamiento;
		}
		
		
		Query query = session.createQuery(queryStr);
		
		query.setParameter("songId", songId);
		
		if (!StringUtils.isEmpty(filtro)) {
			query.setParameter("filtro", "%" + filtro + "%");
		}
			
		return query.setFirstResult(inicio)
					.setMaxResults(cantidadResultados).list();
			
	}

	@Transactional(value="transactionManager")
	public Long getCount(String filtro, Long songId) {
		
		Session session = sessionFactory.getCurrentSession();


		String queryStr = "select count(1) FROM ("
				+ "select 1 from Track t, Song s, Performer p, BMATDigitalChartsDB.Release r, "
				+ "LabelCompany l, Licensor ls "
				+ "WHERE t.songId = :songId "
				+ "AND t.songId = s.id AND t.performerId = p.id "
				+ "AND t.releaseId = r.id AND r.labelCompanyId = l.id "
				+ "AND r.licensorId = ls.id ";
		
		if (!StringUtils.isEmpty(filtro)) {
			queryStr += "AND (t.name like :filtro OR t.isrc like :filtro "
					+ "OR s.name like :filtro OR p.name like :filtro "
					+ "OR l.name like :filtro OR ls.name like :filtro) ";
		}
		
		queryStr += "group by t.isrc, t.releaseId, t.performerId, t.songId) as tmp";
		
		Query query = session.createSQLQuery(queryStr);
		
		query.setParameter("songId", songId);
		
		if (!StringUtils.isEmpty(filtro)) {
			query.setParameter("filtro", "%" + filtro + "%");
		}
			
		BigInteger resultado = (BigInteger) query.uniqueResult();
			
		return resultado != null ? resultado.longValue() : 0;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<Track> getSimilarTracks(Long id) {
		
		Track track = search(id);
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT t FROM Track t WHERE ");
		
		if (track.getIsrc() != null) {
			buffer.append("t.isrc = :isrc ");
			
		} else {
			buffer.append("t.isrc IS NULL ");
		}
		
		buffer.append("AND t.release.id = :releaseId AND t.song.id = :songId ")
			.append("AND t.performer.id = :performerId");
		
		Query query = session.createQuery(buffer.toString());
		
		query.setParameter("releaseId", track.getRelease().getId());
		query.setParameter("songId", track.getSong().getId());
		query.setParameter("performerId", track.getPerformer().getId());
		
		if (track.getIsrc() != null) {
			query.setParameter("isrc", track.getIsrc());
		}
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
	public List<Track> getOtherTracksWithSameRelease(Track track) {
		
		return sessionFactory.getCurrentSession().createQuery(
				"SELECT t FROM Track t WHERE t.release.id = :releaseId "
				+ "AND (t.song.id != :songId OR t.performer.id != :performerId)")
				
				.setParameter("releaseId", track.getRelease().getId())
				.setParameter("songId", track.getSong().getId())
				.setParameter("performerId", track.getPerformer().getId())
				.setMaxResults(10)
				.list();
	}

}
