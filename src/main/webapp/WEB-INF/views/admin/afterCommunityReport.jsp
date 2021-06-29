<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/admin.css"
	rel="stylesheet" type="text/css" />
<style>
#modal{
	display: none;
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
}

#contents table {
	width: 100%;
}

#contents {
	padding: 30px;
    background-color: white;
    border: 2px solid rgb(140, 102, 200);
    border-radius: 5px;
    top: calc(50% - 200px);
    left: calc(50% - 100px);
    width: 400px;
    height: 428px;
    text-align: center;
    position: absolute;
}

#acr th, td {
	padding: 5px !important;
}

#acr td:not(.center, .right){
	text-align: left;
}

#acr {
	position: absolute;
	top: calc(50% - 150px);
	left: calc(50% - 480px);
}

#page{
	text-align: center;
}

#listForm{
	width: 100%;
}

#detail {
	height: 35px;
}

#close {
	height: 35px;
}

#contents table td:nth-child(1) {
	width: 120px;
	text-align: right;
}

#contents table td input {
	width: 270px;
	border: 1px solid rgba(0, 0, 0, 0.5);
	border-radius: 3px;
}

#ccontentText {
	height: 105px;
	width: 270px;
	border: 1px solid rgba(0, 0, 0, 0.5);
	border-radius: 3px;
	font-size: 13.3333px;
	padding: 1px 2px;
	text-align: left;
	background: white;
}

button {
	width: 100%;
}

