package album.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import album.model.AlbumBean;
import album.model.AlbumDao;

@Controller
public class AlbumDetailViewController {
	private static final String getPage = "AlbumDetailView";
	private static final String command = "/detail.ab";
	
	@Autowired
	private AlbumDao albumDao; 
	
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String doActionGet(
			@RequestParam(value="num", required=true) int num,
			@RequestParam(value="pageNumber", required=false) int pageNumber,
			Model model){
		
		System.out.println("Get ¹æ½Ä µé¾î¿È");
		System.out.println("num:"+num);
		System.out.println("pageNumber:"+pageNumber);
		
		
		AlbumBean album = albumDao.GetAlbum(num);
		model.addAttribute("album",album);
		model.addAttribute("pageNumber",pageNumber);
		return getPage;		
	}
	
}
