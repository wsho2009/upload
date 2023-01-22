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
		String loginId = "test@login.com";
		String loginName = "ログイン太郎";
		String title = "履歴データ";
		String unitId = "40952";
		String unitStatus = "COMPLETE";
		String url = request.getServletPath().substring(1);	//リクエストの中のサーブレット名(先頭/はずす)
		
		// 次の画面(jsp)に値を渡す
		request.setAttribute("title", title);
		request.setAttribute("id", loginId);
		request.setAttribute("name", loginName);
		request.setAttribute("unitId", unitId);
		request.setAttribute("unitStatus", unitStatus);
		request.setAttribute("url", url);

    	int col3_max = 0;
    	int col4_max = 0;
    	int col5_max = 0;
    	int col6_max = 0;
        try {
	        //Javaオブジェクトに値をセット
			ArrayList<PoRirekiBean> list = PoRirekiDAO.getInstance().readData(unitId);
			for (int i=0; i<list.size(); i++) {
				PoRirekiBean rireki = list.get(i);
				if (col3_max < rireki.getCOL3().length()) {
					col3_max = rireki.getCOL3().length();
				}
				if (col4_max < rireki.getCOL4().length()) {
					col4_max = rireki.getCOL4().length();
				}
				if (col5_max < rireki.getCOL5().length()) {
					col5_max = rireki.getCOL5().length();
				}
				if (col6_max < rireki.getCOL6().length()) {
					col6_max = rireki.getCOL6().length();
				}
			}
			System.out.println(col3_max + " " + col4_max + " " + col5_max + " " + col6_max);
			
			request.setAttribute("list", list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

        try {
	        //Javaオブジェクトに値をセット
			PoRirekiBean header = PoRirekiDAO.getInstance().readHeader(unitId);
			if (col3_max < header.getCOL3().length()) {
				col3_max = header.getCOL3().length();
			}
			if (col4_max < header.getCOL4().length()) {
				col4_max = header.getCOL4().length();
			}
			if (col5_max < header.getCOL5().length()) {
				col5_max = header.getCOL5().length();
			}
			if (col6_max < header.getCOL6().length()) {
				col6_max = header.getCOL6().length();
			}
			//request.setAttribute("list", rireki);
	        ArrayList<String[]> columns = new ArrayList<String[]>();
	        String width;
	        width = Integer.valueOf(col3_max*14).toString();
		    String[] cols1 = new String[]{"title:'COL3'", "width:"+width, "type:'text'"};
		    cols1[0] = cols1[0].replace("COL3", header.getCOL3()); 
		    columns.add(cols1);
	        width = Integer.valueOf(col4_max*14).toString();
		    String[] cols2 = new String[]{"title:'COL4'", "width:"+width, "type:'text'"};
		    cols2[0] = cols2[0].replace("COL4", header.getCOL4()); 
		    columns.add(cols2);
	        width = Integer.valueOf(col5_max*14).toString();
		    String[] cols3 = new String[]{"title:'COL5'", "width:"+width, "type:'text'"};
		    cols3[0] = cols3[0].replace("COL5", header.getCOL5()); 
		    columns.add(cols3);
	        width = Integer.valueOf(col6_max*14).toString();
		    String[] cols4 = new String[]{"title:'COL6'", "width:"+width, "type:'text'"};
		    cols4[0] = cols4[0].replace("COL6", header.getCOL6()); 
		    columns.add(cols4);
			request.setAttribute("columns", columns);
       
        } catch (SQLException e) {
			e.printStackTrace();
		}

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
        if (type.startsWith("rireki")) {
	        //レスポンス
	        response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
        	PrintWriter out = response.getWriter();
        
	        try {
		        //Javaオブジェクトに値をセット
				ArrayList<PoRirekiBean> rireki = PoRirekiDAO.getInstance().readData(unitId);
			    
		        ObjectMapper mapper = new ObjectMapper();
		        // 戻り値用のオブジェクト作成
		        //Map<String, Object> resMap = new HashMap<>();
		        //resMap.put("data", rireki);
				//resMap.put("columns", columns);
		        try {
		            //JavaオブジェクトからJSONに変換
		        	//String json = mapper.writeValueAsString(resMap);
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
        }
	}

}
