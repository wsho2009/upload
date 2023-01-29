package fax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PoRirekiBean implements Serializable {
//import com.fasterxml.jackson.annotation.JsonProperty;
    @JsonProperty("COL0")
	private String COL0;
    @JsonProperty("COL1")
	private String COL1;
    @JsonProperty("COL2")
	private String COL2;
    @JsonProperty("COL3")
	private String COL3;
    @JsonProperty("COL4")
	private String COL4;
    @JsonProperty("COL5")
	private String COL5;
    @JsonProperty("COL6")
	private String COL6;
	public PoRirekiBean() {
		//COL = new String[4];
	}
	public void setCOL(int index, String col) { 
		switch (index) {
		case 0: 
			this.COL0 = col; 
			break;
		case 1: 
			this.COL1 = col; 
			break;
		case 2: 
			this.COL2 = col; 
			break;
		case 3: 
			this.COL3 = col; 
			break;
		case 4: 
			this.COL4 = col; 
			break;
		case 5: 
			this.COL5 = col; 
			break;
		case 6: 
			this.COL6 = col; 
			break;
		}
	}
	
	public String getCOL(int index)  {
		switch (index) {
		case 0: 
			return this.COL0; 
		case 1: 
			return this.COL1; 
		case 2: 
			return this.COL2; 
		case 3: 
			return this.COL3; 
		case 4: 
			return this.COL4; 
		case 5: 
			return this.COL5; 
		case 6: 
			return this.COL6; 
		}
		return null;
	}
}
