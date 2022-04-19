package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class UpdatePurchaseAction extends Action {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
	
		int tranNo= Integer.parseInt(request.getParameter("tranNo"));
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("user");
				
		PurchaseVO purchaseVO=new PurchaseVO();
		purchaseVO.setTranNo(tranNo);
		purchaseVO.setBuyer(userVO);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("divyAddr"));
		purchaseVO.setDivyRequest(request.getParameter("divyRequest"));
		purchaseVO.setDivyDate(request.getParameter("divyDate"));
		System.out.println(request.getParameter("divyAddr"));
		
		PurchaseService service=new PurchaseServiceImpl();
		service.updatePurchase(purchaseVO);
	
		
	
	//	return "forward:/updatePurchase.jsp";
		return "redirect:/getPurchase.do?tranNo=" +tranNo;
	//	return "forward:/getPurchase.jsp";
	}
}
