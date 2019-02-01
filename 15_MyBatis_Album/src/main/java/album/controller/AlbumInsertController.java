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
	
	// ��Ϻ���(AlbumList.jsp���� �߰��ϱ� Ŭ���ϸ� ��û)
	@RequestMapping(value=command, method=RequestMethod.GET) 
	public String doActionGet(){
		System.out.println(this.getClass() + " Get ��� ����"); 
		
		return getPage;		
	}
	
	
	// AlbumInsertForm.jsp���� �߰��ϱ� Ŭ���ϸ� ��û
	@RequestMapping(value=command, method=RequestMethod.POST) 
	public ModelAndView doActionPost(
			@ModelAttribute("album") @Valid AlbumBean album, 
			BindingResult bindingResult){
		
		System.out.println(this.getClass() + " POST ��� ����"); 

		
		ModelAndView mav = new ModelAndView();
		
		if(bindingResult.hasErrors()){
			System.out.println("��ȿ�� �˻� �����Դϴ�");
			mav.setViewName(getPage); // AlbumInsertForm.jsp
			return mav;
		}
		
		int cnt = -1;
		cnt = albumDao.InsertAlbum(album);
		mav.setViewName(gotoPage); // redirect�� get��� ��û�̴�.
		return mav;		
	}
	
	
}


