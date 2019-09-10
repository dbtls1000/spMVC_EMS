package com.biz.ems.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.biz.ems.model.EmailVO;
import com.biz.ems.model.Pager;
import com.biz.ems.service.FileService;
import com.biz.ems.service.SendMailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/ems")
public class EmailController {
	
	@Autowired
	SendMailService xMailService;
	@Autowired
	FileService fService;
	
	@ModelAttribute("emailVO")
	public EmailVO newEmailVO() {
		return new EmailVO();
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(@RequestParam(defaultValue = "1") int curPage,
						@RequestParam(defaultValue = "toEmail") String search_option,
						@RequestParam(defaultValue = "") String keyword,
						Model model) {
		HashMap<String,String> test = new HashMap<String,String>();
		test.put("search_option", search_option);
		test.put("keyword", keyword);
		int count = xMailService.countArticle(test);
		Pager pager = new Pager(count,curPage);
		int start = pager.getPageBegin();
		int end = pager.getPageEnd();
		
		HashMap<String,Object> option = new HashMap<String,Object>();
		option.put("start", start);
		option.put("end", end);
		option.put("search_option", search_option);
		option.put("keyword", keyword);
		
		List<EmailVO> emsList = xMailService.selectAll(option);
		
		
		model.addAttribute("option",search_option);
		model.addAttribute("keyword",keyword);
		model.addAttribute("pager",pager);
		model.addAttribute("EMSLIST",emsList);
		model.addAttribute("BODY","LIST");
		return "home";
	}
	
	@RequestMapping(value="/view",method=RequestMethod.GET)
	public String view(@RequestParam("ems_seq") long ems_seq,Model model) {
		EmailVO emailVO = xMailService.findBySeq(ems_seq);
		
		model.addAttribute("EMSVIEW",emailVO);
		model.addAttribute("BODY","VIEW");
		return "home";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String write(@ModelAttribute("emailVO") EmailVO emailVO, Model model) {
		
		LocalDateTime ldt = LocalDateTime.now();
		String curDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
		String curTime = ldt.format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString();
		
		emailVO.setEms_send_date(curDate);
		emailVO.setEms_send_time(curTime);
		emailVO.setEms_from_email("dbsqhtjs@naver.com");
		emailVO.setEms_from_name("4Chinese can't win");
		
		model.addAttribute("emailVO",emailVO);
		model.addAttribute("BODY","WRITE");
		
		return "home";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.POST)
	public String write(@ModelAttribute("emailVO") EmailVO emailVO,
						@RequestParam("file1") MultipartFile file1,
						@RequestParam("file2") MultipartFile file2,
						BindingResult result,
						Model model) {
		
		String file_name1 = fService.fileUp(file1);
		emailVO.setEms_file1(file_name1);
		String file_name2 = fService.fileUp(file2);
		emailVO.setEms_file2(file_name2);
		
		
		
		xMailService.insert(emailVO);
		xMailService.sendMail(emailVO);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(@RequestParam("ems_seq") long ems_seq, Model model) {
		EmailVO emailVO = xMailService.findBySeq(ems_seq);
		
//		log.info("수정파일1 : "+ emailVO.getEms_file1());
//		log.info("수정파일2 : "+ emailVO.getEms_file2());
		
		model.addAttribute("emailVO",emailVO);
		model.addAttribute("BODY","WRITE");
		
		return "home";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(@ModelAttribute EmailVO emailVO,
							@RequestParam("file1") MultipartFile file1,
							@RequestParam("file2") MultipartFile file2,
							Model model) {
		LocalDateTime ldt = LocalDateTime.now();
		String curDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
		String curTime = ldt.format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString();
		
		
		String file_name1 = fService.fileUp(file1);
		emailVO.setEms_file1(file_name1);
		String file_name2 = fService.fileUp(file2);
		emailVO.setEms_file2(file_name2);
		
		
		xMailService.update(emailVO);
		xMailService.sendMail(emailVO);
		
		return "redirect:/ems/list";
	}
	
	@RequestMapping(value="/delete/{ems_seq}",method=RequestMethod.GET)
	public String delete(@PathVariable long ems_seq,Model model) {
		
		xMailService.delete(ems_seq);
		
		return "redirect:/ems/list";
	}
	
}
