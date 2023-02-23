package fax;

import static java.nio.file.StandardCopyOption.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
@MultipartConfig(fileSizeThreshold = 5000000, maxFileSize = 700 * 1024 * 1024, location = "D:\\pleiades\\2022-06\\workspace\\upload\\upload")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 文字化け防止
		String userId = "test@login.com";
		String userName = "テストログイン太郎";
		String code = "99";
		String title = "アップロード";
		String unitId = "40952";
		String unitStatus = "COMPLETE";
		// 次の画面(jsp)に値を渡す
		request.setAttribute("title", title);
		request.setAttribute("userId", userId);
		request.setAttribute("userName", userName);
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
        String userId = request.getParameter("userId");
        System.out.println("userId: " + userId);      
        //Form list 
        if (type.equals("select") == true) {
	        //レスポンス
	        response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
        	PrintWriter out = response.getWriter();
			try {
		        //Javaオブジェクトに値をセット
				ArrayList<PoFormBean> select = PoFormDAO.getInstance().read(userId);
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
				ArrayList<PoUploadBean> rireki = PoUploadDAO.getInstance().read(userId);
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
		// Upload file
        } else if (type.equals("upload") == true) {
	        //https://kuwalab.hatenablog.jp/entry/20120424/1335266757
			request.setCharacterEncoding("utf-8");
			Part part = request.getPart("file");
			String filename = getFilename(part);
            System.out.println("filename: " + filename);      
            String userName = request.getParameter("userName");
            userName = new String(userName.getBytes("iso-8859-1"), "utf-8");	//https://www.prime-architect.co.jp/myblog/google-app-engine-260
            System.out.println("userName: " + userName);      
	        String code = request.getParameter("code");
	        System.out.println(code);
	        String dt = request.getParameter("dt");
	        System.out.println(dt);
	        String formId = request.getParameter("formId");
	        System.out.println(formId);
	        String formName = request.getParameter("formName");
	        System.out.println(formName);
			ResourceBundle rb = ResourceBundle.getBundle("prop");
			String LOCAL_PATH = rb.getString("LOCAL_PATH");
			String ch = formId.substring(5, 5);		// 6桁目
			String toriCd;
			if (ch.equals("")) {
				toriCd = formId.substring(0, 5);	//5桁
				formId = formId.substring(6, 11);	//5桁
			} else {
				toriCd = formId.substring(0, 6);	//6桁
				formId = formId.substring(7, 12);	//5桁
			}
			
			String dtStr = dt.substring(0,4) + dt.substring(5,7) + dt.substring(8,10) + dt.substring(11,13) + dt.substring(14,16) + dt.substring(17,19);
			System.out.println(part.getContentType());
			String OCR_INPUT_PATH = rb.getString("OCR_INPUT_PATH");
			String ext = filename.substring(filename.lastIndexOf("."));
			String fileName = toriCd + "_" + dtStr + ext;
			String localFilePath = LOCAL_PATH + fileName;
			String inputFilePath = OCR_INPUT_PATH + fileName;
			String uploadFilePath = null;
			part.write(localFilePath);	//ローカルへファイル保存
	    	Path src = Paths.get(localFilePath);
	        Path dst = Paths.get(inputFilePath);
	    	Files.move(src, dst, REPLACE_EXISTING);
			if (part.getContentType().equals("application/pdf")) {
				//pdfファイルは、OCR登録
				registOcrProcess(formId, inputFilePath);
				
				//ファイルをアップロード後、履歴を登録。その際、OCR変換後のxlsxに変更する。
				String PO_UPLOAD_PATH = rb.getString("PO_UPLOAD_PATH");
				ext = ".xlsx";
				uploadFilePath = PO_UPLOAD_PATH + toriCd + "_" + dtStr + ext;
				registUploadTable(userId, userName, dt, toriCd, uploadFilePath, code);
			} else {
				//ファイルをアップロード後、履歴を登録
				String PO_UPLOAD_PATH = rb.getString("PO_UPLOAD_PATH");
				uploadFilePath = PO_UPLOAD_PATH + toriCd + "_" + dtStr + ext;
				registUploadTable(userId, userName, dt, toriCd, uploadFilePath, code);

				//uploadフォルダへ直接コピー
		    	src = Paths.get(inputFilePath);
		        dst = Paths.get(uploadFilePath);
		    	Files.copy(src, dst, REPLACE_EXISTING);
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
			
	        //レスポンス
			PrintWriter pw = response.getWriter();
			pw.println("{\"result\":\"ok\"}");
			pw.flush();
			pw.close();
			
			//ユーザーがアップロードしたことを通知するメール送信
			JavaMail mail = new JavaMail();
			mail.from = rb.getString("MAIL_FROM");;
			mail.to = "";
			mail.cc = "";
			mail.bcc = "";
			mail.subject = "アップロードされました。";
			mail.body = "ID: " + userId + "\n" +
					   "dt: " + dt  + "\n" +
					   "toriCd: " + toriCd + "\n" +
					   "file: " + toriCd + "\n" +
					   "file: " + uploadFilePath + "\n";;
			mail.sendRawMail();
	    } else if (type.equals("uploadTest") == true) {
			request.setCharacterEncoding("utf-8");
			Part part = request.getPart("file");
			String filename = getFilename(part);
	        System.out.println("filename: " + filename);      
			ResourceBundle rb = ResourceBundle.getBundle("prop");
			String LOCAL_PATH = rb.getString("LOCAL_PATH");
			
			/*------------------------------------------------------------*/
			System.out.println(part.getContentType());
			String localFilePath = LOCAL_PATH + filename;
			part.write(localFilePath);	//ローカルへファイル保存
			
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
	
	//OCR登録処理
	private void registOcrProcess(String documentId, String uploadFilePath) {
		
		OcrFormBean form = null;
		try {
	        //Javaオブジェクトに値をセット
			form = OcrFormDAO.getInstance().readData(documentId);
	        ObjectMapper mapper = new ObjectMapper();
	        try {
	            //JavaオブジェクトからJSONに変換
	            String json = mapper.writeValueAsString(form);
	            //JSONの出力
	            System.out.println(json);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		OcrData ocr = new OcrData();
		ocr.registData(form.Name, uploadFilePath, form.docsetName, documentId, form.documentName, 2);
	}

	private void registUploadTable(String userId, String userName, String dt, String toriCd, String uploadFilePath, String code) {
		PoUploadBean upload = new PoUploadBean();
		upload.registData(userId, userName, dt, toriCd, uploadFilePath, code);
	}

	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
