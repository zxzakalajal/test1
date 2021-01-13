<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의하기</title>
<style type="text/css">
	#container{
		width:1200px;
		margin:0 auto;
	}
	#qna_form{
		width:650px;
		margin:0 auto;
	}
	#qna_form table{
		border-collapse: collapse;
		box-sizing: border-box;
		width:100%;
	}
	#qna_form td{
		padding:5px;
	}
	#qna_form table tr td:first-child{
		width:500px;
		height: 40px;
	}
	#qna_form table tr:nth-child(2){
		height: 100px;
	}
	#qna_form input, #qna_form textarea{
		width:100%;
		height: 100%;
		border-radius: 5px;
		box-sizing: border-box;
	} 
	 #qna_form textarea{
	 	padding:5px;
	 	resize: none;
	 }
	 
	 button{
	 	width: 100%;
	 	height: 140px;
	 }
	
</style>
</head>
<body>
	<jsp:include page="template/header.jsp"></jsp:include>
	<div id="container">
		<div id="qna_form">
			<form action="sendQnA.do">
				<table>
					<tr>
						<td><input type="text" name="title" placeholder="제목을 입력하세요"></td>
						<td rowspan="2"><button>작성완료</button></td>
					</tr>
					<tr>
						<td>
							<textarea name="content" placeholder="문의 내용을 입력해 주세요"></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<hr>
		<div id="qna_list">
			<!-- 질문 답변 목록 -->
			<c:forEach var="dto" items="${requestScope.list }">
				${dto.title } | ${dto.writer } | ${dto.date } | 
				<c:choose>
					<c:when test="${dto.status==0}">않읽음</c:when>
					<c:when test="${dto.status==1}">읽음	</c:when>
					<c:otherwise>답변완료</c:otherwise>
				</c:choose>
				<br>
				${dto.content }<br>
			</c:forEach>
		</div>
	</div>
	<jsp:include page="template/footer.jsp"></jsp:include>
</body>
</html>












