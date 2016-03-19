package com.bmat.digitalcharts.admin.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmat.digitalcharts.admin.controllers.Utils.Params;
import com.bmat.digitalcharts.admin.dao.PerformerDao;
import com.bmat.digitalcharts.admin.dao.SongDao;
import com.bmat.digitalcharts.admin.model.DataTablesResponse;
import com.bmat.digitalcharts.admin.model.FromPageDataStrategy;
import com.bmat.digitalcharts.admin.model.Performer;
import com.bmat.digitalcharts.admin.model.Song;


@Controller
@RequestMapping("/admin/song")
public class SongController {
	
	private static Log log = LogFactory.getLog(SongController.class);
	
	@Autowired
	private SongDao dao;
	
	@Autowired
	private PerformerDao performerDao;
	
	@RequestMapping("/list")
	public String list(ModelMap model) {
		return "admin/song_list";
	}
	
	@ResponseBody
	@RequestMapping("/list_ajax")
	public DataTablesResponse list(HttpServletRequest request, @RequestParam("from")String from) {
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = Song.getOrderingField( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0), from);
		
		
		List<Song> songs = dao.getAllPaginatedAndFiltered(
				(int)params.get(Params.INICIO),
				(int)params.get(Params.CANTIDAD_RESULTADOS),
				(String)params.get(Params.FILTRO),
				campoOrdenamiento,
				(String)params.get(Params.DIRECCION_ORDENAMIENTO));
		
		long totalFiltrados = dao.getCount(
				(String)params.get(Params.FILTRO));
		
		long total = totalFiltrados;
		if (!StringUtils.isEmpty((String)params.get(Params.FILTRO))) {
			total = dao.getCount(null);
		}
		
		DataTablesResponse resultado = new DataTablesResponse(
				songs, request.getParameter("sEcho"), total, totalFiltrados, 
				new FromPageDataStrategy(from, Utils.SONG_LIST));
		
		return resultado;
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam("id") Long id, ModelMap model) {
		
		Song song = dao.search(id);
		return prepareForm(song, model);
	}

	private String prepareForm(Song song, ModelMap model) {
		
		model.addAttribute("song", song);
		
		if (song.getPerformer() != null && song.getPerformer().getId() != null) {
			Performer performer = performerDao.search(song.getPerformer().getId());
			model.addAttribute("performerName", performer.getName());
		}
		
		return "admin/song_edit";
	}
	
	@RequestMapping(value="/accept", method={RequestMethod.POST})
	public String acceptUpdate(@Valid Song song, 
			BindingResult result, ModelMap model){
		
		if (song.getPerformer() == null || song.getPerformer().getId() == null) {
			result.rejectValue("performer.id", "performer.id.not.null", "Debe seleccionar un artista existente.");
		}
		
		if (!result.hasErrors()) {
			
			try {
				dao.save(song);
				model.addAttribute("msg", "La canción se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando la canción.", e);
				model.addAttribute("msg", "Se produjo un error guardando la canción. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return list(model);
		}
		
		return prepareForm(song, model);
	}

	
	@RequestMapping("/merge")
	public String merge(@RequestParam("id") Long id, ModelMap model) {
		
		Song song = dao.search(id);
		model.addAttribute("song", song);
		
		return "admin/song_merge";
	}
	
	
	@RequestMapping(value="/accept_merge", method={RequestMethod.POST})
	public String acceptMerge(@RequestParam("currentId") Long currentId, 
			@RequestParam("song.id") Long newId, ModelMap model) {
		
		try {
			dao.merge(currentId, newId);
			model.addAttribute("msg", "La canción de ID " + currentId 
					+ " se ha fusionado con la de ID " + newId);
			
		} catch (Exception e) {
			log.error("Se produjo un error realizando la fusión de las canciones.", e);
			model.addAttribute("msg", "Se produjo un error realizando la fusión de las canciones. "
					+ "Si el problema persiste consulte al administrador del sistema.");
		}
		
		return list(model);
		
	}

}
