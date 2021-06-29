package com.kh.bts.investment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kh.bts.acnt.model.service.AcntService;
import com.kh.bts.acnt.model.service.CoinAcntService;
import com.kh.bts.acnt.model.vo.Acnt;
import com.kh.bts.acnt.model.vo.CoinAcnt;
import com.kh.bts.investment.model.service.BoughtService;
import com.kh.bts.investment.model.service.SoldService;
import com.kh.bts.investment.model.service.WaitBoughtService;
import com.kh.bts.investment.model.service.WaitSoldService;
import com.kh.bts.investment.model.vo.Bought;
import com.kh.bts.investment.model.vo.Sold;
import com.kh.bts.investment.model.vo.WaitBought;
import com.kh.bts.investment.model.vo.WaitSold;
import com.kh.bts.member.model.service.MemberService;
import com.kh.bts.member.model.vo.Member;

@Controller
public class investmentCtrl {

	@Autowired
	private WaitBoughtService wbService;

	@Autowired
	private WaitSoldService wsService;
	@Autowired
	private BoughtService bService;
	@Autowired
	private SoldService sService;
	@Autowired
	private AcntService aService;
	@Autowired
	private CoinAcntService caService;

	// 미체결 매수 내역 불러오기
	@RequestMapping("buy")
	public ModelAndView buy(ModelAndView mav) {
		List<WaitBought> waitblist = wbService.selectAllCoinListWaitBought();
		mav.addObject("waitblist", waitblist);
		List<WaitBought> waitresult = wbService.selectAllListWaitBought();
		mav.addObject("waitresult", waitresult);
		System.out.println(waitblist);
		System.out.println(waitresult);

		mav.setViewName("investment/buy");
		return mav;
	}
	
	// 미체결 매도 내역 불러오기
	@RequestMapping("sell")
	public ModelAndView sell(ModelAndView mav) {
		List<WaitSold> waitslist = wsService.selectAllCoinListWaitSold();
		mav.addObject("waitslist", waitslist);
		List<WaitSold> waitresult = wsService.selectAllListWaitSold();
		mav.addObject("waitresult", waitresult);
		System.out.println(waitslist);
		System.out.println(waitresult);

		mav.setViewName("investment/sell");
		return mav;
	}

