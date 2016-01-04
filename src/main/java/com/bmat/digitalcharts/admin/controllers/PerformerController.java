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
import com.bmat.digitalcharts.admin.model.DataTablesResponse;
import com.bmat.digitalcharts.admin.model.Performer;


@Controller
@RequestMapping("/admin/performer")
public class PerformerController {
	
	private static Log log = LogFactory.getLog(PerformerController.class);
	
	@Autowired
	private PerformerDao dao;
	
//	@InitBinder
//	private void dateBinder(WebDataBinder binder) {
//
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
//	    binder.registerCustomEditor(Date.class, editor);
//	}
	
	@RequestMapping("/list")
	public String list(ModelMap model) {
		return "admin/performer_list";
	}
	
	@ResponseBody
	@RequestMapping("/list_ajax")
	public DataTablesResponse list(HttpServletRequest request) {
		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = Performer.getOrderingField( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0) );
		
		
		List<Performer> performers = dao.getAllPaginatedAndFiltered(
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
				performers, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam("id") Long id, ModelMap model) {
		
		Performer performer = dao.search(id);
		return prepareForm(performer, model);
	}

	private String prepareForm(Performer performer, ModelMap model) {
		
		model.addAttribute("performer", performer);
		return "admin/performer_edit";
	}
	
	@RequestMapping(value="/accept", method={RequestMethod.POST})
	public String acceptUpdate(@Valid Performer performer, 
			BindingResult result, ModelMap model){
		
		if (!result.hasErrors()) {
			
			try {
				dao.save(performer);
				model.addAttribute("msg", "El artista se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando el artista.", e);
				model.addAttribute("msg", "Se produjo un error guardando el artista. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return list(model);
		}
		
		return prepareForm(performer, model);
	}


	
	@RequestMapping("/findPerformerByName")
	@ResponseBody
	public List<Performer> findPerformerByName(@RequestParam("performerName")String performerName) {
		
		return dao.getPerformersLikeName(performerName);
		
	}
}
