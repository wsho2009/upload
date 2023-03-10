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
public class PoFormDAO {
	
	public PoFormDAO() {
	}

	// インスタンスオブジェクトの生成->返却（コードの簡略化）
	public static PoFormDAO getInstance() {
		return new PoFormDAO();
	}
	
	// 検索処理
	// 戻り値		：ArrayList<Beanクラス>
	public ArrayList<PoFormBean> read(String id) throws SQLException {
		String URL;
		String USER;
		String PASS;
		String sql;
		//SQL作成
        sql = "select * from POFORMTABLE";
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		URL = rb.getString("URL");
		USER = rb.getString("USER");
		PASS = rb.getString("PASS");
		
		//接続処理
		Connection conn = null;
		ArrayList<PoFormBean> list = new ArrayList<PoFormBean>();		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);

			PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            PoFormBean poForm = new PoFormBean();
			while(rs.next()) {
				// ユーザIDと名前をBeanクラスへセット
				poForm.setNo(rs.getInt("NO"));
				poForm.setCode(rs.getString("CODE"));
				poForm.setFormId(rs.getString("FORM_ID"));
				poForm.setFormName(rs.getString("FORM_NAME"));
				poForm.setMember(rs.getString("MEMBER"));
				list.add(poForm);
				//Beanクラスを初期化
				poForm = new PoFormBean();
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
