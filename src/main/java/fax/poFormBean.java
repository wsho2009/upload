package fax;

import java.io.Serializable;

public class poFormBean implements Serializable {

	private int mNo;
	private String mCode;
	private String mFormId;
	private String mMember;
	
	public poFormBean() {
	}

	public void setNo(int no) { this.mNo = no; }
	public void setCode(String code) { this.mCode = code; }
	public void setFormId(String FormId) { this.mFormId = FormId; }
	public void setMember(String member) { this.mMember = member; }

	public int getId() { return this.mNo; }
	public String getCode()  { return this.mCode; }
	public String getFormId() { return this.mFormId; }
	public String getMember() { return this.mMember; }
}
