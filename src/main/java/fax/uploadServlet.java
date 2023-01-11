package fax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class uploadServlet
 */
@WebServlet("/uploadServlet")
@MultipartConfig(fileSizeThreshold = 5000000, maxFileSize = 700 * 1024 * 1024, location = "d:/pleiades")
public class uploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 文字化け防止
		String loginId = "test@login.com";
		String loginName = "テストログイン太郎";
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

        ArrayList<poFormBean> select;
		try {
			select = poFormDAO.getInstance().read(loginId);
	    	// リクエストスコープにArrayListを設定
	    	request.setAttribute("select", select);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 次の画面に遷移
		request.getRequestDispatcher("/upload.jsp").forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        System.out.println(type);
        //Form list 
        if (type.startsWith("select")) {
	        String id = request.getParameter("id");
	        System.out.println(id);
	        
/*
	        ArrayList<poFormBean> list;
			try {
				list = poFormDAO.getInstance().read(id);
		    	// リクエストスコープにArrayListを設定
		    	request.setAttribute("data", list);

		        //レスポンス
		        PrintWriter out = response.getWriter();
		        out.print("success");
			} catch (SQLException e) {
				e.printStackTrace();
			}
*/
	        String[][] data = new String[][] {
	        	{"1","A0001","AAAAA","test@login.com"},
	        	{"2","B0002","BBBBB","test@login.com"}
	        };
	        //レスポンス
	        PrintWriter out = response.getWriter();
	        out.print(data);
	    //History list
        } else if (type.startsWith("rireki")) {
	        String id = request.getParameter("id");
	        System.out.println(id);

	        //レスポンス
	        PrintWriter out = response.getWriter();
	        out.print("");
        } else if (type.startsWith("upload")) {
	        String id = request.getParameter("id");
	        System.out.println(id);
	        String user = request.getParameter("user");
	        System.out.println(user);
	        String code = request.getParameter("code");
	        System.out.println(code);
	        String dt = request.getParameter("dt");
	        System.out.println(dt);
	        String file = request.getParameter("file");
	        System.out.println(file);
	        String formId = request.getParameter("formId");
	        System.out.println(formId);
	        String formName = request.getParameter("formName");
	        System.out.println(formName);
	        
	        //https://kuwalab.hatenablog.jp/entry/20120424/1335266757
			request.setCharacterEncoding("utf-8");
			Part part = request.getPart("file");
			String name = getFilename(part);
			String dtStr = dt.substring(0,4) + dt.substring(5,7) + dt.substring(8,10) + dt.substring(11,13) + dt.substring(14,16) + dt.substring(17,19);
			name = formId + "_" + dtStr + ".pdf";
			part.write(name);	//ファイル保存
			
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
			
	        //レスポンス
			PrintWriter pw = response.getWriter();
			pw.println("{\"result\":\"ok\"}");
			pw.flush();
			pw.close();
        }

		//doGet(request, response);
    }
    private String getFilename(Part part) {
        for (String cd : part.getHeader("Content-Disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }

        return null;
    }

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
