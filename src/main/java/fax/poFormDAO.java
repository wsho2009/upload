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
public class poFormDAO {
	
	public poFormDAO() {
	}

	// インスタンスオブジェクトの生成->返却（コードの簡略化）
	public static poFormDAO getInstance() {
		return new poFormDAO();
	}
	
	// 検索処理
	// 戻り値		：ArrayList<Beanクラス>
	public ArrayList<poFormBean> read(String id) throws SQLException {
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
		
		ArrayList<poFormBean> list = new ArrayList<poFormBean>();		
        String[][] data = new String[][] {
        	{"1","A0001","AAAAA","test@login.com"},
        	{"2","B0002","BBBBB","test@login.com"}
        };
        poFormBean poForm = new poFormBean();
        int no;
		for (int i=0; i<2; i++) {
			// ユーザIDと名前をBeanクラスへセット
			poForm.setNo(Integer.parseInt(data[i][0]));
			poForm.setCode(data[i][1]);
			poForm.setFormId(data[i][2]);
			poForm.setMember(data[i][3]);
        	// リストにBeanクラスごと格納
			list.add(poForm);
			//Beanクラスを初期化
			poForm = new poFormBean();
		}
        
		// リストを返す
		return list;
/*
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
		*/
	}
}
