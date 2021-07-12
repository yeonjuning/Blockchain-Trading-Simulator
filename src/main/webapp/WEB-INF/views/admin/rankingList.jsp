<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/adminml.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/admin.js"></script>
	<script>
	    function dpTime(){
	       	var now = new Date();
	       	var year = now.getFullYear();
	       	var month = (now.getMonth()+1);
	       	var day = now.getDay();
	        var hours = now.getHours();
	        var minutes = now.getMinutes();
	        var seconds = now.getSeconds();
	        if(month < 10){
	    		month = "0"+month;
	    	}
	    	if(day < 10){
	    		day = "0"+day;
	    	}
	        if (hours > 12){
	            hours -= 12;
	        	ampm = "오후 ";
	        }else{
	            ampm = "오전 ";
	        }
	        if (hours < 10){
	            hours = "0" + hours;
	        }
	        if (minutes < 10){
	            minutes = "0" + minutes;
	        }
	        if (seconds < 10){
	            seconds = "0" + seconds;
	        }
		document.getElementById("listForm").innerHTML = year+"년 " + month + "월 "+ day +"일 "+ ampm + hours + ":" + minutes + ":" + seconds +" 기준";
	    }
	 	setInterval("dpTime()",1000);
	</script>
</head>
<%@include file="headerAndAside.jsp"%>
<body>
<div id="wrapper">
	<div id="mmm">
<form action="weeklyrewarding">
	<p class="title inbl">월간 랭킹</p><button type="submit" class="btnPurple inbl">보상 메일 발송</button>

	<p id="listForm" class="inbl">&nbsp;<p>
	<hr>
<table>
	<tr>
	<td>이메일</td>
	<td>닉네임</td>
	<td>수익률</td>
	</tr>
	<c:forEach items="${monthly }" var="monthly">
	<tr>
	<td class="email"><input type="hidden" name="email" value="${monthly.email }">${monthly.email }</td>
	<td>${monthly.nickname }</td>
	<td>${monthly.yield}</td>
	
	</tr>
	</c:forEach>
</table>
<div style="height: 100px">
</div>
</form>
<form action="monthlyrewarding">
	<p class="title inbl">주간 랭킹</p><button type="submit" class="btnPurple inbl">보상 메일 발송</button>
	<hr>
	<table>
		<tr>
		<td>이메일</td>
		<td>닉네임</td>
		<td>수익률</td>
		</tr>
		<c:forEach items="${weekly }" var="weekly">
		<tr>
		<td class="email"><input type="hidden" name="email" value="${weekly.email }">${weekly.email }</td>
		<td>${weekly.nickname }</td>
		<td>${weekly.yield }</td>
		</tr>
		</c:forEach>
	</table>
</form>
</div>
</div>
</body>
</html>