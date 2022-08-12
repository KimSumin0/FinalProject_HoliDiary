package com.project.main.mj_write;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.project.main.js.UserDAO;

@Controller
public class WriteController {

	@Autowired
	private UserDAO uDAO;

	@Autowired
	private DiaryPostDAO pDAO;

	// 게시글 목록 불러오기
	@RequestMapping(value = "/post-list", method = RequestMethod.GET)
	public String listGo(HttpServletRequest req, DiaryPost p, @RequestParam("postWriter") String userId) {

		pDAO.getAllList(req, p, userId);

		req.setAttribute("popupContentPage", "../mj_write/post_list.jsp");
		return "ksm_main/popup";
	}

	// 게시글 상세보기
	@RequestMapping(value = "/post.detail.go", method = RequestMethod.GET)
	public String postDetailGo(DiaryPost p, HttpServletRequest req) {
		
		pDAO.detailPost(p, req);
		req.setAttribute("popupContentPage", "../mj_write/post_detail.jsp");

		return "ksm_main/popup";
	}

	// 글쓰기 페이지 바로가기
	@RequestMapping(value = "/write.go", method = RequestMethod.GET)
	public String writeGo(HttpServletRequest req, @RequestParam("postWriter") String userId) {

		System.out.println(userId);
		req.setAttribute("popupContentPage", "../mj_write/post_write2.jsp");
		return "ksm_main/popup";
	}
	
	@RequestMapping(value="/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request )  {
		JsonObject jsonObject = new JsonObject();
		
        /*
		 * String fileRoot = "C:\\summernote_image\\"; // 외부경로로 저장을 희망할때.
		 */
		
		// 내부경로로 저장
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		String fileRoot = contextRoot+"resources/images/";
		
		String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
		String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
		
		System.out.println("경로1: " + contextRoot);
		System.out.println("경로2: " + fileRoot);
		
		System.out.println("원래 파일명 : " + originalFileName);
		System.out.println("저장될 파일명 : " + savedFileName);
		
		File targetFile = new File(fileRoot + savedFileName);	
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
			jsonObject.addProperty("url", "resources/images/"+savedFileName);
			jsonObject.addProperty("responseCode", "success");
				
		} catch (Exception e) {
			FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		String a = jsonObject.toString();
		return a;
	}
	

	// 글 등록
	@RequestMapping(value = "/post.reg.do", method = RequestMethod.POST)
	public String postRegDo(DiaryPost p, HttpServletRequest req, 
			@RequestParam("postWriter") String userId, 
			@RequestParam("postTitle") String postTitle,
			@RequestParam("postTxt") String postTxt,
			@RequestParam("postCategory") String postCategory,
			@RequestParam("postCountry") String postCountry) {
		System.out.println(postTxt);
		if (uDAO.loginCheck(req)) {
			pDAO.regPost(p, req, userId, postTitle, postTxt, postCategory, postCountry);
			pDAO.detailPost(p, req);
		}
		System.out.println(userId);

		req.setAttribute("popupContentPage", "../mj_write/post_detail.jsp");
		return "ksm_main/popup";
	}

	/*// 이미지 업로드
	@ResponseBody
	@RequestMapping(value = "/fileupload.do")
	public void communityImageUpload(HttpServletRequest req, HttpServletResponse resp,
			MultipartHttpServletRequest multiFile) throws Exception {
		JsonObject jsonObject = new JsonObject();
		PrintWriter printWriter = null;
		OutputStream out = null;
		MultipartFile file = multiFile.getFile("upload");

		if (file != null) {
			if (file.getSize() > 0 && StringUtils.isNotBlank(file.getName())) {
				if (file.getContentType().toLowerCase().startsWith("image/")) {
					try {

						String fileName = file.getOriginalFilename();
						byte[] bytes = file.getBytes();

						String uploadPath = req.getSession().getServletContext().getRealPath("/resources/images/"); // 저장경로
						System.out.println("uploadPath:" + uploadPath);

						File uploadFile = new File(uploadPath);
						if (!uploadFile.exists()) {
							uploadFile.mkdir();
						}
						
						DiaryPost p = new DiaryPost();
						String fileName2 = UUID.randomUUID().toString();
						uploadPath = uploadPath + "/" + fileName2 + fileName;

						p.setPostImg(fileName2);
						out = new FileOutputStream(new File(uploadPath));
						out.write(bytes);

						printWriter = resp.getWriter();
						String fileUrl = req.getContextPath() + "/resources/images/" + fileName2 + fileName; // url경로
						System.out.println("fileUrl :" + fileUrl);
						JsonObject json = new JsonObject();
						json.addProperty("uploaded", 1);
						json.addProperty("fileName", fileName);
						json.addProperty("url", fileUrl);
						printWriter.print(json);
						System.out.println(json);

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (out != null) {
							out.close();
						}
						if (printWriter != null) {
							printWriter.close();
						}
					}
				}

			}

		}
	}*/

	// 지도 만들기
	@RequestMapping(value = "/map.open", method = RequestMethod.GET)
	public String mapOpen(HttpServletRequest req) {

		return "mj_map/map";
	}
}
