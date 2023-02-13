package fax;

import java.io.Serializable;

public class FaxDataBean implements Serializable {

	private int mId;
	private String mTodo;
	private String mTimeLimit;
	
	public FaxDataBean() {
	}

	public void setId(int id) { this.mId = id; }
	public void setTodo(String todo) { this.mTodo = todo; }
	public void setTimeLimit(String timeLimit) { this.mTimeLimit = timeLimit; }

	public int getId() { return this.mId; }
	public String getTodo()  { return this.mTodo; }
	public String getTimeLimit() { return this.mTimeLimit; }
}
