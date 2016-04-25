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
import com.bmat.digitalcharts.admin.dao.CountryDao;
import com.bmat.digitalcharts.admin.dao.EmailAddressDao;
import com.bmat.digitalcharts.admin.dao.RestSourceDao;
import com.bmat.digitalcharts.admin.model.DataTablesResponse;
import com.bmat.digitalcharts.admin.model.EmailAddress;


@Controller
@RequestMapping("/admin/email")
public class EmailAddressController {
	
	private static Log log = LogFactory.getLog(EmailAddressController.class);
	
	@Autowired
	private EmailAddressDao dao;
	
	@Autowired
	private RestSourceDao restSourceDao;
	
	@Autowired
	private CountryDao countryDao;
	
	
	@RequestMapping("/list")
	public String list(ModelMap model) {
		return "admin/email_list";
	}
	
	@ResponseBody
	@RequestMapping("/list_ajax")
	public DataTablesResponse list(HttpServletRequest request) {
		
		
		Map<Params, Object> params = Utils.getParametrosDatatables(request);
		
		String campoOrdenamiento = EmailAddress.getOrderingField( 
				Utils.getInt(request.getParameter("iSortCol_0"), 0) );
		
		
		List<EmailAddress> emailAddresses = dao.getAllPaginatedAndFiltered(
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
				emailAddresses, request.getParameter("sEcho"), total, totalFiltrados);
		
		return resultado;
	}
	
	@RequestMapping("/create")
	public String crear(ModelMap model) {
		
		EmailAddress email = new EmailAddress();
		return prepareForm(email, model);
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam("id") Long id, ModelMap model) {
		
		EmailAddress emailAddress = dao.search(id);
		return prepareForm(emailAddress, model);
	}

	private String prepareForm(EmailAddress emailAddress, ModelMap model) {
		
		model.addAttribute("sources", restSourceDao.getAll());
		model.addAttribute("selectedSource", emailAddress != null && emailAddress.getRestSource() != null ?
				emailAddress.getRestSource().getId() : null);
		
		model.addAttribute("countries", countryDao.getAll());
		model.addAttribute("selectedCountry", emailAddress != null && emailAddress.getCountry() != null ?
				emailAddress.getCountry().getId() : null);
		
		model.addAttribute("emailAddress", emailAddress);
		return "admin/email_edit";
	}
	
	@RequestMapping(value="/accept", method={RequestMethod.POST})
	public String acceptUpdate(@Valid EmailAddress emailAddress, 
			BindingResult result, ModelMap model){
		
		if (!result.hasErrors()) {
			
			try {
				dao.save(emailAddress);
				model.addAttribute("msg", "El email se ha guardado con éxito.");
				
			} catch (Exception e) {
				log.error("Se produjo un error guardando el email.", e);
				model.addAttribute("msg", "Se produjo un error guardando el email. "
						+ "Si el problema persiste consulte al administrador del sistema.");
			}
			
			return list(model);
		}
		
		return prepareForm(emailAddress, model);
	}
	
	
	@RequestMapping(value="/delete", method={RequestMethod.POST})
	public String delete(@RequestParam("id") Long id, ModelMap model) {
		
		try {
			dao.delete(id);
			model.addAttribute("msg", "El email se ha eliminado con éxito.");
			
		} catch (Exception e) {
			log.error("Error al eliminar la dirección de email.", e);
			model.addAttribute("msg", "No se ha podido eliminar la dirección de email. " 
					+ "Si el problema persiste consulte al administrador del sistema.");
		}
		return list(model);
	}

	
	@RequestMapping("/getAll")
	@ResponseBody
	public List<EmailAddress> getAll(@RequestParam("countryId" )Long countryId) {
		return dao.getByCountryId(countryId);
	}
}
