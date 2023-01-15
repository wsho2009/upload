package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import konyurireki.konyuBean;
import konyurireki.konyuDAO;

/**
 * Servlet implementation class webApi
 */
@WebServlet("/webApi")
public class webApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public webApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String Object = request.getParameter("obj");
        if (Object == null) {
        	return;
        }
        if (Object.startsWith("konyurireki")) {
        	//パラメータ解析
	        String Konyuusaki = request.getParameter("konyuusaki");
	        System.out.println("Konyuusaki: " + Konyuusaki);
	
			try {
				ArrayList<konyuBean> list = konyuDAO.getInstance().read(Konyuusaki);
				//https://rainbow-engine.com/jspservlet-csv-download/
				response.setContentType("text/tsv;charset=UTF8");
				String fileName = new String("konyurireki.tsv".getBytes("Shift_JIS"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
				PrintWriter writer = response.getWriter();
	  			String line;
				//ヘッダ
				line = "ID\t日付\t購入先\t種別\t品名\t価格\t送料\t合計\r\n";
				writer.write(line);
				//データ
				for (int i=0; i<list.size(); i++) {
					konyuBean dto = (konyuBean)list.get(i);
		  			line = dto.getAllData();
		  			writer.write(line);
				}
				writer.close();
	
			}catch(Exception e){
			}
        } else {
        	//対象データなし
        	//http://struts.wasureppoi.com/servlet/09_sendError.html
        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("text/html;charset=UTF8");
			PrintWriter writer = response.getWriter();
			String line = "対象データなし";
			writer.write(line);
			writer.close();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
