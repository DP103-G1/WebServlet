package box;

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

import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/BoxServlet")
public class BoxServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
    Dao<Box> dao = null;
    
   
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null){
			jsonIn.append(line);
		}
		// 將輸入資料列印出來除錯用
	   // System.out.println("input: " + jsonIn);
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (dao == null) {
			dao = new BoxDao();
		}
		String action = jsonObject.get("action").getAsString();

		int count = 0;
		if (action.equals("getAll")) {
			List<Box> boxs = dao.getAll();
			writeText(response,gson.toJson(boxs));
		}else if (action.equals("boxInsert") || action.equals("boxUpdate")) {
			String boxJson = jsonObject.get("box").getAsString();
			System.out.println("boxJson ="+boxJson);
			Box box = gson.fromJson(boxJson,Box.class);
			if(action.equals("boxInsert")) {
		    	count = dao.add(box);	
		    }else{
				String id = jsonObject.get("id").getAsString();
				count = dao.update(id, box);
			}
			writeText(response, String.valueOf(count));//寫外或if else 各寫一次
		}
	    else if (action.equals("boxDelete")) {
			String id = jsonObject.get("id").getAsString();
			count = dao.deleteById(id);
			writeText(response, String.valueOf(count));
		}else if (action.equals("findById")) {
			String id = jsonObject.get("id").getAsString();
			Box box = dao.getById(id);
			writeText(response,gson.toJson(box));
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (dao == null) {
			dao = new BoxDao();
		}
		List<Box> boxs = dao.getAll();
		writeText(response, new Gson().toJson(boxs));
	}
	
}
