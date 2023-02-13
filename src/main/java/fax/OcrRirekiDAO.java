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
public class OcrRirekiDAO {
	
	public OcrRirekiDAO() {
	}

	// インスタンスオブジェクトの生成->返却（コードの簡略化）
	public static OcrRirekiDAO getInstance() {
		return new OcrRirekiDAO();
	}

	// 検索処理
	// 戻り値		：ArrayList<Beanクラス>
	public ArrayList<OcrRirekiBean> readData(String unitId, int dataWidth) throws SQLException {
		String fields = "";
		for(int i=0; i<dataWidth; i++) {
        	fields = fields + "COL" + (i+3) + ",";
		}
		fields = fields.substring(0, fields.length() - 1);	//末尾 , 削除
		String sql = "select " + fields + " from PORIREKITABLE where COL0 = ? and COL1 <> '0' order by COL1";
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		String URL = rb.getString("URL");
		String USER = rb.getString("USER");
		String PASS = rb.getString("PASS");
		
		//接続処理
		Connection conn = null;
		ArrayList<OcrRirekiBean> list = new ArrayList<OcrRirekiBean>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, unitId);
            ResultSet rs = ps.executeQuery();
            
            OcrRirekiBean rireki = new OcrRirekiBean();
			while(rs.next()) {
				// ユーザIDと名前をBeanクラスへセット
				for(int i=0; i<dataWidth; i++) {
	            	rireki.setCOL(i, rs.getString("COL"+(i+3)));
				}
            	// リストにBeanクラスごと格納
				list.add(rireki);
				//Beanクラスを初期化
				rireki = new OcrRirekiBean();
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
	public OcrRirekiBean readHeader(String unitId) throws SQLException {
 		int dataWidth = 4;
		String fields = "";
		for(int i=0; i<dataWidth; i++) {
        	fields = fields + "COL" + (i+3) + ",";
		}
		fields = fields.substring(0, fields.length() - 1);	//末尾 , 削除
		String sql = "select " + fields + " from PORIREKITABLE where COL0 = ? and COL1 = '0' order by COL1";
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		String URL = rb.getString("URL");
		String USER = rb.getString("USER");
		String PASS = rb.getString("PASS");
		
		//接続処理
		Connection conn = null;
		//ArrayList<OcrRirekiBean> list = new ArrayList<OcrRirekiBean>();
        OcrRirekiBean rireki = new OcrRirekiBean();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, unitId);
            ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				// ユーザIDと名前をBeanクラスへセット
				for(int i=0; i<dataWidth; i++) {
	            	rireki.setCOL(i, rs.getString("COL"+(i+3)));
				}
            	// リストにBeanクラスごと格納
            	//list.add(rireki);
				//Beanクラスを初期化
            	//rireki = new OcrRirekiBean();
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
