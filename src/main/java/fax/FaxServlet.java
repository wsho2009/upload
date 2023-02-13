package fax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import konyurireki.konyuBean;
import konyurireki.konyuBean2;
import konyurireki.konyuDAO;
import konyurireki.konyuDAO2;

/**
 * Servlet implementation class faxServlet
 */
@WebServlet("/fax")
public class FaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 文字化け防止
		String loginId = "test@login.com";
		String loginName = "テストログイン太郎";
		String code = "99";
		String title = "FAXリスト";
		String unitId = "40952";
		String unitStatus = "COMPLETE";
		
        ArrayList<konyuBean> list;
		try {
			String Konnyusaki = null;
	        list = konyuDAO.getInstance().read(Konnyusaki);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		
		// 次の画面(jsp)に値を渡す
		request.setAttribute("title", title);
		request.setAttribute("id", loginId);
		request.setAttribute("name", loginName);
		request.setAttribute("code", code);
		request.setAttribute("list", list);

		request.setAttribute("unitId", unitId);
		request.setAttribute("unitStatus", unitStatus);
		
		// 次の画面に遷移
		request.getRequestDispatcher("/inquiryist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        System.out.println("type: " + type);
        String userId = request.getParameter("id");
        System.out.println("userId: " + userId);      
        
        String form = request.getParameter("form");
        System.out.println("type: " + form);
        String date = request.getParameter("date");
        System.out.println("userId: " + date);          
        //Form list 
		if (type.equals("list") == true) {
	        //レスポンス
	        response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
	    	PrintWriter out = response.getWriter();
			try {
		        //Javaオブジェクトに値をセット
				String konyuRireki = null;
				ArrayList<konyuBean2> rireki = konyuDAO2.getInstance().read(konyuRireki);
		        ObjectMapper mapper = new ObjectMapper();
		        try {
		            //JavaオブジェクトからJSONに変換
		            String json = mapper.writeValueAsString(rireki);
		            //JSONの出力
		            System.out.println(json);
		            out.write(json);
		        } catch (JsonProcessingException e) {
		            e.printStackTrace();
		        }
				out.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    } else {
	        doGet(request, response);
		}
	}
}
