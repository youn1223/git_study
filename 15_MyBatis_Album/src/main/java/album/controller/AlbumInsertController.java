package album.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import album.model.AlbumBean;
import album.model.AlbumDao;

@Controller
public class AlbumInsertController { 
	
	private static final String getPage = "AlbumInsertForm";
	private static final String gotoPage = "redirect:/list.ab";
	private static final String command = "/insert.ab";
	
	@Autowired
	private AlbumDao albumDao;
	
	// 목록보기(AlbumList.jsp에서 추가하기 클릭하면 요청)
	@RequestMapping(value=command, method=RequestMethod.GET) 
	public String doActionGet(){
		System.out.println(this.getClass() + " Get 방식 들어옴"); 
		
		return getPage;		
	}
	
	
	// AlbumInsertForm.jsp에서 추가하기 클릭하면 요청
	@RequestMapping(value=command, method=RequestMethod.POST) 
	public ModelAndView doActionPost(
			@ModelAttribute("album") @Valid AlbumBean album, 
			BindingResult bindingResult){
		
		System.out.println(this.getClass() + " POST 방식 들어옴"); 

		
		ModelAndView mav = new ModelAndView();
		
		if(bindingResult.hasErrors()){
			System.out.println("유효성 검사 오류입니다");
			mav.setViewName(getPage); // AlbumInsertForm.jsp
			return mav;
		}
		
		int cnt = -1;
		cnt = albumDao.InsertAlbum(album);
		mav.setViewName(gotoPage); // redirect는 get방식 요청이다.
		return mav;		
	}
	
	
}


