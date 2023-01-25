package fax;

import java.io.Serializable;


public class PoRirekiBean implements Serializable {
//import com.fasterxml.jackson.annotation.JsonProperty;
	private String COL[];
	
	public PoRirekiBean() {
		COL = new String[4];
	}
	public void setCOL(int index, String col) { this.COL[index] = col; }
	
	public String getCOL(int index)  { return this.COL[index]; }
}
