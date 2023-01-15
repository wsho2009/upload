/**
 * 
 */
package konyurireki;

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
public class konyuDAO {

	// インスタンスオブジェクトの生成->返却（コードの簡略化）
	public static konyuDAO getInstance() {
		return new konyuDAO();
	}
	
	// 検索処理
	// 戻り値		：ArrayList<Beanクラス>
	public ArrayList<konyuBean> read(String Konnyusaki) throws SQLException {
		String URL;
		String USER;
		String PASS;
		String sql;
		//SQL作成
		sql = "select No,TO_CHAR(Hizuke,'YYYY/MM/DD') HIZUKE,Konnyusaki,Syubetsu,Hinmei,Kakaku,Soryo,Kakaku_Kei "
				+ "from KONYU_RIREKI where NO <> 0";
        if (Konnyusaki != null) {
        	sql = sql + " and Konnyusaki='" + Konnyusaki + "'";
        }
        sql = sql + " order by NO desc ";
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		URL = rb.getString("URL");
		USER = rb.getString("USER");
		PASS = rb.getString("PASS");
		//接続処理
		Connection conn = null;
		ArrayList<konyuBean> konyu_dao = new ArrayList<konyuBean>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);

			PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            konyuBean dao = new konyuBean();
			while(rs.next()) {
				// ユーザIDと名前をBeanクラスへセット
				dao.setNo(rs.getInt("NO"));
				dao.setHizuke(rs.getString("HIZUKE"));
				dao.setKonnyusaki(rs.getString("KONNYUSAKI"));
				dao.setSyubetsu(rs.getString("SYUBETSU"));
				dao.setHinmei(rs.getString("HINMEI"));
				dao.setKakaku(rs.getString("KAKAKU"));
				dao.setSoryo(rs.getString("SORYO"));
				dao.setKakakuKie(rs.getString("KAKAKU_KEI"));
				// リストにBeanクラスごと格納
				konyu_dao.add(dao);
				//Beanクラスを初期化
				dao = new konyuBean();
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
		return konyu_dao;
	}
}
