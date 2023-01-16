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
public class PoRirekiDAO {
	
	public PoRirekiDAO() {
	}

	// インスタンスオブジェクトの生成->返却（コードの簡略化）
	public static PoRirekiDAO getInstance() {
		return new PoRirekiDAO();
	}

	// 検索処理
	// 戻り値		：ArrayList<Beanクラス>
	public ArrayList<PoRirekiBean> readData(String unitId) throws SQLException {
		String URL;
		String USER;
		String PASS;
		String sql;
		//SQL作成
		String fields = "COL3,COL4,COL5,COL6";
        sql = "select " + fields + " from PORIREKITABLE where COL0 = ? and COL1 <> '0' order by COL1";
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		URL = rb.getString("URL");
		USER = rb.getString("USER");
		PASS = rb.getString("PASS");
		
		//接続処理
		Connection conn = null;
		ArrayList<PoRirekiBean> list = new ArrayList<PoRirekiBean>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, unitId);
            ResultSet rs = ps.executeQuery();

            PoRirekiBean rireki = new PoRirekiBean();
			while(rs.next()) {
				// ユーザIDと名前をBeanクラスへセット
            	rireki.setCOL3(rs.getString("COL3"));
            	rireki.setCOL4(rs.getString("COL4"));
            	rireki.setCOL5(rs.getString("COL5"));
            	rireki.setCOL6(rs.getString("COL6"));
            	// リストにBeanクラスごと格納
				list.add(rireki);
				//Beanクラスを初期化
				rireki = new PoRirekiBean();
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
	public PoRirekiBean readHeader(String unitId) throws SQLException {
		String URL;
		String USER;
		String PASS;
		String sql;
		//SQL作成
		String fields = "COL3,COL4,COL5,COL6";
        sql = "select " + fields + " from PORIREKITABLE where COL0 = ? and COL1 = '0' order by COL1";
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		URL = rb.getString("URL");
		USER = rb.getString("USER");
		PASS = rb.getString("PASS");
		
		//接続処理
		Connection conn = null;
		//ArrayList<PoRirekiBean> list = new ArrayList<PoRirekiBean>();
        PoRirekiBean rireki = new PoRirekiBean();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, unitId);
            ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				// ユーザIDと名前をBeanクラスへセット
            	rireki.setCOL3(rs.getString("COL3"));
            	rireki.setCOL4(rs.getString("COL4"));
            	rireki.setCOL5(rs.getString("COL5"));
            	rireki.setCOL6(rs.getString("COL6"));
            	// リストにBeanクラスごと格納
            	//list.add(rireki);
				//Beanクラスを初期化
            	//rireki = new PoRirekiBean();
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
		return rireki;
	}
}
