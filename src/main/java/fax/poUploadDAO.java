/**
 * 
 */
package fax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author PC
 *
 */
public class PoUploadDAO {
	
	public PoUploadDAO() {
	}

	// インスタンスオブジェクトの生成->返却（コードの簡略化）
	public static PoUploadDAO getInstance() {
		return new PoUploadDAO();
	}
	
	// 検索処理
	// 戻り値		：ArrayList<Beanクラス>
	public ArrayList<PoUploadBean> read(String unitId) throws SQLException {
		String sql = "select * from POUPLOADTABLE where USER_ID = ?";
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		String URL = rb.getString("URL");
		String USER = rb.getString("USER");
		String PASS = rb.getString("PASS");
		
		//接続処理
		Connection conn = null;
		ArrayList<PoUploadBean> list = new ArrayList<PoUploadBean>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, unitId);
            ResultSet rs = ps.executeQuery();
            
            PoUploadBean upload = new PoUploadBean();
			while(rs.next()) {
				// ユーザIDと名前をBeanクラスへセット
				upload.setUserId(rs.getString("USER_ID"));
				upload.setUserName(rs.getString("USER_NAME"));
				upload.setDatetime(rs.getString("CREATED_DATE"));
				upload.setToriCd(rs.getString("TORIHIKISAKI_CD"));
				upload.setUploadPath(rs.getString("UPLOAD_PATH"));
				upload.setCode(rs.getString("CODE"));
            	// リストにBeanクラスごと格納
				list.add(upload);
				//Beanクラスを初期化
				upload = new PoUploadBean();
			}
			
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
		return list;
	}
}
