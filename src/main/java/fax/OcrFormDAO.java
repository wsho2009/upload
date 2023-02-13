/**
 * 
 */
package fax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author PC
 *
 */
public class OcrFormDAO {
	
	public OcrFormDAO() {
	}

	// インスタンスオブジェクトの生成->返却（コードの簡略化）
	public static OcrFormDAO getInstance() {
		return new OcrFormDAO();
	}

	public OcrFormBean readData(String documentId) throws SQLException {
		
		if (documentId.equals("") || documentId == null) {
			return null;
		}
		String sql = "select * from OCRFORMTABLE where DOCUMENT_ID=?";
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		String URL = rb.getString("URL");
		String USER = rb.getString("USER");
		String PASS = rb.getString("PASS");
		
		//接続処理
		Connection conn = null;
        OcrFormBean form = new OcrFormBean();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, documentId);
            ResultSet rs = ps.executeQuery();
            
            //1レコードなので、listなし
            rs.next();
			form.setNo(rs.getString("NO"));
			form.setName(rs.getString("FORM_NAME"));
			form.setDocumentId(rs.getString("DOCUMENT_ID"));
			form.setDocumentName(rs.getString("DOCUMENT_NAME"));
			form.setDocsetId(rs.getString("DOCSET_ID"));
			form.setDocsetName(rs.getString("DOCSET_NAME"));
			
		} catch(SQLException sql_e) {
			// エラーハンドリング
			System.out.println("sql実行失敗");
			sql_e.printStackTrace();
			
		} catch(ClassNotFoundException e) {
			// エラーハンドリング
			System.out.println("JDBCドライバ関連エラー");
			e.printStackTrace();
			
		} finally {
			// DB接続を解除
			if (conn != null) {
				conn.close();
			}
		}
		// リストを返す
		return form;
	}
}
