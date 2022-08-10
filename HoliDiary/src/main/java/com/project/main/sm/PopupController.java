package com.project.main.sm;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.main.js.UserDAO;

@Controller
public class PopupController {

	@Autowired
	private UserDAO uDAO;
	
	@Autowired
	private DiaryDAO dDAO;

	// 팝업창 - 오픈
	@RequestMapping(value = "/popup.open", method = RequestMethod.GET)
	public String popupOpen(HttpServletRequest req, Diary d, @RequestParam("userId") String userId) {

		//System.out.println(d.getDiaryUserId());
		//System.out.println(userId);
		
		if (uDAO.loginCheck(req)) {
			dDAO.getDiaryInfo(req, d, userId);
			req.setAttribute("popupContentPage", "popupHome.jsp");
			return "ksm_main/popup";
		} else {
			req.setAttribute("popupContentPage", "popupBack.jsp");
			return "ksm_main/popupBack";
		}

	}

	// 팝업창 - 홈
	@RequestMapping(value = "/popupHomeGo", method = RequestMethod.GET)
	public String popupHome(HttpServletRequest req, Diary d, @RequestParam("userId") String userId) {

		uDAO.loginCheck(req);
		dDAO.getDiaryInfo(req, d, userId);
		req.setAttribute("popupContentPage", "popupHome.jsp");
		return "ksm_main/popup";
	}

	// 팝업창 - 스케줄
	@RequestMapping(value = "/popupScheduleGo", method = RequestMethod.GET)
	public String popupScedule(HttpServletRequest req, Diary d, @RequestParam("userId") String userId) {

		uDAO.loginCheck(req);
		dDAO.getDiaryInfo(req, d, userId);
		req.setAttribute("popupContentPage", "popupSchedule.jsp");
		return "ksm_main/popup";
	}

	// 팝업창 - 설정 관리 들어가기
	@RequestMapping(value = "/updateMyPopup", method = RequestMethod.GET)
	public String updateMyPopup(HttpServletRequest req, Diary d, @RequestParam("userId") String userId) {

		uDAO.loginCheck(req);
		dDAO.getDiaryInfo(req, d, userId);
		req.setAttribute("popupContentPage", "updateMyPopup.jsp");
		return "ksm_main/popup";
	}

	// 다이어리 - 정보 업데이트
	@RequestMapping(value = "/diary.update", method = RequestMethod.GET)
	public String updateMyDiary(HttpServletRequest req, Diary d, @RequestParam("diaryUserId") String userId) {

		
		if (uDAO.loginCheck(req)) {
			dDAO.updateDiary(req, d, userId);
			dDAO.getDiaryInfo(req, d, userId);
			req.setAttribute("popupContentPage", "popupHome.jsp");
		} else {
			req.setAttribute("popupContentPage", "popupBack.jsp");
			return "ksm_main/popupBack";
		}

		return "ksm_main/popup";
	}

}
