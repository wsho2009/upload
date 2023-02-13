package fax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class resultServlet
 */
@WebServlet("/result")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 文字化け防止
		String loginId = "test@login.com";
		String loginName = "ログイン太郎";
		String title = "履歴データ";
		String unitId = "40951";
		String unitStatus = "COMPLETE";
		String url = request.getServletPath().substring(1);	//リクエストの中のサーブレット名(先頭/はずす)
		
		// 次の画面(jsp)に値を渡す
		request.setAttribute("title", title);
		request.setAttribute("id", loginId);
		request.setAttribute("name", loginName);
		request.setAttribute("unitId", unitId);
		request.setAttribute("unitStatus", unitStatus);
		request.setAttribute("url", url);
/*
		int dataWidth = 4;
		int col_width[] = new int[dataWidth];;
        try {
	        //Javaオブジェクトに値をセット
			ArrayList<OcrRirekiBean> list = OcrRirekiDAO.getInstance().readData(unitId, dataWidth);
			//4列
			for (int i=0; i<list.size(); i++) {
				OcrRirekiBean rireki = list.get(i);
				for (int j=0; j<dataWidth; j++) {
					if (col_width[j] < rireki.getCOL(j).length()) {
						col_width[j] = rireki.getCOL(j).length();
					}
				}
			}
			for (int i=0; i<dataWidth; i++) {
				System.out.print(col_width[i] + " ");	//各カラムの文字数
			}
			System.out.println("");
			request.setAttribute("list", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
        try {
	        //Javaオブジェクトに値をセット
			OcrRirekiBean header = OcrRirekiDAO.getInstance().readHeader(unitId);
			for (int j=0; j<col_width.length; j++) {
				if (col_width[j] < header.getCOL(j).length()) {
					col_width[j] = header.getCOL(j).length();
				}
			}
			//request.setAttribute("list", rireki);
	        ArrayList<String[]> columns = new ArrayList<String[]>();
	        String width;
	        String[] cols;
	        for (int i=0; i<dataWidth; i++) {
		        width = Integer.valueOf(col_width[i]*14).toString();
			    cols = new String[]{"title:'" + header.getCOL(i) +"'", "name:COL"+i, "width:"+width, "type:'text'"};
			    columns.add(cols);
	        }
			request.setAttribute("columns", columns);
       
        } catch (SQLException e) {
			e.printStackTrace();
		}
*/
		// 次の画面に遷移
		request.getRequestDispatcher("/result.jsp").forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //レスポンス
        String type = request.getParameter("type");
        System.out.println("type: " + type);
        String unitId = request.getParameter("unitId");
        System.out.println("unitId: " + unitId);      
        //Form list 
        if (type.equals("rireki") == true) {
	        //レスポンス
	        response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
        	PrintWriter out = response.getWriter();
        
    		int dataWidth = 4;
    		int col_width[] = new int[dataWidth];;
	        try {
		        //Javaオブジェクトに値をセット
				ArrayList<OcrRirekiBean> datalist = OcrRirekiDAO.getInstance().readData(unitId, dataWidth);
				for (int i=0; i<datalist.size(); i++) {
					OcrRirekiBean rireki = datalist.get(i);
					for (int j=0; j<dataWidth; j++) {
						int len = rireki.getCOL(j).getBytes("Shift_JIS").length;
						if (col_width[j] < len) {
							col_width[j] = len;
						}
					}
				}
				/*for (int i=0; i<dataWidth; i++) {
					System.out.print(i + ":" + col_width[i] + " ");	//各カラムの文字数
				}*/
				System.out.println("");
				OcrRirekiBean header = OcrRirekiDAO.getInstance().readHeader(unitId);
				for (int j=0; j<dataWidth; j++) {
					int len = header.getCOL(j).getBytes("Shift_JIS").length;
					if (col_width[j] < len) {
						col_width[j] = len;
					}
				}
				/*for (int i=0; i<dataWidth; i++) {
					System.out.print(i + ":" + col_width[i] + " ");	//各カラムの文字数
				}
				System.out.println("");*/
		        ArrayList<OcrColmunsBean> columns = new ArrayList<OcrColmunsBean>();
		        String width;
		        OcrColmunsBean cols;
		        for (int i=0; i<7; i++) {
		        	if (i < dataWidth) {
				        width = Integer.valueOf(col_width[i]*14).toString();
					    cols = new OcrColmunsBean(header.getCOL(i), "COL"+i, width, "text");
		        	} else {
					    cols = new OcrColmunsBean("", "COL"+i, "0", "hidden");
		        	}
				    columns.add(cols);
		        }
		        
		        ObjectMapper mapper = new ObjectMapper();
		        // 戻り値用のオブジェクト作成
		        Map<String, Object> resMap = new HashMap<>();
		        resMap.put("datalist", datalist);
				resMap.put("columns", columns);
		        try {
		            //JavaオブジェクトからJSONに変換
		        	String json = mapper.writeValueAsString(resMap);
		        	//String json = mapper.writeValueAsString(rireki);	//単体jsonのケース
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
        }
	}
}
