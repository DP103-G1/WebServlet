package member;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import server_main.ImageUtil;

@SuppressWarnings("serial")
@WebServlet("/MembersServlet")
public class MemberServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	MemberDao memberDataDao = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		// 將輸入資料列印出來除錯用
		// System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (memberDataDao == null) {
			memberDataDao = new MemberDaoMySqlImpl();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			List<Member> memberDatas = memberDataDao.getAll();
			writeText(response, gson.toJson(memberDatas));
		} else if (action.equals("memberInsert") || action.equals("memberUpdate")) {
			String memberJson = jsonObject.get("member").getAsString();
			System.out.println("memberJson = " + memberJson);
			Member memberData = gson.fromJson(memberJson, Member.class);
			int count = 0;
			if (action.equals("memberInsert")) {
				count = memberDataDao.insert(memberData);
			} else if (action.equals("memberUpdate")) {
				count = memberDataDao.update(memberData);
			}
			writeText(response, String.valueOf(count));
		} else if (action.equals("findByMemberId")) {
			int member_Id = jsonObject.get("member_Id").getAsInt();
			Member memberData = memberDataDao.findByMemberId(member_Id);
			writeText(response, gson.toJson(memberData));
		} else if (action.equals("login")) {
			String account = jsonObject.get("account").getAsString();
			String password = jsonObject.get("password").getAsString();
			writeText(response, memberDataDao.login(account, password));
		} else if(action.equals("forget")){
			String account = jsonObject.get("account").getAsString();
			String phone = jsonObject.get("phone").getAsString();
			boolean conut = memberDataDao.forget(account, phone);
			writeText(response, String.valueOf(conut));
		}else if(action.equals("updatePassword")){
			String accountJson = jsonObject.get("account").getAsString();
			Member account = gson.fromJson(accountJson, Member.class);
			int count = memberDataDao.updatePassword(account);
			writeText(response, String.valueOf(count));
		}else {
			writeText(response, "");
		}
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		// 將輸出資料列印出來除錯用
		System.out.println("output: " + outText);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (memberDataDao == null) {
			memberDataDao = new MemberDaoMySqlImpl();
		}
		List<Member> memberDatas = memberDataDao.getAll();
		writeText(response, new Gson().toJson(memberDatas));
	}

}
