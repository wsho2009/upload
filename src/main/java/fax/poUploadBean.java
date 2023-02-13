package fax;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PoUploadBean implements Serializable {

    @JsonProperty("userid")
	private String userId;
    @JsonProperty("username")
	private String userName;
    @JsonProperty("datetime")
	private String dt;
    @JsonProperty("toricd")
	private String toriCd;
    @JsonProperty("uploadpath")
	private String uploadPath;
    @JsonProperty("code")
	private String code;
	
	public PoUploadBean() {
	}

	public void setUserId(String UserId) { this.userId = UserId; }
	public void setUserName(String UserName) { this.userName = UserName; }
	public void setDatetime(String Datetime) { this.dt = Datetime; }
	public void setToriCd(String ToriCd) { this.toriCd = ToriCd; }
	public void setUploadPath(String UploadPath) { this.uploadPath = UploadPath; }
	public void setCode(String Code) { this.code = Code; }

	public String getUserId() { return this.userId; }
	public String getUserName() { return this.userName; }
	public String getDatetime()  { return this.dt; }
	public String getToricd() { return this.toriCd; }
	public String getUploadPath() { return this.uploadPath; }
	public String getCode()  { return this.code; }
	
	public void registData(String userId, String userName, String dt, String toriCd, String uploadFilePath, String code) {
		
		this.userId = userId;
		this.userName = userName;
		this.dt = dt;
		this.toriCd = toriCd;
		this.uploadPath = uploadFilePath;
		this.code = code;
		
		try {
			int count = query_count(uploadFilePath);
			if (count == 0) {
				insert();
			} else {
				update();
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	private int query_count(String uploadFilePath) throws SQLException {
		int count = -1;
		String sql = "select count(*) from POUPLOADTABLE where UPLOAD_PATH=?";
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
			ps.setString(1, uploadFilePath);
            ResultSet rs = ps.executeQuery();
            
            //1レコードなので、listなし
            rs.next();
            count = rs.getInt("COUNT(*)");
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
		return count;
	}

	private void insert() throws SQLException {
		String sql = "INSERT INTO POUPLOADTABLE(USER_ID,USER_NAME,CREATED_DATE,TORIHIKISAKI_CD,UPLOAD_PATH,CODE) " + 
					 "VALUES(?,?,TO_DATE(?,'YYYY/MM/DD HH24:MI:SS'),?,?,?)";
		
        //接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		String URL = rb.getString("URL");
		String USER = rb.getString("USER");
		String PASS = rb.getString("PASS");
		
		//接続処理
		Connection conn = null;
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);
            conn.setAutoCommit(false);
            
            PreparedStatement ps = conn.prepareStatement(sql);
            int i=1;
            ps.setString(i++, this.userId);
            ps.setString(i++, this.userName);
            ps.setString(i++, this.dt);
            ps.setString(i++, this.toriCd);
            ps.setString(i++, this.uploadPath);
            ps.setString(i++, this.code);
            
            ps.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			// DB接続を解除
			if (conn != null) {
				conn.close();
			}
        }
	}

	private void update() throws SQLException {
		String sql = "UPDATE POUPLOADTABLE SET USER_ID=?,USER_NAME=?,CREATED_DATE=TO_DATE(?,'YYYY/MM/DD HH:MM:SS'),TORIHIKISAKI_CD=?,CODE=? " + 
				 	 "WHERE UPLOAD_PATH=?";
	
		//接続情報取得
		ResourceBundle rb = ResourceBundle.getBundle("prop");
		String URL = rb.getString("URL");
		String USER = rb.getString("USER");
		String PASS = rb.getString("PASS");
		
		//接続処理
		Connection conn = null;
	   try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println(sql);
	       conn.setAutoCommit(false);
	       
	       PreparedStatement ps = conn.prepareStatement(sql);
	       int i=1;
	       ps.setString(i++, this.userId);
	       ps.setString(i++, this.userName);
	       ps.setString(i++, this.dt);
	       ps.setString(i++, this.toriCd);
	       ps.setString(i++, this.uploadPath);
	       ps.setString(i++, this.code);
	       
	       ps.executeUpdate();
	       conn.commit();
	   } catch (Exception e) {
	       e.printStackTrace();
	   } finally {
			// DB接続を解除
			if (conn != null) {
				conn.close();
			}
	   }
	}
}
