package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseViewAction extends Action {

	
	public String execute( HttpServletRequest request,
											HttpServletResponse response ) throws Exception{
		
//		String userId = request.getParameter("userId");
//		System.out.println("userId : "+userId);
//		
//		UserService uservice=new UserServiceImpl();
//		UserVO uo=uservice.getUser(userId);
				
		
		int prodNo= (Integer.parseInt(request.getParameter("prodNo")));
		System.out.println("prodNo : "+prodNo);
		
		ProductService service=new ProductServiceImpl();
		ProductVO vo=service.getProduct(prodNo);
		
		HttpSession session=request.getSession();
		UserVO user = (UserVO)session.getAttribute("user");
		System.out.println("userId : "+user);
		
//		PurchaseService service= new PurchaseServiceImpl();
//		PurchaseVO purchaseVO=service.getPurchase(tranNo);
		
		request.setAttribute("vo", vo);
		System.out.println(vo);
		
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
}
