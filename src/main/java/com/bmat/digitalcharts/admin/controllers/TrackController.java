package com.bmat.digitalcharts.admin.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmat.digitalcharts.admin.controllers.Utils.Params;
import com.bmat.digitalcharts.admin.dao.LabelCompanyDao;
import com.bmat.digitalcharts.admin.dao.LicensorDao;
import com.bmat.digitalcharts.admin.dao.SongDao;
import com.bmat.digitalcharts.admin.dao.TrackDao;
import com.bmat.digitalcharts.admin.model.DataTablesResponse;
import com.bmat.digitalcharts.admin.model.LabelCompany;
import com.bmat.digitalcharts.admin.model.Licensor;
import com.bmat.digitalcharts.admin.model.Performer;
import com.bmat.digitalcharts.admin.model.Song;
import com.bmat.digitalcharts.admin.model.Track;


@Controller
@RequestMapping("/admin/track")
public class TrackController {
	
	private static Log log = LogFactory.getLog(TrackController.class);
	
	@Autowired
	private TrackDao dao;
	
	@Autowired
	private SongDao songDao;
	
	@Autowired
	private LabelCompanyDao labelCompanyDao;
	
	@Autowired
	private LicensorDao licensorDao;
	
	
	@RequestMapping("/list")
	public String list(ModelMap model) {
		return "admin/track_list";
	}
	
	@RequestMapping("/updateTracks")
	public String updateTracks(@RequestParam("id") Long id, ModelMap model) {
		
		Song song = songDao.search(id);
		model.addAttribute("song", song);
		
		return "admin/track_edition_list";
	}
	
	@ResponseBody
	@RequestMapping("/list_ajax")
	public DataTablesResponse list(HttpServletRequest request, @RequestParam("songId") Long songId) {
		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = Track.getOrderingField( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0) );
		
		
		List<Track> tracks = dao.getAllPaginatedAndFiltered(
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO),
				campoOrdenamiento,
				(String)params.get(Params.DIRECCION_ORDENAMIENTO),
				songId);
		
		long totalFiltrados = dao.getCount(
				(String)params.get(Params.FILTRO), songId);
		
		long total = totalFiltrados;
		if (!StringUtils.isEmpty((String)params.get(Params.FILTRO))) {
			total = dao.getCount(null, songId);
		}
		
		DataTablesResponse resultado = new DataTablesResponse(
				tracks, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
	@RequestMapping("/merge")
	public String merge(@RequestParam("id") Long id, ModelMap model) {
		
		Track track = dao.search(id);
		return prepareForm(track, model);
	}

	private String prepareForm(Track track, ModelMap model) {
		
		model.addAttribute("track", track);
		return "admin/track_merge";
	}
	
	@RequestMapping(value="/accept", method={RequestMethod.POST})
	@Transactional
	public String acceptMerge(@Valid Track track, 
			BindingResult result, ModelMap model){
		
		if (!result.hasErrors()) {
			
			try {
				
				List<Track> similarTracks = dao.getSimilarTracks(track.getId());
				
				Performer performer = null;
				
				if (track.getSong() != null && track.getSong().getId() != null) {
					Song song = songDao.search(track.getSong().getId());
					performer = song.getPerformer();
				}
				
				for (Track everyTrack : similarTracks) {
					
					if (performer != null) {
						everyTrack.setPerformer(performer);
					}
					
					everyTrack.setSong(track.getSong());
					dao.save(everyTrack);
				}
				
				model.addAttribute("msg", "El track se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando el track.", e);
				model.addAttribute("msg", "Se produjo un error guardando el track. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return updateTracks(track.getSong().getId(), model);
		}
		
		return prepareForm(track, model);
	}
	
	
	@RequestMapping("/update")
	public String update(@RequestParam("id") Long id, ModelMap model) {
		
		Track track = dao.search(id);
		return prepareFormForEdit(track, model);
	}
	
	private String prepareFormForEdit(Track track, ModelMap model) {
		
		model.addAttribute("track", track);
		
		if (track.getRelease().getLabelCompany().getId() != null) {
			
			LabelCompany labelCompany = labelCompanyDao.search(
					track.getRelease().getLabelCompany().getId());
			model.addAttribute("labelCompanyName", labelCompany.getName());
		}
		
		if (track.getRelease().getLicensor().getId() != null) {
			
			Licensor licensor = licensorDao.search(track.getRelease().getLicensor().getId());			
			model.addAttribute("licensorName", licensor.getName());
		}
		
		return "admin/track_edit";
	}

	
	@RequestMapping(value="/acceptEdition", method={RequestMethod.POST})
	@Transactional
	public String acceptUpdate(@Valid Track track, 
			BindingResult result, ModelMap model){
		
		if (track.getRelease().getLabelCompany().getId() == null) {
			result.rejectValue("release.labelCompany.name", "required", "Debe seleccionar un valor");
		}
		
		if (track.getRelease().getLicensor().getId() == null) {
			result.rejectValue("release.licensor.name", "required", "Debe seleccionar un valor");
		}
		
		if (!result.hasErrors()) {
			
			try {
				
				LabelCompany labelCompany = labelCompanyDao.search(
						track.getRelease().getLabelCompany().getId());

				Licensor licensor = licensorDao.search(track.getRelease().getLicensor().getId());
				
				List<Track> similarTracks = dao.getSimilarTracks(track.getId());
				
				for (Track everyTrack : similarTracks) {
					
					everyTrack.getRelease().setLabelCompany(labelCompany);
					everyTrack.getRelease().setLicensor(licensor);
					dao.save(everyTrack);
				}
				
				model.addAttribute("msg", "El track se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando el track.", e);
				model.addAttribute("msg", "Se produjo un error guardando el track. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return updateTracks(track.getSong().getId(), model);
		}
		
		return prepareFormForEdit(track, model);
	}
	
	
	@RequestMapping("/findLabelCompanyByName")
	@ResponseBody
	public List<LabelCompany> findLabelCompanyByName(
			@RequestParam("labelCompanyName")String labelCompanyName) {
		
		return labelCompanyDao.getLikeName(labelCompanyName);
		
	}
	
	
	@RequestMapping("/findLicensorByName")
	@ResponseBody
	public List<Licensor> findLicensorByName(
			@RequestParam("licensorName")String licensorName) {
		
		return licensorDao.getLikeName(licensorName);
		
	}

}
