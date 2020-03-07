package order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import Booking_Web.Booking;
import Booking_Web.BookingDao;
import Booking_Web.BookingDaoMySQLImp;


@SuppressWarnings("serial")
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	OrderDao orderDao = null;

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
		if (orderDao == null) {
			orderDao = new OrderDaoMySQL();
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAll")) {
			List<Order> orders = orderDao.getAll();
			writeText(response, gson.toJson(orders));
		} else if (action.equals("getAllByMemberId")) {
			int memberId = jsonObject.get("memberId").getAsInt();
			List<Order> orders = orderDao.getAllByMemberId(memberId);
			writeText(response, gson.toJson(orders));
		} else if (action.equals("add") || action.equals("update")) {
			String orderJson = jsonObject.get("order").getAsString();
			System.out.println("orderJson =" + orderJson);
			Order order = gson.fromJson(orderJson, Order.class);
			int count = 0;
			if (action.equals("add")) {
				int bkid = orderDao.getBkid(order.getMEMBER_ID());
				BookingDao bookingDao = new BookingDaoMySQLImp();
				Booking booking = bookingDao.getbkId(bkid);
				order.setBK_ID(bkid);
				count = orderDao.add(order);
			} else if (action.equals("update")) {
				count = orderDao.update(order);
			}
			writeText(response, String.valueOf(count));
		} else if (action.equals("getId")) {
			int id = jsonObject.get("ORD_ID").getAsInt();
			Order order = orderDao.getId(id);
			writeText(response, gson.toJson(order));
		}else if (action.equals("search")) {
			long searchDate = jsonObject.get("calendar").getAsLong();
			String type = jsonObject.get("type").getAsString();
			List<Order>orders = orderDao.search(new Date(searchDate), type);
			writeText(response, gson.toJson(orders));
		} else if (action.equals("getAllByOrdId")) {
			int ordId = jsonObject.get("ordId").getAsInt();
			List<Order> orderMenuDetails = orderDao.getAllByOrdId(ordId);
			writeText(response, gson.toJson(orderMenuDetails));
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
		if (orderDao == null) {
			orderDao = new OrderDaoMySQL();
		}
		Calendar calendar = new Calendar.Builder().setDate(2018, 3, 2).build();
		List<Order> orders = orderDao.search(calendar.getTime(), "year");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		writeText(response, gson.toJson(orders));
	}

}
