package ezeats;


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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	MenuDao menuDao = null;

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
		if (menuDao == null) {
			menuDao = new MenuDaoMySQL();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			List<Menu> menu = menuDao.getAll();
			writeText(response, gson.toJson(menu));
		}else if (action.equals("getAllShow")) {
			List<Menu> menu = menuDao.getAllShow();
			writeText(response, gson.toJson(menu));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			String menu_id = jsonObject.get("id").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = menuDao.getImage(menu_id);
			if (image != null) {
//				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLengthLong(image.length);
				os.write(image);
			}
		} else if (action.equals("add") || action.equals("update")) {
			String menuJson = jsonObject.get("menu").getAsString();
			System.out.print("menuJson =" + menuJson);
			Menu menu = gson.fromJson(menuJson, Menu.class);
			byte[] image = null;
			if (jsonObject.get("imageBase64") != null) {
				String imageBase64 = jsonObject.get("imageBase64").getAsString();
				if (imageBase64 != null && !imageBase64.isEmpty()) {
					image = Base64.getMimeDecoder().decode(imageBase64);
				}
			}
			int count = 0;
			if (action.equals("add")) {
				count = menuDao.add(menu, image);
			} else if (action.equals("update")) {
				count = menuDao.update(menu, image);
			}
			writeText(response, String.valueOf(count));
		} else if (action.equals("getId")) {
			String id = jsonObject.get("MENU_ID").getAsString();
			Menu menu = menuDao.getId(id);
			writeText(response, gson.toJson(menu));
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
		if (menuDao == null) {
			menuDao = new MenuDaoMySQL();
		}
		List<Menu> menu = menuDao.getAll();
		writeText(response, new Gson().toJson(menu));
	}

}
