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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import album.model.AlbumBean;
import album.model.AlbumDao;

@Controller
public class AlbumUpdateController {
	
	
	private static final String getPage = "AlbumUpdateForm";
	private static final String gotoPage =  "redirect:/list.ab";
	private static final String command = "/update.ab";
	
	
	@Autowired
	private AlbumDao albumDao;
	
	// 어디에서 요청하는지 쓰기
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String doActionGet(
			@RequestParam(value="num",required=true) int num,
			@RequestParam(value="pageNumber",required=true) int pageNumber,
			@RequestParam(value="pageSize",required=true) int pageSize,
			Model model){
		
		System.out.println("AlbumUpdateController Get 방식 들어옴");
		
		System.out.println("num="+num);
		System.out.println("pageNumber="+pageNumber);
		System.out.println("pageSize="+pageSize);
		
		AlbumBean album =  albumDao.GetAlbum(num);
		model.addAttribute("album" , album);
		model.addAttribute("num" , num);
		model.addAttribute("pageNumber" , pageNumber);
		model.addAttribute("pageSize" , pageSize);
		
		return getPage;
	}
	
	
	
	// 어디에서 요청하는지 쓰기 
	@RequestMapping(value=command, method=RequestMethod.POST)
	public ModelAndView doActionGet(
			@RequestParam(value="num",required=true) String num,
			@RequestParam(value="pageNumber",required=true) String pageNumber,
			@RequestParam(value="pageSize",required=true) String pageSize,
			@ModelAttribute("album") @Valid AlbumBean album, BindingResult result,
			HttpServletRequest request){
		
		
		String title = request.getParameter("title");
		String singer = request.getParameter("singer");
		System.out.println(num +"/"+title+"/"+ singer+"/"+pageNumber);
		//System.out.println(album.getTitle());
		
		System.out.println("AlbumUpdateController Post 방식 들어옴");
		
		ModelAndView mav = new ModelAndView();	
		

		if(result.hasErrors()){
			mav.addObject("pageNumber", pageNumber);  
			mav.addObject("pageSize", pageSize);  
			mav.setViewName(getPage); // "AlbumUpdateForm"
			return mav; 
		}

		
		int cnt = albumDao.updateAlbum(album);
		if(cnt != -1){
			mav.setViewName(
				gotoPage+"?pageNumber="+pageNumber+"&pageSize="+pageSize);
		}else{
			mav.setViewName(getPage);
		}
		return mav;
	}
}




