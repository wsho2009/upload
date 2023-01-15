package fax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class poUploadBean implements Serializable {

    @JsonProperty("username")
	private String mUserName;
    @JsonProperty("datetime")
	private String mDatetime;
    @JsonProperty("filename")
	private String mFileName;
    @JsonProperty("formname")
	private String mFormName;
	
	public poUploadBean() {
	}

	public void setUserName(String UserName) { this.mUserName = UserName; }
	public void setDatetime(String Datetime) { this.mDatetime = Datetime; }
	public void setFileName(String FileName) { this.mFileName = FileName; }
	public void setFormName(String FormName) { this.mFormName = FormName; }

	public String getUserName() { return this.mUserName; }
	public String getCode()  { return this.mDatetime; }
	public String getFileName() { return this.mFileName; }
	public String getFormName() { return this.mFormName; }
}
