package fax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OcrData {

	String unitId;
	String unitName;
	String uploadFilePath;
	String status;
	String csvFileName;
	String docsetId;
	String docsetName;
	String documentId;
	String documentName;
	String createdAt;
	int type;
	
	public void registData(String Name, String uploadFilePath, String docsetName, String documentId,
			String documentName, int type) {
		
		this.unitId = "";
		this.unitName = Name;
		this.uploadFilePath = uploadFilePath;
		this.status = "REGIST";
		this.csvFileName = "";
		this.docsetId = "";
		this.docsetName = docsetName;
		this.documentId = documentId;
		this.documentName = documentName;
		this.createdAt = "";
		this.type = type;
		
		try {
			insert();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	private void insert() throws SQLException {
		String sql = "INSERT INTO OCRDATATABLE(UNIT_ID,UNIT_NAME,UPLOAD_PATH,STATUS,DL_CSV_NAME,DOCSET_ID,DOCSET_NAME,DOCUMENT_ID,DOCUMENT_NAME,CREATEDAT,TYPE) " + 
					 "VALUES(?,?,?,?,?,?,?,?,?,sysdate,?)";
		
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		String URL = rb.getString("URL");
		String USER = rb.getString("USER");
		String PASS = rb.getString("PASS");
		
		//接続処理
		Connection conn = null;
				
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);
            conn.setAutoCommit(false);
            
            PreparedStatement ps = conn.prepareStatement(sql);
            int i=1;
            ps.setString(i++, this.unitId);
            ps.setString(i++, this.unitName);
            ps.setString(i++, this.uploadFilePath);
            ps.setString(i++, this.status);
            ps.setString(i++, this.csvFileName);
            ps.setString(i++, this.docsetId);
            ps.setString(i++, this.docsetName);
            ps.setString(i++, this.documentId);
            ps.setString(i++, this.documentName);
            //ps.setString(i++, this.createdAt);
            ps.setInt(i++, this.type);
            
            ps.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			// DB接続を解除
			if (conn != null) {
				conn.close();
			}
       }		
	}
	
}
