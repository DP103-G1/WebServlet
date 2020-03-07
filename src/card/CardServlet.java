package card;

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
@WebServlet("/CardServlet")
public class CardServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
    CardDao cardDao = null; 
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    	if (cardDao == null) {
			cardDao = new CardDaoMySQL();
		}
    	
    	String action = jsonObject.get("action").getAsString();
    	
    	if (action.equals("add")) {
			String cardJson = jsonObject.get("card").getAsString();
			System.out.println("cardJson =" + cardJson);
			Card card = gson.fromJson(cardJson, Card.class);
			int count = cardDao.add(card);
			writeText(response, String.valueOf(count));
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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (cardDao == null) {
			cardDao = new CardDaoMySQL();
		}
		List<Card> cards = cardDao.getAll();
		writeText(response, new Gson().toJson(cards));
	}

	
	

}
