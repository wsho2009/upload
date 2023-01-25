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
		int dataWidth = 4;
		//int col_width[]
		//col_width = new int[dataWidth];
		int col_width[] = new int[dataWidth];;
        try {
	        //Javaオブジェクトに値をセット
			ArrayList<PoRirekiBean> list = PoRirekiDAO.getInstance().readData(unitId);
			//4列
			for (int i=0; i<list.size(); i++) {
				PoRirekiBean rireki = list.get(i);
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
			PoRirekiBean header = PoRirekiDAO.getInstance().readHeader(unitId);
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
			    cols = new String[]{"title:'" + header.getCOL(i) +"'", "width:"+width, "type:'text'"};
			    columns.add(cols);
	        }
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
