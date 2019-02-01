package album.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import album.model.AlbumBean;
import album.model.AlbumDao;
import utility.Paging;

@Controller
public class AlbumListController {
	private static final String getPage = "AlbumList";
	private static final String command = "/list.ab"; 

	@Autowired
	@Qualifier("myAlbumDao") 
	private AlbumDao albumDao;

	
	@RequestMapping(value=command)
	public ModelAndView doActionPost(
			@RequestParam(value="whatColumn",required=false) String whatColumn,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="pageNumber",required = false) String pageNumber,
			@RequestParam(value="pageSize",required = false) String pageSize,
			HttpServletRequest request, Model model) {
		
		System.out.println("method:"+request.getMethod());
		System.out.println("검색할 컬럼(whatColumn) : " + whatColumn + ", ");
		System.out.println("검색할 단어(keyword) : " + keyword + ", ");
		System.out.println("pageNumber : " + pageNumber + ", ");
		System.out.println("pageSize : " + pageSize + ", "); //한 페이지에 보여줄 건수(레코드갯수)

		/*
		맨처음에 list.ab가 요청되면
		검색할 컬럼(whatColumn) : null, 
		검색할 단어(keyword) : null, 
		pageNumber : null, 
		pageSize : null, 
		
		2페이지 클릭하면
		검색할 컬럼(whatColumn) : null, 
		검색할 단어(keyword) : null, 
		pageNumber : 2, 
		pageSize : 2, 

		제목에 a넣고 2페이지 클릭하면
		검색할 컬럼(whatColumn) : title, 
		검색할 단어(keyword) : a, 
		pageNumber : 2, 
		pageSize : 2, 
		*/
		
		Map<String, String> map = new HashMap<String, String>() ;	

		map.put("whatColumn", whatColumn ) ;
		map.put("keyword", "%" + keyword + "%" ) ;
		//map.put("keyword", "%하하%" ) ;
		/*whatColumn=title keyword=%날%
		whatColumn=singer keyword=%유%*/
		
		
		
		int totalCount = albumDao.GetTotalCount( map );
		System.out.println("전체 행수(totalCount) : " + totalCount + ", ");
		
		String url = request.getContextPath() +  this.command ;
		System.out.println("url : "+url); // url : /ex/list.ab

		ModelAndView mav = new ModelAndView();
		
		
		Paging pageInfo 
		= new Paging(pageNumber,pageSize, totalCount,url,whatColumn, keyword); 
		
		System.out.println();
		System.out.println("offset : " + pageInfo.getOffset());// offset:0, 3페이지 클릭하면 offset : 4
		System.out.println("limit : " + pageInfo.getLimit() ); //limit : 2, 항상 2로 나온다. 한페이지에 나오는 레코드 갯수
		System.out.println("url : " + pageInfo.getUrl()) ;// url : /ex/list.ab
		System.out.println("pageInfo.getPageNumber() : "+
									pageInfo.getPageNumber());
		
		List<AlbumBean> albumLists = albumDao.GetAlbumList( pageInfo, map );
		// map에는 whatColumn, keyword가 담겨있다.

		System.out.println("조회된 건수: " + albumLists.size());
		
		mav.addObject( "albumLists", albumLists);//albumLists:한페이지에 나올 레코드	
		mav.addObject( "pageInfo", pageInfo ); 
		mav.setViewName(getPage); // "AlbumList"
		return mav;

	} 
}
