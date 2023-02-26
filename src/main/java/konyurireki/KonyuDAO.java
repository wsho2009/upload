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
public class KonyuDAO {

	// インスタンスオブジェクトの生成->返却（コードの簡略化）
	public static KonyuDAO getInstance() {
		return new KonyuDAO();
	}
	
	public ArrayList<KonyuBean> read() throws SQLException {
		return read("", "", "", "");
	}
	
	public ArrayList<KonyuBean> read(String konyusaki, String syubetsu, String date_fr, String date_to) throws SQLException {
		String URL;
		String USER;
		String PASS;
		String sql;
		if (konyusaki.equals("")== true)
			konyusaki = "%";
		if (syubetsu.equals("")== true)
			syubetsu = "%";
		//SQL作成
		sql = "select No,TO_CHAR(Hizuke,'YYYY/MM/DD') HIZUKE,Konnyusaki,Syubetsu,Hinmei,Kakaku,Soryo,Kakaku_Kei "
			+ "from KONYU_RIREKI "
			+ "where NO <> 0 "
			+ "and Konnyusaki like ? "
			+ "and Syubetsu like ? "
			+ "and Hizuke >= NVL(?, '1900/1/1') and Hizuke <= NVL(?, '2900/12/31') "
        	+ " order by NO desc ";
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		URL = rb.getString("URL");
		USER = rb.getString("USER");
		PASS = rb.getString("PASS");
		//接続処理
		Connection conn = null;
		ArrayList<KonyuBean> konyu_dao = new ArrayList<KonyuBean>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setString(i++, konyusaki);
			ps.setString(i++, syubetsu);
			ps.setString(i++, date_fr);
			ps.setString(i++, date_to);
            ResultSet rs = ps.executeQuery();

            KonyuBean dao = new KonyuBean();
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
				dao = new KonyuBean();
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
