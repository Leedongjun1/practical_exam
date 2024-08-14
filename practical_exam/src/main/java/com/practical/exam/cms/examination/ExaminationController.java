package com.practical.exam.cms.examination;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.practical.exam.cms.examination.service.ExaminationService;

/**
 * 시험 화면 Controller  
 * 
 * @author adgjl
 *
 */
@Controller
@RequestMapping(value="/examination")
public class ExaminationController {
	

	@Autowired
	ExaminationService examinationService;

	/**
	 * 20문제 뽑아내기
	 * 
	 * @param reqData
	 * @return
	 */
	@RequestMapping(value = "/shamExam", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView index(@RequestParam HashMap<String,String> reqData) {
		ModelAndView mav = new ModelAndView();
		if(reqData.get("hiddenData") == null) {
			mav.setViewName("redirect:/examination");
		} else {
			mav.setViewName("/examination/shamExam");
			mav.addObject("examList",examinationService.getExamination());
		}
		return mav;
	}
	
	/**
	 * 문제 풀기 화면 노출
	 * 
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView mun() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/examination/index");
		return mav;
	}
	
	
	/**
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/marking", method = RequestMethod.POST)
	public ModelAndView marking(@RequestBody HashMap<String,Object> params) {
		ModelAndView mav = new ModelAndView();
		if(examinationService.marking(params).get("selfMark") != null) { 
			System.out.println("셀프마킹 진입");
			mav.setViewName("/examination/selfMarking");
			mav.addObject("selfMarking", examinationService.marking(params));
			return mav;
		} else {
			System.out.println("기본마킹 진입");
			mav.setViewName("/examination/marking");
			mav.addObject("marking", examinationService.marking(params));
//			model.addAttribute("marking", examinationService.marking(params));
			return mav;
		}
		
	}
	
}
