package fax;

import java.io.Serializable;


public class PoRirekiBean implements Serializable {
//import com.fasterxml.jackson.annotation.JsonProperty;
/*    @JsonProperty("COL0")
	private String COL0;
    @JsonProperty("COL1")
	private String COL1;
    @JsonProperty("COL2")
	private String COL2;*/
    //@JsonProperty("COL3")
	private String COL3;
	//@JsonProperty("COL4")
	private String COL4;
	//@JsonProperty("COL5")
	private String COL5;
	//@JsonProperty("COL6")
	private String COL6;
	
	public PoRirekiBean() {
	}
/*
	public void setCOL0(String col) { this.COL0 = col; }
	public void setCOL1(String col) { this.COL1 = col; }
	public void setCOL2(String col) { this.COL2 = col; }*/
	public void setCOL3(String col) { this.COL3 = col; }
	public void setCOL4(String col) { this.COL4 = col; }
	public void setCOL5(String col) { this.COL5 = col; }
	public void setCOL6(String col) { this.COL6 = col; }
/*	
	public String getCOL0()  { return this.COL0; }
	public String getCOL1()  { return this.COL1; }
	public String getCOL2()  { return this.COL2; }*/
	public String getCOL3()  { return this.COL3; }
	public String getCOL4()  { return this.COL4; }
	public String getCOL5()  { return this.COL5; }
	public String getCOL6()  { return this.COL6; }
}
