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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class uploadServlet
 */
@WebServlet("/upload")
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
		// 次の画面に遷移
		request.getRequestDispatcher("/upload.jsp").forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        System.out.println("type: " + type);
        String userId = request.getParameter("id");
        System.out.println("userId: " + userId);      
        //Form list 
        if (type.equals("select") == true) {
	        //レスポンス
	        response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
        	PrintWriter out = response.getWriter();
			try {
		        //Javaオブジェクトに値をセット
				ArrayList<poFormBean> select = poFormDAO.getInstance().read(userId);
		        ObjectMapper mapper = new ObjectMapper();
		        try {
		            //JavaオブジェクトからJSONに変換
		            String json = mapper.writeValueAsString(select);
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
	    //History list
        } else if (type.equals("rireki") == true) {
	        //レスポンス
	        response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
        	PrintWriter out = response.getWriter();
			try {
		        //Javaオブジェクトに値をセット
				ArrayList<poUploadBean> rireki = poUploadDAO.getInstance().read(userId);
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
        } else if (type.equals("upload") == true) {
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
			
			
			/*------------------------------------------------------------*/
			String toriCd;
			String ch = formId.substring(5, 1);	//6桁目
			if (ch.equals("") == true) {
				toriCd = formId.substring(0, 5);
			} else {
				toriCd = formId.substring(0, 6);
			}
			System.out.println(part.getContentType());
			if (part.getContentType() == "application/pdf") {
				//pdfファイルは、OCR
				//String dtStr = dt.substring(0,4) + dt.substring(5,7) + dt.substring(8,10) + dt.substring(11,13) + dt.substring(14,16) + dt.substring(17,19);
				String fileName = toriCd + "_" + dtStr + ".pdf";
				registerOcrProcess(formId, formName, fileName);

			} else {
				//直接ファイル渡し
			}
			/*------------------------------------------------------------*/
			
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
    private void registerOcrProcess(String formId, String formName, String fileName) {
		// TODO 自動生成されたメソッド・スタブ
		
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
