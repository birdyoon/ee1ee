package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class GetPurchaseAction extends Action {

	public String execute(HttpServletRequest request,
									HttpServletResponse response) throws Exception{
		
		int tranNo=(Integer.parseInt(request.getParameter("tranNo")));
		
		PurchaseService service=new PurchaseServiceImpl();
		PurchaseVO po=service.getPurchase(tranNo);
		
		request.setAttribute("po", po);
		
		
		
		
		
		
		return "forward:/purchase/getPurchase.jsp";
	}
}
