package Table_Web;

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
@WebServlet("/TableServlet")
public class TableServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
       Table_Dao tableDao = null;
	
  

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine())!=null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),JsonObject.class);
		if (tableDao == null) {
			tableDao = new TableDaoMySQLImp();
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAll")) {
			List<Table> tables = tableDao.getAll();
			writeText(response, gson.toJson(tables));
		} else if (action.equals("getAllOrdId")) {
			List<Table> tables = tableDao.getAllOrdId();
			writeText(response, gson.toJson(tables));	
		} else if (action.equals("tableInsert") || action.equals("tableUpdate")) {
			String tableJson = jsonObject.get("table").getAsString();
			System.out.println("tableJson = " + tableJson);
			Table table = gson.fromJson(tableJson, Table.class);
			int count = 0;
			if (action.equals("tableInsert")) {
				count = tableDao.insert(table);
			}else if(action.equals("tableUpdate")){
				count = tableDao.update(table);
			}
			writeText(response, String.valueOf(count));
		}else if(action.equals("tableDelete")){
			int tableId = jsonObject.get("tableId").getAsInt();
			int count = tableDao.deleteId(tableId);
			writeText(response, String.valueOf(count));
		}else if (action.equals("getTableId")) {
			int tableId = jsonObject.get("tableId").getAsInt();
			Table table = tableDao.getTableId(tableId);
			writeText(response, gson.toJson(table));
		} else if (action.equals("updateStatus")) {
			int count = 0;
			String tableJson = jsonObject.get("table").getAsString();
			count = tableDao.updateStatus(gson.fromJson(tableJson, Table.class));
			writeText(response, String.valueOf(count));
		} else if (action.equals("getUsingTableByMemberId")) {
			int memberId = jsonObject.get("memberId").getAsInt();
			Table table = tableDao.getUsingTableByMemberId(memberId);
			writeText(response, gson.toJson(table));
		} else if (action.equals("updateBkId")) {
			int count = 0;
			int tableId = jsonObject.get("tableId").getAsInt();
			count = tableDao.updateBkId(tableId);
			writeText(response, String.valueOf(count));
		}
		else {
			writeText(response, "");
		}
	}
	
	private void writeText(HttpServletResponse response, String json) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(json);
		// 將輸出資料列印出來除錯用
		System.out.println("output: " + json);
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (tableDao == null) {
			tableDao = new TableDaoMySQLImp();
			
		}
		List<Table> tables = tableDao.getAll();
		writeText(response,new Gson().toJson(tables));
		
	}

}