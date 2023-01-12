package fax;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class resultServlet
 */
@WebServlet("/resultServlet")
public class resultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public resultServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8"); // 文字化け防止
		String loginId = "543412";
		String loginName = "Form";
		String code = "99";
		String title = "アップロード";
		String unitId = "40952";
		String unitStatus = "COMPLETE";
		
		// 次の画面(jsp)に値を渡す
		request.setAttribute("title", title);
		request.setAttribute("id", loginId);
		request.setAttribute("name", loginName);
		request.setAttribute("code", code);

		request.setAttribute("unitId", unitId);
		request.setAttribute("unitStatus", unitStatus);
	    //String[][] data = new String[][]{
		//	{"2021/12/30", "3000", "2021/11/30", "3000"},
		//	{"2021/12/30", "3000", "2021/11/30", "3000"},
		//	{"2021/12/15", "3000", "2021/11/15", "3000"},
		//	{"2021/12/15", "3000", "2021/11/15", "3000"},
		//};
	    ArrayList<String[]> list = new ArrayList<String[]>();
	    String[] data1 = new String[]{"2021/12/15", "3000", "2021/11/30", "3000"};
	    list.add(data1);
	    String[] data2 = new String[]{"2021/12/15", "3000", "2021/11/30", "3000"};
	    list.add(data2);
	    String[] data3 = new String[]{"2021/12/30", "3000", "2021/11/15", "3000"};
	    list.add(data3);
	    String[] data4 = new String[]{"2021/12/30", "3000", "2021/11/15", "3000"};
	    list.add(data4);
		request.setAttribute("list", list);

	    ArrayList<String[]> columns = new ArrayList<String[]>();
	    String[] cols1 = new String[]{"title:'客先要求納期'", "width:120", "type:'text'"};
	    columns.add(cols1);
	    String[] cols2 = new String[]{"title:'数量'", "width:80", "type:'numeric'"};
	    columns.add(cols2);
	    String[] cols3 = new String[]{"title:'営業希望納期'", "width:120", "type:'text'"};
	    columns.add(cols3);
	    String[] cols4 = new String[]{"title:'数量'", "width:80", "type:'numeric'"};
	    columns.add(cols4);
		request.setAttribute("columns", columns);
		
		// 次の画面に遷移
		request.getRequestDispatcher("/result.jsp").forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
