/**
 * 
 */
package fax;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author PC
 *
 */
public class poUploadDAO {
	
	public poUploadDAO() {
	}

	// インスタンスオブジェクトの生成->返却（コードの簡略化）
	public static poUploadDAO getInstance() {
		return new poUploadDAO();
	}
	
	// 検索処理
	// 戻り値		：ArrayList<Beanクラス>
	public ArrayList<poUploadBean> read(String id) throws SQLException {
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
		
		ArrayList<poUploadBean> list = new ArrayList<poUploadBean>();		
        String[][] data = new String[][] {
        	{"Taro","2023/01/01 10:11:22","AAAAA.pdf","AAA_NAME"},
        	{"Jiro","2023/01/04 08:11:22","BBBBB.pdf","BBB_NAME"}
        };
        poUploadBean poUpload = new poUploadBean();
		for (int i=0; i<2; i++) {
			// ユーザIDと名前をBeanクラスへセット
			poUpload.setUserName(data[i][0]);
			poUpload.setDatetime(data[i][1]);
			poUpload.setFileName(data[i][2]);
			poUpload.setFormName(data[i][3]);
        	// リストにBeanクラスごと格納
			list.add(poUpload);
			//Beanクラスを初期化
			poUpload = new poUploadBean();
		}
        
		// リストを返す
		return list;
/*
		//接続処理
		Connection conn = null;
		ArrayList<poFormBean> fax_dao = new ArrayList<poFormBean>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);

			PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            poFormBean fax = new poFormBean();
			while(rs.next()) {
				// ユーザIDと名前をBeanクラスへセット
            	fax.setId(rs.getInt("id"));
            	fax.setTodo(rs.getString("TODO"));
            	fax.setTimeLimit(rs.getString("TIMELIMIT"));
            	// リストにBeanクラスごと格納
				fax_dao.add(fax);
				//Beanクラスを初期化
				fax = new poFormBean();
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
		*/
	}
}
