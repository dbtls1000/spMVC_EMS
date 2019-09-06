package com.biz.ems.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.ems.model.EmailVO;
import com.biz.ems.model.Pager;
import com.biz.ems.service.SendMailService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	SendMailService xMailService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@RequestParam(defaultValue = "1") int curPage, Model model) {
		HashMap<String,Object> option = new HashMap<String,Object >();
		
		int count = xMailService.countArticle();
		Pager pager = new Pager(count,curPage);
		int start = pager.getPageBegin();
		int end = pager.getPageEnd();
		
		List<EmailVO> emsList = xMailService.selectAll(option);
		option.put("start", start);
		option.put("end", end);
		
		model.addAttribute("pager",pager);
		model.addAttribute("EMSLIST",emsList);
		
		return "home";
	}
	
}
