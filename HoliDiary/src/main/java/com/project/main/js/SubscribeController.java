package com.project.main.js;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SubscribeController {
	
	@Autowired
	UserDAO uDAO;
	
	@Autowired
	SubscribeDAO sDAO;
	
		// 나의구독 검색
		@RequestMapping(value = "/mysub.search", method = RequestMethod.GET)
		public @ResponseBody List<SubscribeInfo> mysubSearch(HttpServletRequest req, @RequestParam("mysubSearch") String search) {
			
			return sDAO.mysubSearch(req, search);
			
		}

		// 구독 취소
		@RequestMapping(value = "/mysub.cancel", method = RequestMethod.GET)
		public @ResponseBody int mysubCancel(HttpServletRequest req, @RequestParam("no") String subscribeNo) {
			
			return sDAO.mysubCancel(subscribeNo);
			
		}
		
		// 구독
		@RequestMapping(value = "/subscribe.do", method = RequestMethod.GET)
		public @ResponseBody int subscribeDo(HttpServletRequest req, @RequestParam("id") String DiaryID) {
			
			return sDAO.subscribe(req, DiaryID);
			
		}

}
