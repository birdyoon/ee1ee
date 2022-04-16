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
			// setSizeThreshold의 크기를 벗어나게 되면 지정한 위치에 임시로 저장한다.
			fileUpload.setSizeMax(1024 * 1024 * 10);
			// 최대 1메가까지 업로드 가능 (1024 * 1024 * 100) <- 100MB
			fileUpload.setSizeThreshold(1024 * 100); // 한번에 100k까지는 메모리에 저장
			
			if(request.getContentLength() < fileUpload.getSizeMax()) {
				ProductVO productVO1 = new ProductVO();
				
				StringTokenizer token = null;
				
				// parseRequest()는 Fileltem을 포함하고 있는 List타입의 리턴한다.
				List fileltemList = fileUpload.parseRequest(request);
				int Size = fileltemList.size(); // html page에서 받은 값들의 개수를 구한다.
				for(int i =0 ; i<Size ; i++) {
					FileItem fileltem = (FileItem) fileltemList.get(i);
					// isFormField()를 통해서 파일형식인지 파라미터인지 구분한다. 파라미터라면 true
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
					} else { // 파일형식이면..
						
						if(fileltem.getSize() > 0) { // 파일을 저장하는 if
							int idx = fileltem.getName().lastIndexOf("\\");
							// getName()은 경로를 다 가져오기 때문에 lastlndexOf로 잘라낸다
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
				// 업로드하는 파일이 setSizeMax보다 큰 경우
				int overSize = (request.getContentLength() / 1000000 );
				System.out.println("<script>alert('파일의 크기는 1MB까지 입니다. 올리신 파일 용량은"
						+overSize + "MB입니다');");
				System.out.println("history.back();</script>");
			}
		}else {
			System.out.println("인코딩 타입이 multipart/form-data가 아닙니다..");
		}
		
	//	return "forward:/product/addProduct.jsp";
		return "forward:/product/getProduct.jsp";
	}
}