	// 미체결 매수 내역을 체결 내역으로 바꾸기
	@RequestMapping("bought")
	public void bought(@RequestParam("buyCoin") String coin, @RequestParam("buyPrice") double buyprice,
			HttpServletResponse response) {
		WaitBought vo = new WaitBought();
		vo.setBuyprice(buyprice);
		vo.setCoin(coin);
		int result = bService.insertBought(vo);
		PrintWriter out = null;
		try {
			if (result > 0) {
				System.out.println("전부 처리 완료");
				out = response.getWriter();
				out.print(result);
			} else {
				System.out.println("전부 처리 실패");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}
	
	// 미체결 매도 내역을 체결 내역으로 바꾸기
	@RequestMapping("sold")
	public void sold(@RequestParam("sellCoin") String coin, @RequestParam("sellPrice") double sellprice,
			HttpServletResponse response) {
		System.out.println("코인 팔러 컨트롤러 들어옴#@#############################");
		System.out.println(coin);
		System.out.println(sellprice);
		WaitSold vo = new WaitSold();
		vo.setSellprice(sellprice);
		vo.setCoin(coin);
		int result = sService.insertSold(vo);
		PrintWriter out = null;
		try {
			if (result > 0) {
				System.out.println("전부 처리 완료");
				out = response.getWriter();
				out.print(result);
			} else {
				System.out.println("전부 처리 실패");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		
	}

	@RequestMapping("Chatting")
	public ModelAndView Chatting(ModelAndView mav) {
		mav.setViewName("sub/chatting");
		return mav;
	}

	@RequestMapping("kakao")
	public ModelAndView Kakao(ModelAndView mav) {
		mav.setViewName("sub/kakao");
		return mav;
	}

	@RequestMapping("investmentpage")
	public ModelAndView MainPage(Member vo, ModelAndView mav, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String loginEmail = (String) session.getAttribute("loginMember");
		if (loginEmail == null) {
			System.out.println("비회원입니다");
		} else {
			List<WaitBought> waitblist = wbService.selectAllCoinListWaitBought();
			mav.addObject("waitblist", waitblist);
			List<WaitBought> waitresult = wbService.selectAllListWaitBought();
			mav.addObject("waitresult", waitresult);

			System.out.println(waitblist);
			System.out.println(waitresult);

			System.out.println(loginEmail);
			mav.addObject("email", loginEmail);
			List<Acnt> result = aService.selectListAcnt(loginEmail);
			Acnt acnt = result.get(0);
			mav.addObject("acnt", acnt);
			System.out.println("@@" + acnt.getAcntno());

			/*
			 * String acntno = acnt.getAcntno(); List<CoinAcnt> result1 =
			 * caService.allselectList(acntno); System.out.println(result1);
			 * mav.addObject("coinacnt",result1);
			 */

		}
		mav.setViewName("investment/investmentPage");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "bankpw", method = RequestMethod.POST)
	public int Check(Acnt vo) throws Exception {

		int result = aService.cntAcnt(vo);
		if (result > 0) {
			System.out.println("bankpw성공");
		} else {
			System.out.println("bankpw실패");
		}
		System.out.println(result);

		return result;
	}

	@RequestMapping("1sChart")
	public ModelAndView MainPaged(ModelAndView mav) {
		mav.setViewName("investment/1sChart");
		return mav;
	}

	/*
	 * @RequestMapping(value = "sInsert") public void SoldInsert(Sold ws,
	 * HttpServletResponse response) { int result = sService.insertSold(ws);
	 * PrintWriter out = null; try { if (result > 0) {
	 * System.out.println("insert성공"); } else { System.out.println("insert실패"); }
	 * out = response.getWriter(); out.print(result); } catch (IOException e) {
	 * e.printStackTrace(); } finally { out.flush(); out.close(); }
	 * 
	 * }
	 */

	@RequestMapping(value = "coinacntInsert")
	public void CoinAcntInsert(CoinAcnt vo, HttpServletResponse response) {

		int result = caService.insertCoinAcnt(vo);
		PrintWriter out = null;
		try {
			if (result > 0) {
				System.out.println("insert성공");
			} else {
				System.out.println("insert실패");
			}
			out = response.getWriter();
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}

	@RequestMapping(value = "coinacntdelete", method = RequestMethod.POST)
	public void CoinAcntdelete(@RequestParam(name = "cano") int cano, HttpServletResponse response) {

		System.out.println(cano);
		int result = caService.deleteCoinAcnt(cano);
		PrintWriter out = null;
		try {
			if (result > 0) {
				System.out.println("wbdelete 성공");
			} else {
				System.out.println("wbdelete 실패");
			}
			out = response.getWriter();
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}

	// ajax
	@RequestMapping(value = "coinacntlists", method = RequestMethod.POST)
	public void coinacntService(@RequestParam(name = "acntno") String acntno, HttpServletResponse response) {
		List<CoinAcnt> result = caService.allselectList(acntno);
		PrintWriter out = null;
		Gson gson = new GsonBuilder().create();
		String jsonlist = gson.toJson(result);

		try {

			System.out.println("ajax select성공");

			out = response.getWriter();
			out.print(jsonlist);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	// ajax
	@RequestMapping(value = "acntlists", method = RequestMethod.POST)
	public void acntService(@RequestParam(name = "acntno") String acntno, HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		String loginEmail = (String) session.getAttribute("loginMember");
		List<Acnt> result = aService.selectListAcnt(loginEmail);
		PrintWriter out = null;
		Gson gson = new GsonBuilder().create();
		String jsonlist = gson.toJson(result);

		try {

			System.out.println("ajax select성공");

			out = response.getWriter();
			out.print(jsonlist);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	// ajax
	@RequestMapping(value = "ajslists", method = RequestMethod.POST)
	public void SoldListService(@RequestParam(name = "acntno") String acntno, HttpServletResponse response) {
		System.out.println(acntno);
		List<Sold> result = sService.selectListSold(acntno);
		PrintWriter out = null;
		Gson gson = new GsonBuilder().create();
		String jsonlist = gson.toJson(result);

		try {

			System.out.println("ajax select성공");

			out = response.getWriter();
			out.print(jsonlist);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	@RequestMapping(value = "sdelete", method = RequestMethod.POST)
	public void Solddelete(@RequestParam(name = "usno") int usno, HttpServletResponse response) {
		System.out.println("SDelete@@@@@@");

		System.out.println(usno);
		int result = sService.deleteSold(usno);
		PrintWriter out = null;
		try {
			if (result > 0) {
				System.out.println("wbdelete 성공");
			} else {
				System.out.println("wbdelete 실패");
			}
			out = response.getWriter();
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}

//////////////////////////////////////waitsold 부분 //////////////////////////
	@RequestMapping(value = "wsInsert")
	public void WaitSoldInsert(WaitSold ws, HttpServletResponse response) {
		System.out.println(ws.getCoin() + "코인이름");
		System.out.println(ws.getSellcnt() + "코인수 ");
		System.out.println(ws.getAcntno() + "코인수 ");
		System.out.println(ws.getSellprice() + "코인수 ");
		int result = wsService.insertWaitSold(ws);
		PrintWriter out = null;
		try {
			if (result > 0) {
				System.out.println("insert성공");
			} else {
				System.out.println("insert실패");
			}
			out = response.getWriter();
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}

	// ajax
	@RequestMapping(value = "ajwslists", method = RequestMethod.POST)
	public void WaitSoldListService(@RequestParam(name = "acntno") String acntno, HttpServletResponse response) {
		System.out.println(acntno);
		List<WaitSold> result = wsService.selectListWaitSold(acntno);
		PrintWriter out = null;
		Gson gson = new GsonBuilder().create();
		String jsonlist = gson.toJson(result);

		try {

			System.out.println("ajax select성공");

			out = response.getWriter();
			out.print(jsonlist);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	@RequestMapping(value = "wsdelete", method = RequestMethod.POST)
	public void WaitSolddelete(@RequestParam(name = "usno") int usno, HttpServletResponse response) {
		System.out.println("WSDelete@@@@@@");

		System.out.println(usno);
		int result = wsService.deleteWaitSold(usno);
		PrintWriter out = null;
		try {
			if (result > 0) {
				System.out.println("wbdelete 성공");
			} else {
				System.out.println("wbdelete 실패");
			}
			out = response.getWriter();
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}

//////////////////////////////////////wait bought 부분 //////////////////////////
	@RequestMapping(value = "wbInsert")
	public void WaitBoughtInsert(WaitBought wb, HttpServletResponse response) {
		System.out.println(wb.getCoin() + "코인이름");
		int result = wbService.insertWaitBought(wb);
		PrintWriter out = null;
		try {
			if (result > 0) {
				System.out.println("insert성공");
				// 계좌 비밀번호 입력하면 => 계좌 비밀번호가 맞는가! 체크하고. 맞으면매수 매도 버튼 열림과 동시에, 현금액이랑 코인 계좌 정보 알아오고,
				// + 미체결 주문 내용까지
				// 매수 매도 버튼 눌렀을 때 미체결 테이블에 ㅑㅜㄴㄷ 하면서 동시에 ㅑㅜㄴㄷㄳ 포함한 전체 미체결 내역을 갖고 돌아ㅇ와
			} else {
				System.out.println("insert실패");
			}
			out = response.getWriter();
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}

// ajax 
	@RequestMapping(value = "ajwblists", method = RequestMethod.POST)
	public void WaitBoughtListService(@RequestParam(name = "acntno") String acntno, HttpServletResponse response) {
		System.out.println(acntno);
		List<WaitBought> result = wbService.selectListWaitBought(acntno);
		String[] coinArr = new String[result.size()];
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < i; j++) {
				coinArr[i] = result.get(i).getCoin();
				if (coinArr[i] == coinArr[j]) {
					i--;
				}
			}
		}
		System.out.println(coinArr.length);
		System.out.println(coinArr[0]);
		System.out.println(coinArr[1]);
		System.out.println(coinArr[2]);
		PrintWriter out = null;
		Gson gson = new GsonBuilder().create();
		String jsonlist = gson.toJson(result);

		try {

			System.out.println("ajax select성공");

			out = response.getWriter();
			out.print(jsonlist);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	@RequestMapping(value = "wbdelete", method = RequestMethod.POST)
	public void WaitBoughtdelete(@RequestParam(name = "ubno") int ubno, HttpServletResponse response) {
		System.out.println("WBDelete@@@@@@");
		// @RequestParam(name="wbno") int wbno,
		System.out.println(ubno);
		int result = wbService.deleteWaitBought(ubno);
		PrintWriter out = null;
		try {
			if (result > 0) {
				System.out.println("wbdelete 성공");
			} else {
				System.out.println("wbdelete 실패");
			}
			out = response.getWriter();
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}

////////////////////////////////////// bought 부분 //////////////////////////
	/*
	 * @RequestMapping(value = "bInsert") public void BoughtInsert(Bought b,
	 * HttpServletResponse response) { System.out.println(b.getCoin() + "코인이름"); int
	 * result = bService.insertBought(b); PrintWriter out = null; try { if (result >
	 * 0) { System.out.println("binsert성공"); // 계좌 비밀번호 입력하면 => 계좌 비밀번호가 맞는가! 체크하고.
	 * 맞으면매수 매도 버튼 열림과 동시에, 현금액이랑 코인 계좌 정보 알아오고, // + 미체결 주문 내용까지 // 매수 매도 버튼 눌렀을 때
	 * 미체결 테이블에 ㅑㅜㄴㄷ 하면서 동시에 ㅑㅜㄴㄷㄳ 포함한 전체 미체결 내역을 갖고 돌아ㅇ와 } else {
	 * System.out.println("binsert실패"); } out = response.getWriter();
	 * out.print(result); } catch (IOException e) { e.printStackTrace(); } finally {
	 * out.flush(); out.close(); }
	 * 
	 * }
	 */
	// ajax
	@RequestMapping(value = "ajblists", method = RequestMethod.POST)
	public void BoughtListService(@RequestParam(name = "acntno") String acntno, HttpServletResponse response) {
		System.out.println(acntno);
		List<Bought> result = bService.selectListBought(acntno);
		PrintWriter out = null;
		Gson gson = new GsonBuilder().create();
		String jsonlist = gson.toJson(result);

		try {

			System.out.println("ajax select성공");

			out = response.getWriter();
			out.print(jsonlist);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	@RequestMapping(value = "bdelete", method = RequestMethod.POST)
	public void Boughtdelete(@RequestParam(name = "ubno") int ubno, HttpServletResponse response) {
		System.out.println("BDelete@@@@@@");
		System.out.println(ubno);
		int result = bService.deleteBought(ubno);
		PrintWriter out = null;
		try {
			if (result > 0) {
				System.out.println("bdelete 성공");
			} else {
				System.out.println("bdelete 실패");
			}
			out = response.getWriter();
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}

/////////////////////////////////////////////////////////////////////////  연습코드 ///////////////////////////////////////////////////		
//// ajax 아닌버젼
//	@RequestMapping(value = "wblists", method = RequestMethod.GET)
//	public ModelAndView WaitBoughtListService2(@RequestParam(name="acntno") String acntno,ModelAndView mv) {
//		// DB에서 ID 가지고 acnt 읽어오기
//		System.out.println("아무거나11111");
//		System.out.println(acntno);
//		List<WaitBought> result = wbService.selectListWaitBought("b12345678");
//		for (int i = 0; i < result.size(); i++) {
//			System.out.println(result.get(i).getCoin());
//		}
//		System.out.println(result.size() + "크기 입니다.");
//
//		mv.addObject("wblists", result);
//		mv.setViewName("investment/investmentPage");
//		System.out.println("아무거나2222222");
//		return mv;
//	}
}