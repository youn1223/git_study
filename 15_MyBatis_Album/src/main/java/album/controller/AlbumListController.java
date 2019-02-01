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
		System.out.println("�˻��� �÷�(whatColumn) : " + whatColumn + ", ");
		System.out.println("�˻��� �ܾ�(keyword) : " + keyword + ", ");
		System.out.println("pageNumber : " + pageNumber + ", ");
		System.out.println("pageSize : " + pageSize + ", "); //�� �������� ������ �Ǽ�(���ڵ尹��)

		/*
		��ó���� list.ab�� ��û�Ǹ�
		�˻��� �÷�(whatColumn) : null, 
		�˻��� �ܾ�(keyword) : null, 
		pageNumber : null, 
		pageSize : null, 
		
		2������ Ŭ���ϸ�
		�˻��� �÷�(whatColumn) : null, 
		�˻��� �ܾ�(keyword) : null, 
		pageNumber : 2, 
		pageSize : 2, 

		���� a�ְ� 2������ Ŭ���ϸ�
		�˻��� �÷�(whatColumn) : title, 
		�˻��� �ܾ�(keyword) : a, 
		pageNumber : 2, 
		pageSize : 2, 
		*/
		
		Map<String, String> map = new HashMap<String, String>() ;	

		map.put("whatColumn", whatColumn ) ;
		map.put("keyword", "%" + keyword + "%" ) ;
		//map.put("keyword", "%����%" ) ;
		/*whatColumn=title keyword=%��%
		whatColumn=singer keyword=%��%*/
		
		
		
		int totalCount = albumDao.GetTotalCount( map );
		System.out.println("��ü ���(totalCount) : " + totalCount + ", ");
		
		String url = request.getContextPath() +  this.command ;
		System.out.println("url : "+url); // url : /ex/list.ab

		ModelAndView mav = new ModelAndView();
		
		
		Paging pageInfo 
		= new Paging(pageNumber,pageSize, totalCount,url,whatColumn, keyword); 
		
		System.out.println();
		System.out.println("offset : " + pageInfo.getOffset());// offset:0, 3������ Ŭ���ϸ� offset : 4
		System.out.println("limit : " + pageInfo.getLimit() ); //limit : 2, �׻� 2�� ���´�. ���������� ������ ���ڵ� ����
		System.out.println("url : " + pageInfo.getUrl()) ;// url : /ex/list.ab
		System.out.println("pageInfo.getPageNumber() : "+
									pageInfo.getPageNumber());
		
		List<AlbumBean> albumLists = albumDao.GetAlbumList( pageInfo, map );
		// map���� whatColumn, keyword�� ����ִ�.

		System.out.println("��ȸ�� �Ǽ�: " + albumLists.size());
		
		mav.addObject( "albumLists", albumLists);//albumLists:���������� ���� ���ڵ�	
		mav.addObject( "pageInfo", pageInfo ); 
		mav.setViewName(getPage); // "AlbumList"
		return mav;

	} 
}
