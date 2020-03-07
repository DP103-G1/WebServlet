package Booking_Web;

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
@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	BookingDao bookingDao = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (bookingDao == null) {
			bookingDao = new BookingDaoMySQLImp();
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAll")) {
			List<Booking> bookings = bookingDao.getAll();
			writeText(response, gson.toJson(bookings));
		} else if (action.equals("bookingInsert")) {
			String bookingJson = jsonObject.get("booking").getAsString();
			System.out.println("bookingJson =" + bookingJson);
			Booking booking = gson.fromJson(bookingJson, Booking.class);
			int count = 0;
			count = bookingDao.insert(booking);
			writeText(response, String.valueOf(count));
		} else if (action.equals("getAllByMemberId")) {
			int memberId = jsonObject.get("memberId").getAsInt();
			List<Booking> bookings = bookingDao.getAllByMemberId(memberId);
			writeText(response, gson.toJson(bookings));

		} else if (action.equals("getbkId")) {
			int bkId = jsonObject.get("bkId").getAsInt();
			Booking booking = bookingDao.getbkId(bkId);
			writeText(response, gson.toJson(booking));
		} else if (action.equals("update")) {
			int bkid = jsonObject.get("bk_id").getAsInt();
			int member_id = jsonObject.get("member_id").getAsInt();
			int count = bookingDao.update(bkid, member_id);
			writeText(response, gson.toJson(count));
		} else {
			writeText(response, "");
		}

	}

	private void writeText(HttpServletResponse response, String json) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(json);
//		System.out.println("output: " + json);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (bookingDao == null) {
			bookingDao = new BookingDaoMySQLImp();
		}
		List<Booking> bookings = bookingDao.getAll();
		writeText(response, new Gson().toJson(bookings));
	}

}
