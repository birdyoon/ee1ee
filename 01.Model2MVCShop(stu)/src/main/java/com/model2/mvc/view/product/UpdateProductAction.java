package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;


public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
//		String prodNo=(request.getParameter("prodNo"));
//		
//		ProductVO productVO=new ProductVO();
//		productVO.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
//		productVO.setProdName(request.getParameter("prodName"));
//		productVO.setProdDetail(request.getParameter("prodDetail"));
//		productVO.setManuDate(request.getParameter("manuDate"));
//		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
//		productVO.setFileName(request.getParameter("fileName"));
		
	//	ProductService service=new ProductServiceImpl();
	//	service.updateProduct(productVO);
		
//		HttpSession session=request.getSession();
//		session.setAttribute("product", session);
//		session=((ProductVO)session.setAttribute("product")).getProdNo();
//		System.out.println("product");
		
//		if(sessionId == (prodNo)){
//			session.setAttribute("product", productVO);
//		}
//		System.out.println("product");
		
		
		
		int prodNo =0;
		
		if(FileUpload.isMultipartContent(request)) {
			
			String temDir =
			"C:\\workspace\\ee1ee\\01.Model2MVCShop(stu)\\src\\main\\webapp\\images\\uploadFiles\\";
			//String temDir2 = "/uploadFiles/";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			// setSizeThreshold?? ?????? ???????? ???? ?????? ?????? ?????? ????????.
			fileUpload.setSizeMax(1024 * 1024 * 10);
			// ???? 1???????? ?????? ???? (1024 * 1024 * 100) <- 100MB
			fileUpload.setSizeThreshold(1024 * 100); // ?????? 100k?????? ???????? ????
			
			if(request.getContentLength() < fileUpload.getSizeMax()) {
				ProductVO productVO = new ProductVO();
				
				StringTokenizer token = null;
		
				ProductService service=new ProductServiceImpl();
				
				
				// parseRequest()?? Fileltem?? ???????? ???? List?????? ????????.
				List fileltemList = fileUpload.parseRequest(request);
				int Size = fileltemList.size(); // html page???? ???? ?????? ?????? ??????.
				for(int i =0 ; i<Size ; i++) {
					FileItem fileltem = (FileItem) fileltemList.get(i);
					// isFormField()?? ?????? ???????????? ???????????? ????????. ???????????? true
					if(fileltem.isFormField()) {
						if(fileltem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileltem.getString("euc-kr"), "-");
							String manuDate = token.nextToken();
							while(token.hasMoreTokens())
								manuDate+= token.nextToken();
							productVO.setManuDate(manuDate);
						}
						else if(fileltem.getFieldName().equals("prodName"))
							productVO.setProdName(fileltem.getString("euc-kr"));
						else if(fileltem.getFieldName().equals("prodDetail"))
							productVO.setProdDetail(fileltem.getString("euc-kr"));
						else if(fileltem.getFieldName().equals("price"))
							productVO.setPrice(Integer.parseInt(fileltem.getString("euc-kr")));
						else if(fileltem.getFieldName().equals("prodNo")) {
							prodNo = Integer.parseInt(fileltem.getString("euc-kr"));
							productVO.setProdNo(prodNo);
						}
					} else { // ????????????..
						
						if(fileltem.getSize() > 0) { // ?????? ???????? if
							int idx = fileltem.getName().lastIndexOf("\\");
							// getName()?? ?????? ?? ???????? ?????? lastlndexOf?? ????????
							if(idx == -1) {
								idx = fileltem.getName().lastIndexOf("/");
							}
							String fileName = fileltem.getName().substring(idx + 1);
							productVO.setFileName(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileltem.write(uploadedFile);
							} catch (IOException e) {
								System.out.println(e);
							}
						}else {
							productVO.setFileName("../../images/empty.GIF");
						}
					}//else
				}//for
				service.updateProduct(productVO);
				
				request.setAttribute("prodvo", productVO);
			}else {
				// ?????????? ?????? setSizeMax???? ?? ????
				int overSize = (request.getContentLength() / 1000000 );
				System.out.println("<script>alert('?????? ?????? 1MB???? ??????. ?????? ???? ??????"
						+overSize + "MB??????');");
				System.out.println("history.back();</script>");
			}
		}else {
			System.out.println("?????? ?????? multipart/form-data?? ????????..");
		}
				
		
	//	return "forward:/getProduct.do?prodNo="+prodNo;
		return "redirect:/getProduct.do?prodNo="+prodNo+"&menu=ok";		
	}
}