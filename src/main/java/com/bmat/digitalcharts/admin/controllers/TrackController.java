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
import com.bmat.digitalcharts.admin.dao.TrackDao;
import com.bmat.digitalcharts.admin.model.DataTablesResponse;
import com.bmat.digitalcharts.admin.model.Track;


@Controller
@RequestMapping("/admin/track")
public class TrackController {
	
	private static Log log = LogFactory.getLog(TrackController.class);
	
	@Autowired
	private TrackDao dao;
	
	
	@RequestMapping("/list")
	public String list(ModelMap model) {
		return "admin/track_list";
	}
	
	@ResponseBody
	@RequestMapping("/list_ajax")
	public DataTablesResponse list(HttpServletRequest request) {
		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = Track.getOrderingField( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0) );
		
		
		List<Track> tracks = dao.getAllPaginatedAndFiltered(
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
				tracks, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam("id") Long id, ModelMap model) {
		
		Track track = dao.search(id);
		return prepareForm(track, model);
	}

	private String prepareForm(Track track, ModelMap model) {
		
		model.addAttribute("track", track);
		return "admin/track_edit";
	}
	
	@RequestMapping(value="/accept", method={RequestMethod.POST})
	public String acceptUpdate(@Valid Track track, 
			BindingResult result, ModelMap model){
		
		if (!result.hasErrors()) {
			
			try {
				dao.save(track);
				model.addAttribute("msg", "El track se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando el track.", e);
				model.addAttribute("msg", "Se produjo un error guardando el track. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return list(model);
		}
		
		return prepareForm(track, model);
	}


}