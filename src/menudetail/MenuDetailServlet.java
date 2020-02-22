package menudetail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/MenuDetailServlet")
public class MenuDetailServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	MenuDetailDao menuDetailDao = null;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}

		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (menuDetailDao == null) {
			menuDetailDao = new MenuDetailDaoMySQL();
		}
		
		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			List<MenuDetail> menuDetail = menuDetailDao.getAll();
			writeText(response, gson.toJson(menuDetail));
		} else if (action.equals("update")) {
			String menudetailJson = jsonObject.get("menuDetail").getAsString();
			System.out.print("menudetailJson =" + menudetailJson);
			MenuDetail menuDetail = gson.fromJson(menudetailJson, MenuDetail.class);
			int count = 0;
			count = menuDetailDao.update(menuDetail);
			writeText(response, gson.toJson(menuDetail));
		} else if (action.equals("getAllByMemberId")) {
			int memberId = jsonObject.get("memberId").getAsInt();
			List<MenuDetail> menuDetails = menuDetailDao.getAllByMemberId(memberId);
			writeText(response, gson.toJson(menuDetails));
		
		} else {
			writeText(response, "");
		}
	}
	
	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (menuDetailDao == null) {
			menuDetailDao = new MenuDetailDaoMySQL();
		}
		List<MenuDetail> menuDetail = menuDetailDao.getAll();
		writeText(response, new Gson().toJson(menuDetail));
	}

}
