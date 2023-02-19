package api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class watchdog
 */
@WebServlet("/watchdog")
public class watchdog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public watchdog() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("response OK");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String node = request.getParameter("node");
        //System.out.println("node: " + node);
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		//String fileName = ".\\upload\\" + node + "_watchdog.dat";
        String fileName = ".\\" + node + "_watchdog.dat";
        System.out.println(node + " now: " + sdf.format(now));
		try {
			File file = new File(fileName);
			if (file.exists()) {
				//読み込み
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String strDate = br.readLine();	//1行読み込み
		        System.out.println(node + " pre: " + strDate);
				br.close();
				Date pre = sdf.parse(strDate);
				//差分計算
			    long nowtime = now.getTime();
			    long pretime = pre.getTime();
			    long diff = nowtime - pretime;
			    System.out.println(node + " diff: " + (int)(diff/1000) + "s");
			} 
			//新規作成
        	FileWriter filewriter = new FileWriter(file);
        	filewriter.write(sdf.format(now));
        	filewriter.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}        
	}

}
