package com.project.main.sm;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.main.js.Subscribe;
import com.project.main.js.User;
import com.project.main.js.UserDAO;

@Controller
public class PopupController {

	@Autowired
	private UserDAO uDAO;

	@Autowired
	private DiaryDAO dDAO;
	
	@Autowired
	private CategoryDAO cDAO;

	// 팝업창 - 오픈
	@RequestMapping(value = "/popup.open", method = RequestMethod.GET)
	public String popupOpen(HttpServletRequest req, Diary d, User u, Category cate, Subscribe s, @RequestParam("userId") String userId) {

		// System.out.println(d.getDiaryUserId());
		// System.out.println(userId);

		if (uDAO.loginCheck(req)) {
			dDAO.getDiaryInfo(req, d, userId, u, cate, s);
			req.setAttribute("popupContentPage", "popupHome.jsp");
			return "ksm_main/popup";
		} else {
			req.setAttribute("popupContentPage", "popupBack.jsp");
			return "ksm_main/popupBack";
		}

	}

	// 팝업창 - 홈
	@RequestMapping(value = "/popupHomeGo", method = RequestMethod.GET)
	public String popupHome(HttpServletRequest req, Diary d, User u, Category cate, Subscribe s, @RequestParam("userId") String userId) {

		uDAO.loginCheck(req);
		dDAO.getDiaryInfo(req, d, userId, u, cate, s);
		req.setAttribute("popupContentPage", "popupHome.jsp");
		return "ksm_main/popup";
	}

	// 팝업창 - 스케줄
	@RequestMapping(value = "/popupScheduleGo", method = RequestMethod.GET)
	public String popupScedule(HttpServletRequest req, Diary d, User u, Category cate, Subscribe s, @RequestParam("userId") String userId) {

		uDAO.loginCheck(req);
		dDAO.getDiaryInfo(req, d, userId, u, cate, s);
		req.setAttribute("popupContentPage", "popupSchedule.jsp");
		return "ksm_main/popup";
	}

	// 다이어리 - 설정 관리 들어가기
	@RequestMapping(value = "/updateMyPopup", method = RequestMethod.GET)
	public String updateMyPopup(HttpServletRequest req, Diary d, User u, Category cate, Subscribe s, @RequestParam("userId") String userId) {

		uDAO.loginCheck(req);
		dDAO.getDiaryInfo(req, d, userId, u, cate, s);
		req.setAttribute("popupContentPage", "updateMyPopup.jsp");
		return "ksm_main/popup";
	}

	// 다이어리 - 정보 업데이트
	@RequestMapping(value = "/diary.update", method = RequestMethod.GET)
	public String updateMyDiary(HttpServletRequest req, Diary d, User u, Category cate, Subscribe s, @RequestParam("diaryUserId") String userId) {

		if (uDAO.loginCheck(req)) {
			dDAO.updateDiary(req, d, userId);
			dDAO.getDiaryInfo(req, d, userId, u, cate, s);
			req.setAttribute("popupContentPage", "popupHome.jsp");
		} else {
			req.setAttribute("popupContentPage", "popupBack.jsp");
			return "ksm_main/popupBack";
		}

		return "ksm_main/popup";
	}

	// 다이어리 - 카테고리 업데이트 들어가기
	@RequestMapping(value = "/updateCategory", method = RequestMethod.GET)
	public String updateCategory(HttpServletRequest req, Diary d, User u, Category cate, Subscribe s, @RequestParam("userId") String userId) {

		uDAO.loginCheck(req);
		dDAO.getDiaryInfo(req, d, userId, u, cate, s);
		req.setAttribute("popupContentPage", "updateCategory.jsp");
		return "ksm_main/popup";
	}
	
	// 다이어리 - 카테고리 업데이트 (추가)
	@RequestMapping(value = "/category.add", method = RequestMethod.GET)
	public String updateCategoryAdd(HttpServletRequest req, Diary d, User u, Subscribe s, @RequestParam("diaryUserId") String userId, Category cate) {
		
		if (uDAO.loginCheck(req)) {
//			dDAO.updateDiary(req, d, userId);
			cDAO.updateCategoryAdd(req, d, userId, cate);
			dDAO.getDiaryInfo(req, d, userId, u, cate, s);
			req.setAttribute("popupContentPage", "popupHome.jsp");
		} else {
			req.setAttribute("popupContentPage", "popupBack.jsp");
			return "ksm_main/popupBack";
		}
		
		return "ksm_main/popup";
	}
	
