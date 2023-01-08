package fax;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class faxServlet
 */
@WebServlet("/faxServlet")
public class faxServlet extends HttpServlet {
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
		
		faxDAO dao = new faxDAO();
        List<faxDTO> list;
		try {
			list = dao.read();
		} catch (SQLException e) {
			list = null;
			// TODO 自動生成された catch ブロック
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
		request.getRequestDispatcher("/faxlist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
