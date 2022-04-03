<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ page import="com.model2.mvc.service.product.vo.*"%>
<%@ page import="com.model2.mvc.service.purchase.vo.*"%>

<%		
	ProductVO vo = (ProductVO)request.getAttribute("vo");
	System.out.println(vo);
	
	PurchaseVO po = (PurchaseVO)request.getAttribute("po");
	System.out.println(po);
 %>

<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td><%=vo.getProdNo() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${user.userId }</td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td><select 	name="paymentOption"	class="ct_input_g" 
							style="width: 100px; height: 19px" maxLength="20">
				<option value="1" selected="selected">현금구매</option>
				<option value="2">신용구매</option>
			</select>				
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td><%=po.getReceiverName() %>(</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td><%=po.getReceiverPhone() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td><%=po.getDivyAddr() %></td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td><%=po.getDivyRequest() %></td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td><%=po.getDivyDate() %></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>