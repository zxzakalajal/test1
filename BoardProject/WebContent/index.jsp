<%@page import="vo.PaggingVO"%>
<%@page import="dao.BoardDAO"%>
<%@page import="service.BoardService"%>
<%@page import="dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인페이지</title>
<style type="text/css">
#container {
	/* height: 600px; */
	/* background-color: lime; */
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	$(function(){
		$(".board tr").click(function(){
			if($(this).index() == 0) return;
			var bno = $(this).find("td").first().html();
			location.href = "<%=request.getContextPath()%>/board/board_view.jsp?bno="+bno;
			console.log($(this).find("td").first().html());
		});
	});
</script>
<style>
	.board{
		margin:20px auto;
		border-collapse: collapse;
	}
	.board th, .board td{
		padding: 10px;
		text-align: center;
	}
	.board th{
		border-top : 2px solid black;
		border-bottom : 2px solid black;
	}
	.board td{
		border-top : 1px solid black;
		border-bottom : 1px solid black;
	}
	.board tr:last-child > td {
		border-bottom : 2px solid black;
	}
	.title{
		width:500px;	
	}
	.writer{
		width:150px;	
	}
	.date{
		width:200px;
	}
	.page_bar{
		position:relative;
		text-align: center;
	}
	.page_bar a:link,.page_bar a:visited {
		color:black;
		text-decoration: none;
		font-size : 18px;
		margin-left: 10px;
		margin-right: 10px;
	}
	.page_bar a:hover{
		font-weight: bold;
		color:red;
	}
	.btn_writer{
		position:absolute;
		right:0px;
	}
</style>
</head>
<body>
	<!-- header.jsp를 현재 문서에 포함 -->
	<jsp:include page="/template/header.jsp" flush="false"></jsp:include>
	<div id="container">
		<!-- 게시판 글목록 --> 
		<%
			String mode = request.getParameter("mode");
			if(mode == null)
				mode = "bno";
			int pageNo = 1;//맨 처음에 접속을 했을때는 파라미터로 페이지 정보가 없음 그래서 첫번째 페이지로 고정
			if(request.getParameter("pageNo") != null)//페이지 번호를 눌러서 접근한 경우
				pageNo = Integer.parseInt(request.getParameter("pageNo"));
			ArrayList<BoardDTO> list = BoardService.getInstance().selectBoardList(pageNo,mode);
		%>
		<table class="board">
			<tr>
				<th>글번호</th>
				<th class="title">제목</th>
				<th class="writer">작성자</th>
				<th class="date">작성일</th>
				<th>조회수</th>
				<th><a href="index.jsp?mode=blike">좋아요</a></th><!-- 좋아요 클릭하면 좋아요를 많이 받은 순서대로 출력 -->
				<th><a href="index.jsp?mode=bhate">싫어요</a></th>
			</tr>
			<%
				for(int i=0;i<list.size();i++){
					%>
					<tr>
						<td><%=list.get(i).getBno() %></td>
						<td><%=list.get(i).getTitle() %>
						 <%=list.get(i).getcCount() == 0 ? "" : "["+list.get(i).getcCount()+"]"  %></td>
						<td><%=list.get(i).getWriter() %></td>
						<td><%=list.get(i).getbDate() %></td>
						<td><%=list.get(i).getbCount() %></td>
						<td><%=list.get(i).getbLike() %></td>
						<td><%=list.get(i).getbHate() %></td>
					</tr>					
					<%
				}
			//페이징 정보를 읽어옴
			int count = BoardDAO.getInstance().getCount();
			PaggingVO pageVO = new PaggingVO(count, pageNo);
			%>
			<tr>
				<td colspan="7">
					<div class="page_bar">
					<!-- 페이징 처리 시작 -->
					<%
						if(pageVO.isPreviousPageGroup()){
					%>
						<!-- 현재 페이지 그룹의 첫번째 페이지 - 1 == 이전페이지그룹의 마지막 페이지 -->
						<a href="index.jsp?pageNo=<%=pageVO.getStartPageOfPageGroup()-1%>&mode=<%=mode%>">◀</a>
					<%
						}
						//loop start 시작페이지번호, 마지막 페이지 번호를 이용 반복문
						int start = pageVO.getStartPageOfPageGroup();
						int end = pageVO.getEndPageOfPageGroup();
						for(int i=start;i<=end;i++){
							if(i==pageNo){
					%>
						<!-- 출력하는 페이지 번호가 현재 페이지 번호랑 동일하면 
							하이퍼링크를 제거, 글꼴 스타일을 바꿈 -->
							<a style="font-weight: bold;color:red;"><%=i%></a>
						<%}else{ %>	
						<a href="index.jsp?pageNo=<%=i%>&mode=<%=mode%>"><%=i%></a>
						<%
							}
						}
						if(pageVO.isNextPageGroup()){
						%>
						<a href="index.jsp?pageNo=<%=pageVO.getEndPageOfPageGroup()+1%>&mode=<%=mode%>">▶</a>
						<%
						}
						%>
					<!-- 페이징 처리 종료 -->
						<a href="<%=request.getContextPath()%>/board/board_write_view.jsp" class="btn_writer">글쓰기</a>
					</div>
				</td>
			</tr>
		</table>

	</div>
	<jsp:include page="/template/footer.jsp" flush="false"></jsp:include>
	<script>
		console.log("Context : <%= request.getContextPath() %>");
		console.log("URI : <%= request.getRequestURI() %>");
		console.log("URL : <%= request.getRequestURL() %>");
		console.log("Path : <%= request.getServletPath() %>");
		console.log("QueryString : <%= request.getQueryString() %>");
		console.log("Path : " + location.href);
		
	</script>




</body>
</html>







