<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/investment.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/investmentData.js"></script>
<script type="text/javascript">
	$(function() {
		var sum = 0;
		$("#buycnt").keyup(function() {
			console.log("눌림눌림");
			var a = $("#buyprice").val();
			var b = $("#buycnt").val();
			sum = a * b;
			$("#totalprice").val(sum);
		});
		$("#bought").on(
				"click",
				function() { // 컨트롤러로 부터 리스트를 받아서 출력한다
					var dataList = $("#frm22").serialize();
					$.ajax({
						url : "wbInsert",
						type : "post",
						data : dataList,
						dataType : "json",
						success : function(data) { // 전달받은 data를 JSON 문자열 형태로 바꾼다
							alert("성공");
							console.log(data);
						},
						error : function(request, status, errorData) {
							alert("실패" + "error code : " + request.status
									+ "\n" + "message : "
									+ request.responseText + "\n" + "error : "
									+ errorData);
						}
					});
				});
		$("#sold").on(
				"click",
				function() { // 컨트롤러로 부터 리스트를 받아서 출력한다
					var dataList = $("#frm22").serialize();
					$.ajax({
						url : "wsInsert",
						type : "post",
						data : dataList,
						dataType : "json",
						success : function(data) { // 전달받은 data를 JSON 문자열 형태로 바꾼다
							alert("성공");
							console.log(data);
						},
						error : function(request, status, errorData) {
							alert("실패" + "error code : " + request.status
									+ "\n" + "message : "
									+ request.responseText + "\n" + "error : "
									+ errorData);
						}
					});
				});

	});
</script>

<meta charset="UTF-8">
<title>InvestmentPage</title>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../main/header.jsp"></jsp:include>
		<div id="container">
			<br> <br>
			<div id="center">
				<table id="top_t">
					<tr>
						<td id="top_t_1">
							<!-- TradingView Widget BEGIN -->
							<div class="tradingview-widget-container">
								<div id="tradingview_3fe73"></div>
								<div class="tradingview-widget-copyright">
									TradingView 제공 <a
										href="https://kr.tradingview.com/symbols/BTCKRW/?exchange=BITHUMB"
										rel="noopener" target="_blank"><span class="blue-text">BTS
											차트</span></a>
								</div>
								<script type="text/javascript"
									src="https://s3.tradingview.com/tv.js"></script>

							</div> <!-- TradingView Widget END -->
						</td>
						<td id="top_t_2">
							<div>
								<input type="text" id="searchcoin" style="width: 500px;"
									value="">
							</div> <br>
							<div id="cointable_div"></div>


						</td>
					</tr>
				</table>

				<br>
				<div id="alltable">
					<table id="all_t" class='table table-striped'>
						<tr>
							<td>
								<table>
									<tr>
										<td>현재가</td>
										<td>수량</td>
										<td>누적수량</td>
									</tr>
									<tr>
										<td>11111</td>
										<td>11111</td>
										<td>1111</td>
									</tr>
									<tr>
										<td>22222</td>
										<td>22222</td>
										<td>22222</td>
									</tr>
									<tr>
										<td>333333</td>
										<td>333333</td>
										<td>333333</td>
									</tr>
									<tr>
										<td>44444</td>
										<td>44444</td>
										<td>44444</td>
									</tr>
									<tr>
										<td>55555</td>
										<td>55555</td>
										<td>55555</td>

									</tr>
								</table>
							</td>

							<td>
								<form id="frm22">
									<table>
										<tr>
											<td><button value="">매도</button>
												<button value="">매수</button></td>
										</tr>
										<tr>
											<td><input id="cash" placeholder="보유 KRW"> <br>
												<input id="cash" placeholder="매도가능 코인"></td>

										</tr>
										<tr>
											<th colspan="1">코인종류</th>
										</tr>
										<tr>
											<td colspan="1"><input name="coin" id="coin" type="text"
												value="BTC" readonly="readonly" style="text-align: center;"></td>
										</tr>

										<tr>
											<th colspan="1">가격</th>
										</tr>
										<tr>
											<td colspan="1"><input id="price_b" type="text"
												name="buyprice"></td>
											<td colspan="1"><input id="price_s" type="text"
												name="sellprice"></td>

										</tr>
										<tr>
											<th colspan="1">수량</th>

										</tr>
										<tr>
											<td colspan="1"><input id="cnt_b" type="text"
												name="buycnt"></td>
											<td colspan="1"><input id="cnt_s" type="text"
												name="sellcnt"></td>
										</tr>
										<tr>
											<th colspan="1">총액</th>

										</tr>
										<tr>
											<td colspan="1"><input id="totalprice" type="text"
												value="0"></td>
										</tr>
										<tr>
											<td colspan="1"><input id="acntno" name="acntno"
												type="text" value="b12345678"></td>
										</tr>


										<tr>
											<td colspan="1"><button value="10%">10%</button>
												<button value="25%">25%</button>
												<button value="50%">50%</button>
												<button value="100%">100%</button></td>
										</tr>
										<tr>
											<td colspan="1"><button id="bought" type="button">매수</button>
												<button id="sold" type="button">매도</button></td>

										</tr>

									</table>


								</form>

							</td>
							<td>
								<table>

									<tr>
										<th colspan="1">회원계좌번호 : <a>불러와</a><input type="password"
											placeholder="비밀번호"></th>

									</tr>
									<tr>
										<th colspan="1">미체결주문</th>
									</tr>
									<tr>
										<td><a> aaaa</a></td>
									</tr>
									<tr>
										<th colspan="1">체결주문</th>
									</tr>
									<tr>
										<td><a> aaaa</a></td>
									</tr>

								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>


		</div>
		<jsp:include page="../main/footer.jsp"></jsp:include>
	</div>
</body>
</html>