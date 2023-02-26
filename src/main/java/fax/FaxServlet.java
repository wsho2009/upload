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

import konyurireki.KonyuBean;
import konyurireki.KonyuDAO;

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
		String userId = "test@login.com";
		String userName = "テストログイン太郎";
		String code = "99";
		String title = "購入履歴リスト";
		
		ArrayList<KonyuBean> list;
		try {
	        list = KonyuDAO.getInstance().read();
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		// 次の画面(jsp)に値を渡す
		request.setAttribute("title", title);
		request.setAttribute("userId", userId);
		request.setAttribute("userName", userName);
		request.setAttribute("code", code);
		request.setAttribute("list", list);
		
		// 次の画面に遷移
		request.getRequestDispatcher("/inquiryist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 文字化け防止
		String userId = "test@login.com";
		String userName = "テストログイン太郎";
		String code = "99";
		String title = "購入履歴リスト";
		
        String type = request.getParameter("type");
        System.out.println("type: " + type);
        userId = request.getParameter("userId");
        System.out.println("userId: " + userId);      
        
        //Form list 
		if (type.equals("list") == true) {
	        String konyusaki = request.getParameter("konyusaki");
	        System.out.println("konyusaki: " + konyusaki);
	        String syubetsu = request.getParameter("syubetsu");
	        System.out.println("syubetsu: " + syubetsu);
	        String date_fr = request.getParameter("date_fr");
	        System.out.println("date_fr: " + date_fr);   
	        String date_to = request.getParameter("date_to");
	        System.out.println("date_to: " + date_to);   
	        
			ArrayList<KonyuBean> list;
			try {
		        list = KonyuDAO.getInstance().read(konyusaki, syubetsu, date_fr, date_to);
			} catch (SQLException e) {
				list = null;
				e.printStackTrace();
			}
			// 次の画面(jsp)に値を渡す
			request.setAttribute("title", title);
			request.setAttribute("userId", userId);
			request.setAttribute("userName", userName);
			request.setAttribute("code", code);
			request.setAttribute("list", list);
			
			// 次の画面に遷移
			request.getRequestDispatcher("/inquiryist.jsp").forward(request, response);
		} else if (type.equals("list2") == true) {
	        //レスポンス
	        response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
	    	PrintWriter out = response.getWriter();
			try {
		        //Javaオブジェクトに値をセット
				String konyuRireki = null;
				ArrayList<KonyuBean> rireki = KonyuDAO.getInstance().read();
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
