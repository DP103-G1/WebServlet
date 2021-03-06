package employee;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	EmployeeDao employeeDao = null;
	
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println(jsonIn.toString());
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if(employeeDao == null) {
			employeeDao = new EmployeeMySqlImp();
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("updatePassword")) {
			int count = 0;
			int employee_Id = jsonObject.get("employee_Id").getAsInt();
			String password = jsonObject.get("password").getAsString();
			count = employeeDao.updatePassword(employee_Id, password);
			writeText(response, String.valueOf(count));
			}
		else if (action.equals("login")) {
				String account = jsonObject.get("account").getAsString();
				String password = jsonObject.get("password").getAsString();
				writeText(response, employeeDao.login(account, password));
			}else if(action.equals("getAll")){
				List<Employee> employees = employeeDao.getAll();
				writeText(response, gson.toJson(employees));
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
		if (employeeDao == null) {
			employeeDao = new EmployeeMySqlImp();
		}
		List<Employee> employeeDatas = employeeDao.getAll();
		writeText(response, new Gson().toJson(employeeDatas));
		
	}


	
}
