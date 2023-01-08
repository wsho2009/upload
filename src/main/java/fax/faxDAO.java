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
public class faxDAO {
	
	public faxDAO() {
	}

	// インスタンスオブジェクトの生成->返却（コードの簡略化）
	public static faxDAO getInstance() {
		return new faxDAO();
	}
	
	// 検索処理
	// 戻り値		：ArrayList<Beanクラス>
	public ArrayList<faxDTO> read() throws SQLException {
		String URL;
		String USER;
		String PASS;
		String sql;
		//SQL作成
        sql = "select * from todo";
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		URL = rb.getString("URL");
		USER = rb.getString("USER");
		PASS = rb.getString("PASS");
		//接続処理
		Connection conn = null;
		ArrayList<faxDTO> fax_dao = new ArrayList<faxDTO>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);

			PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            faxDTO fax = new faxDTO();
			while(rs.next()) {
				// ユーザIDと名前をBeanクラスへセット
            	fax.setId(rs.getInt("id"));
            	fax.setTodo(rs.getString("TODO"));
            	fax.setTimeLimit(rs.getString("TIMELIMIT"));
            	// リストにBeanクラスごと格納
				fax_dao.add(fax);
				//Beanクラスを初期化
				fax = new faxDTO();
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
		return fax_dao;
	}
}
