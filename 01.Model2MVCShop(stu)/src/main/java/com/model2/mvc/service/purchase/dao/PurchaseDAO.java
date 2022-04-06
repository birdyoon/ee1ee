package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {

	public PurchaseDAO() {
		
	}
	
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO transaction values (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,?,?)";	
			
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		pStmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		pStmt.setString(2, purchaseVO.getBuyer().getUserId());
		pStmt.setString(3, purchaseVO.getPaymentOption());
		pStmt.setString(4, purchaseVO.getReceiverName());
		pStmt.setString(5, purchaseVO.getReceiverPhone());
		pStmt.setString(6, purchaseVO.getDivyAddr());
		pStmt.setString(7, purchaseVO.getDivyRequest());
		pStmt.setString(8, purchaseVO.getTranCode());
		pStmt.setDate(9, purchaseVO.getOrderDate());
		pStmt.setString(10, purchaseVO.getDivyDate());
		pStmt.executeUpdate();
		
		con.close();
	}
	
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String userId) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction ";
//		if (searchVO.getSearchCondition() != null) {
//			if (searchVO.getSearchCondition().equals("0")) {
//				sql += " WHERE prod_no='" + searchVO.getSearchKeyword()
//						+ "'";
//			} else if (searchVO.getSearchCondition().equals("1")) {
//				sql += " WHERE prod_name='" + searchVO.getSearchKeyword()
//						+ "'";
//			}
//		}
		sql += " WHERE buyer_Id='" + userId+"'"; 
		sql += " ORDER BY buyer_Id";
		
		PreparedStatement stmt = 
				con.prepareStatement(	sql,
															ResultSet.TYPE_SCROLL_INSENSITIVE,
															ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery();
		
			rs.last();
			int total = rs.getRow();
			System.out.println("로우의 수:" + total);

			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("count", new Integer(total));

			rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
			System.out.println("searchVO.getPage():" + searchVO.getPage());
			System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
			
			ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
			if (total > 0) {
				for (int i = 0; i < searchVO.getPageUnit(); i++) {
					PurchaseVO po = new PurchaseVO();
					UserVO user = new UserVO();
					po.setTranNo(Integer.parseInt(rs.getString("tran_no")));
					user.setUserId(rs.getString("buyer_Id"));
					po.setBuyer(user);
					po.setPaymentOption(rs.getString("payment_Option"));
					po.setReceiverName(rs.getString("receiver_Name"));
					po.setReceiverPhone(rs.getString("receiver_Phone"));
					po.setDivyAddr(rs.getString("dlvy_Addr"));
					po.setDivyRequest(rs.getString("dlvy_Request"));
					po.setDivyDate(rs.getString("dlvy_Date"));
					po.setOrderDate(rs.getDate("order_Date"));
					
					list.add(po);
					if (!rs.next())
						break;
				}
			}
			System.out.println("list.size() : "+ list.size());
			map.put("list", list);
			System.out.println("map().size() : "+ map.size());

			con.close();
					
			return map;
	}
	
}
	
	
	
	
	