.hidden{
	display: none;
}
</style>
<script>
$(function(){
	var  openModal = function(event) {
		$("#modal").css("display", "block");
		var ele = event.currentTarget.querySelectorAll("td");
		console.log(ele);
		for(i=0; i<ele.length; i++){
			console.log(i+"번째 " + ele[i].innerText);
		};
		
		$("#crno").val(ele[0].innerText); // 신고 번호
		$("#csubject").val(ele[1].innerText); // 신고하는 게시글 제목
		$("#ccontentText").html(ele[2].innerText); // 신고하는 게시글 내용
		$("#crespondent").val(ele[3].innerText); // 신고하는 게시글 작성자
		$("#creporter").val(ele[4].innerText); // 신고자
		$("#crreasonText").val(ele[5].innerText); // 신고 사유
		$("#crdate").val(ele[6].innerText); // 신고 날짜
		$("#creason").val(ele[7].innerText); // 처리 사유
		$("#acrdate").val(ele[8].innerText); // 처리 시간
		$("#cstatus").val(ele[9].innerText); // 처리 상태
	}
	$(".tr").on("click", openModal);
	
	var closeModal = function() {
		$("#modal").css("display", "none");
	}
	
	$("#close").on("click", closeModal);

})
</script>
</head>
<%@include file="headerAndAside.jsp"%>
<body>
	<div id="acr">
		<div>
			<table border="1" class="table">
				<tr>
					<th>신고 번호</th>
					<th>게시글 제목</th>
					<th>피신고자</th>
					<th>신고자</th>
					<th>신고 사유</th>
					<th>신고 시간</th>
					<th>처리 사유</th>
					<th>처리 시간</th>
					<th>처리 상태</th>
				</tr>
				<c:if test="${listCount eq 0}">
					<tr>
						<td colspan="9" align="center">조회된 신고 내역이 없습니다.</td>
					</tr>
				</c:if>
				<c:if test="${listCount ne 0}">
					<c:forEach var="vo" items="${list}" varStatus="status">
						<tr class="tr">
							<td style="cursor: pointer;">${vo.crno}</td>
							<td style="cursor: pointer;" class="subject">${vo.csubject}</td>
							<td class="hidden">${vo.ccontent}</td>
							<td style="cursor: pointer;">${vo.crespondent}</td>
							<td style="cursor: pointer;">${vo.creporter}</td>
							<c:choose>
							<c:when test="${vo.crreason eq 1}">
							<td style="cursor: pointer;">나체 이미지 또는 성적 행위</td>
							</c:when>
							<c:when test="${vo.crreason eq 2}">
							<td style="cursor: pointer;">혐오 발언 또는 폭력적</td>
							</c:when>
							<c:when test="${vo.crreason eq 3}">
							<td style="cursor: pointer;">증오 또는 학대</td>
							</c:when>
							<c:when test="${vo.crreason eq 4}">
							<td style="cursor: pointer;">유해하거나 위험한 행위</td>
							</c:when>
							<c:when test="${vo.crreason eq 5}">
							<td style="cursor: pointer;">스팸 또는 사용자 현혹</td>
							</c:when>
							<c:when test="${vo.crreason eq 6}">
							<td style="cursor: pointer;">마음에 들지 않습니다.</td>
							</c:when>
							</c:choose>
							<td style="cursor: pointer;">${vo.crdate}</td>
							<td style="cursor: pointer;">${vo.creason}</td>
							<td style="cursor: pointer;">${vo.acrdate}</td>
							<c:choose>
							<c:when test="${vo.cstatus eq 'accept'}">
							<td style="cursor: pointer;">수리</td>
							</c:when>
							<c:when test="${vo.cstatus eq 'deny'}">
							<td style="cursor: pointer;">반려</td>
							</c:when>
							</c:choose>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<td colspan="9">
						<div id="page">
							<!-- 앞 페이지 번호 처리 -->
							<c:if test="${currentPage <= 1}">
							<i class="fas fa-angle-double-left"></i>
							</c:if>
							<c:if test="${currentPage > 1}">
								<c:url var="crlistST" value="cr">
									<c:param name="page" value="${currentPage-1}"/>
								</c:url>
								<a href="${crlistST}"><i class="fas fa-angle-double-left"></i></a>
							</c:if>
							<!-- 끝 페이지 번호 처리 -->
							<c:set var="endPage" value="${maxPage}" />
							<c:forEach var="p" begin="${startPage+1}" end="${endPage}">
								<c:if test="${p eq currentPage}">
									<div class="pageNum"><b>${p}</b></div>
								</c:if>
								<c:if test="${p ne currentPage}">
									<c:url var="crlistchk" value="cr">
										<c:param name="page" value="${p}" />
									</c:url>
									<a href="${crlistchk}">${p}</a>
								</c:if>
							</c:forEach>
							<c:if test="${currentPage >= maxPage}">
								<i class="fas fa-angle-double-right"></i>
							</c:if>
							<c:if test="${currentPage < maxPage}">
								<c:url var="crlistEND" value="cr">
									<c:param name="page" value="${currentPage+1}" />
								</c:url>
								<a href="${crlistEND}"><i class="fas fa-angle-double-right"></i></a>
							</c:if>
						</div>
					</td>
			</table>
		</div>
	</div>
	<div id="modal">
			<div id="contents">
				<form id="frmReport">
					<table>
						<tr class="hidden">
							<td>신고 접수 번호</td>
							<td><input type="hidden" value="" name="crno" id="crno"></td>
						</tr>
						<tr>
							<td>게시글 제목</td>
							<td><input type="text" value="" name="csubject" id="csubject" readonly></td>
						</tr>
						<tr>
							<td>피신고자</td>
							<td><input type="text" value="" name="crespondent" id="crespondent" readonly></td>
						</tr>
						<tr>
							<td class="content">게시글 내용</td>
							<td><div id="ccontentText">&nbsp;</div></td>
						</tr>
						<tr>
							<td>신고자</td>
							<td><input type="text" value="" name="creporter" id="creporter" readonly></td>
						</tr>
						<tr>
							<td>신고 사유</td>
							<td><input type="text" value="" id="crreasonText" readonly></td>
						</tr>
						<tr>
							<td>신고 시간</td>
							<td><input type="text" value="" id="crdate" name="crdate" readonly></td>
						</tr>
						<tr>
							<td>처리 사유</td>
							<td><input type="text" name="creason" id="creason"></td>
						</tr>
						<tr>
							<td>처리 시간</td>
							<td><input type="text" value="" name="acrdate" id="acrdate" readonly></td>
						</tr>
						<tr>
							<td>처리 상태</td>
							<td><input type="text" value="" name="cstatus" id="cstatus" readonly></td>
						</tr>
						<tr>
							<td colspan="2">
								<button type="button" id="close" class="btnPurple">닫기</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
</body>
</html>