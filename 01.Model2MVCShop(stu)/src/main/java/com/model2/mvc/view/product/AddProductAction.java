package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class AddProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		ProductVO productVO=new ProductVO();
		
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price").trim()));
		productVO.setFileName(request.getParameter("fileName"));
		System.out.println(productVO);
		
		
		
		request.setAttribute("vo", productVO);
		System.out.println("sdfds");
		
		
		if(FileUpload.isMultipartContent(request)) {
			
			String temDir =
			"C:\\workspace\\01.Model2MVCShop(ins)\\src\\main\\webapp\\images\\uploadFiles\\";
			//String temDir2 = "/uploadFiles/";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			// setSizeThreshold�� ũ�⸦ ����� �Ǹ� ������ ��ġ�� �ӽ÷� �����Ѵ�.
			fileUpload.setSizeMax(1024 * 1024 * 10);
			// �ִ� 1�ް����� ���ε� ���� (1024 * 1024 * 100) <- 100MB
			fileUpload.setSizeThreshold(1024 * 100); // �ѹ��� 100k������ �޸𸮿� ����
			
			if(request.getContentLength() < fileUpload.getSizeMax()) {
				ProductVO productVO1 = new ProductVO();
				
				StringTokenizer token = null;
				
				// parseRequest()�� Fileltem�� �����ϰ� �ִ� ListŸ���� �����Ѵ�.
				List fileltemList = fileUpload.parseRequest(request);
				int Size = fileltemList.size(); // html page���� ���� ������ ������ ���Ѵ�.
				for(int i =0 ; i<Size ; i++) {
					FileItem fileltem = (FileItem) fileltemList.get(i);
					// isFormField()�� ���ؼ� ������������ �Ķ�������� �����Ѵ�. �Ķ���Ͷ�� true
					if(fileltem.isFormField()) {
						if(fileltem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileltem.getString("euc-kr"), "-");
							String manuDate = token.nextToken()+ token.nextToken()+ token.nextToken();
							productVO1.setManuDate(manuDate);
						}
						else if(fileltem.getFieldName().equals("prodName"))
							productVO1.setProdName(fileltem.getString("euc-kr"));
						else if(fileltem.getFieldName().equals("prodDetail"))
							productVO1.setProdDetail(fileltem.getString("euc-kr"));
						else if(fileltem.getFieldName().equals("price"))
							productVO1.setPrice(Integer.parseInt(fileltem.getString("euc-kr")));
					} else { // ���������̸�..
						
						if(fileltem.getSize() > 0) { // ������ �����ϴ� if
							int idx = fileltem.getName().lastIndexOf("\\");
							// getName()�� ��θ� �� �������� ������ lastlndexOf�� �߶󳽴�
							if(idx == -1) {
								idx = fileltem.getName().lastIndexOf("/");
							}
							String fileName = fileltem.getName().substring(idx + 1);
							productVO1.setFileName(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileltem.write(uploadedFile);
							} catch (IOException e) {
								System.out.println(e);
							}
						}else {
							productVO1.setFileName("../../images/empty.GIF");
						}
					}//else
				}//for
				
				ProductService service=new ProductServiceImpl();
				service.addProduct(productVO1);
				
				request.setAttribute("prodvo", productVO1);
			}else {
				// ���ε��ϴ� ������ setSizeMax���� ū ���
				int overSize = (request.getContentLength() / 1000000 );
				System.out.println("<script>alert('������ ũ��� 1MB���� �Դϴ�. �ø��� ���� �뷮��"
						+overSize + "MB�Դϴ�');");
				System.out.println("history.back();</script>");
			}
		}else {
			System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�..");
		}
		
	//	return "forward:/product/addProduct.jsp";
		return "forward:/product/getProduct.jsp";
	}
}