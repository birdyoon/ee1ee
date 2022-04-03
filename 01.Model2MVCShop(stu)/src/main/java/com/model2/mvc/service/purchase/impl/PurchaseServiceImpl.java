package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;

import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseServiceImpl implements PurchaseService {

	private PurchaseDAO purchaseDAO;
//	private ProductDAO productDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO=new PurchaseDAO();
//		productDAO=new ProductDAO();
	}
	public void addPurchase(PurchaseVO purchaseVO) throws Exception{
		purchaseDAO.insertPurchase(purchaseVO);
	}
	
	public PurchaseVO getPurchase(int tranNo) throws Exception{
		return purchaseDAO.findPurchase(tranNo);
	}
//	
//	public HashMap<String,Object> getPurchaseList(String SearchVO) throws Exception{
//		return purchaseDAO.getPurchaseList(searchVO);
//	}
//	
//	public PurchaseVO updatePurchase(PurchaseVO purchaseVO) throws Exception{
//		return purchaseDAO.updatePurchase(purchaseVO);
//	}
//	
//	public void updateTranCode(PurchaseVO purchaseVO) throws Exception{
//		return purchaseDAO.updateTranCode(purchaseVO);
//	}
	
	
	
	
}