	// 다이어리 - 카테고리 업데이트 (수정 및 삭제)
	@RequestMapping(value = "/category.update", method = RequestMethod.GET)
	public String updateCategoryUp(HttpServletRequest req, Diary d, User u, Subscribe s, @RequestParam("diaryUserId") String userId, 
			Category cate, @RequestParam("newCategory") String newCategory) {
		
		if (uDAO.loginCheck(req)) {
			cDAO.updateCategoryup(req, d, userId, cate, newCategory);
			dDAO.getDiaryInfo(req, d, userId, u, cate, s);
			req.setAttribute("popupContentPage", "popupHome.jsp");
		} else {
			req.setAttribute("popupContentPage", "popupBack.jsp");
			return "ksm_main/popupBack";
		}
		
		return "ksm_main/popup";
	}
	
	// 다이어리 - 스케줄러 추가
	@ResponseBody
	@RequestMapping(value = "/schedule.insert", method = RequestMethod.GET)
	public int insertSchedule(HttpServletRequest req, Schedule s) {
		/*System.out.println("==============================");
		System.out.println(req.getParameter("ScheduleUserId"));
		System.out.println(req.getParameter("ScheduleTitle"));
		System.out.println(req.getParameter("ScheduleDate"));
		System.out.println(req.getParameter("ScheduleStartTime1"));
		System.out.println(req.getParameter("ScheduleEndTime1"));
		System.out.println("==============================");*/
		
		uDAO.loginCheck(req);
		return dDAO.insertSchedule(req, s);
	}
	
	// 다이어리 - 스케줄러 내용 삭제
	@ResponseBody
	@RequestMapping(value = "/schedule.delete", method = RequestMethod.GET)
	public int deleteSchedule(HttpServletRequest req, Schedule s) {
		
		System.out.println(req.getParameter("ScheduleNum"));
		
		uDAO.loginCheck(req);
		return dDAO.deleteSchedule(req, s);
	}
	
	// 스케줄러 정보 얻기
	@ResponseBody
	@RequestMapping(value = "/schedule.get", method = RequestMethod.GET, produces="application/json")
	public Schedule getSchedule(HttpServletRequest req, Schedule s) {
 System.out.println(s.getScheduleDate());
System.out.println(s.getScheduleUserId());
 
		return dDAO.getSchedule(s);
	}
	
	// 다이어리 - 메인 이미지 수정 들어가기
	@RequestMapping(value = "/mainImg.updateGo", method = RequestMethod.GET)
	public String updateMainImgGo(HttpServletRequest req, Diary d, User u, Category cate, Subscribe s, @RequestParam("userId") String userId) {
		
		uDAO.loginCheck(req);
		dDAO.getDiaryInfo(req, d, userId, u, cate, s);
		req.setAttribute("popupContentPage", "updateMainImg.jsp");
		return "ksm_main/popup";
	}
	
	// 다이어리 - 메인 이미지 (수정)
	@RequestMapping(value = "/mainImg.upload", method = RequestMethod.POST)
	public String updateMainImg(HttpServletRequest req, Diary d, User u, Category cate, Subscribe s,
			@RequestParam("userId") String userId, @RequestParam("mainImg") MultipartFile file) {
		
		System.out.println("ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ");
		if (uDAO.loginCheck(req)) {
			System.out.println("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ");
			dDAO.updateMainImg(req, d, userId, file);
			System.out.println("ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");
			dDAO.getDiaryInfo(req, d, userId, u, cate, s);
			req.setAttribute("popupContentPage", "popupHome.jsp");
		} else {
			req.setAttribute("popupContentPage", "popupBack.jsp");
			return "ksm_main/popupBack";
		}
		
		return "ksm_main/popup";
	}
	
	// 스케줄러 전체 정보 얻기
	@ResponseBody
	@RequestMapping(value = "/schedule.getAllEvents", produces="application/json")
	public String[] getAllEvents(HttpServletRequest req) {
		return dDAO.getAllEvent(req);
	}

	
}
