package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class AddPurchaseAction extends Action {

	public String execute(HttpServletRequest request,
												HttpServletResponse response) throws Exception { 
		
		int prodNo= (Integer.parseInt(request.getParameter("prodNo")));
		System.out.println("2 : "+prodNo);
		ProductService service=new ProductServiceImpl();
		ProductVO vo=service.getProduct(prodNo);
		
		String userId= request.getParameter("buyerId");
		UserService uservice=new UserServiceImpl();
		UserVO uo=uservice.getUser(userId);
		System.out.println(uo);
				
	//	String tranNo= request.getParameter("tranNo");
		
		PurchaseVO purchaseVO= new PurchaseVO();
		purchaseVO.setBuyer(uo);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("divyAddr"));
		purchaseVO.setDivyRequest(request.getParameter("divyRequest"));
		purchaseVO.setDivyDate(request.getParameter("divyDate"));
		purchaseVO.setPurchaseProd(vo);
		purchaseVO.setTranCode("001");
	//	purchaseVO.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		System.out.println(purchaseVO);
		
		PurchaseService Pservice=new PurchaseServiceImpl();
		Pservice.addPurchase(purchaseVO);
	
		
		request.setAttribute("vo", vo);
		request.setAttribute("po", purchaseVO );
			
		System.out.println(vo);
		
//		HttpSession session=request.getSession();
//		UserVO userId = (UserVO)session.getAttribute("user");
//		System.out.println("userId : "+userId);
		
		
		
		
		
		
		return "forward:/purchase/addPurchase.jsp";
	}
}
