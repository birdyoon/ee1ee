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

<!--  <form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post"> -->

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<td><%=vo.getProdNo() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<td>${user.userId }</td>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td><% if(po.getPaymentOption().equals("1")) { %>	
						���ݱ���
			  <% }else if(po.getPaymentOption().equals("2")) { %>	
						�ſ뱸��
			  <% } %>		
		</td>			
		
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td><%=po.getReceiverName() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td><%=po.getReceiverPhone() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td><%=po.getDivyAddr() %></td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td><%=po.getDivyRequest() %></td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td><%=po.getDivyDate() %></td>
		<td></td>
	</tr>
</table>
<!-- </form>  -->

</body>
</html>