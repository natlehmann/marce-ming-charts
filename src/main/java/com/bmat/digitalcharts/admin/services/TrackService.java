package com.bmat.digitalcharts.admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmat.digitalcharts.admin.dao.LabelCompanyDao;
import com.bmat.digitalcharts.admin.dao.LicensorDao;
import com.bmat.digitalcharts.admin.dao.ReleaseDao;
import com.bmat.digitalcharts.admin.dao.TrackDao;
import com.bmat.digitalcharts.admin.model.LabelCompany;
import com.bmat.digitalcharts.admin.model.Licensor;
import com.bmat.digitalcharts.admin.model.Release;
import com.bmat.digitalcharts.admin.model.Track;

@Service
public class TrackService {
	
	@Autowired
	private TrackDao trackDao;
	
	@Autowired
	private ReleaseDao releaseDao;
	
	@Autowired
	private LabelCompanyDao labelCompanyDao;
	
	@Autowired
	private LicensorDao licensorDao;
	

	public void updateLicensorAndCompany(Track track) {
		
		LabelCompany labelCompany = labelCompanyDao.search(
				track.getRelease().getLabelCompany().getId());

		Licensor licensor = licensorDao.search(track.getRelease().getLicensor().getId());
		
		Release newRelease = track.getRelease();
		
		List<Track> otherTracksWithSameRelease = trackDao.getOtherTracksWithSameRelease(track);
		if ( !otherTracksWithSameRelease.isEmpty() ) {
			newRelease = new Release();
		}
		
		newRelease.setLabelCompany(labelCompany);
		newRelease.setLicensor(licensor);
		
		if (newRelease.getId() == null) {
			newRelease = releaseDao.save(newRelease);
		}
		
		List<Track> similarTracks = trackDao.getSimilarTracks(track.getId());
		
		for (Track everyTrack : similarTracks) {
			
			everyTrack.setRelease(newRelease);
			trackDao.save(everyTrack);
		}
		
	}

}
