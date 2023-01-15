package konyurireki;

import java.io.Serializable;

public class konyuBean implements Serializable {

	private int mNo;
	private String mHizuke;
	private String mKonnyusaki;
	private String mSyubetsu;
	private String mHinmei;
	private String mKakaku;
	private String mSoryo;
	private String mKakakuKei;
	
	public konyuBean() {
	}

	public void setNo(int no) { this.mNo = no; }
	public void setHizuke(String hizuke) { this.mHizuke = hizuke; }
	public void setKonnyusaki(String konnyusaki) { this.mKonnyusaki = konnyusaki; }
	public void setSyubetsu(String syubetsu) { this.mSyubetsu = syubetsu; }
	public void setHinmei(String hinmei) { this.mHinmei = hinmei; }
	public void setKakaku(String kakaku) { this.mKakaku = kakaku; }
	public void setSoryo(String soryo) { this.mSoryo = soryo; }
	public void setKakakuKie(String kakakukei) { this.mKakakuKei = kakakukei; }

	public int getNo() { return this.mNo; }
	public String getHizuke()  { return this.mHizuke; }
	public String getKonnyusaki()  { return this.mKonnyusaki; }
	public String getSyubetsu() { return this.mSyubetsu; }
	public String getHinmei()  { return this.mHinmei; }
	public String getKakaku() { return this.mKakaku; }
	public String getSoryo()  { return this.mSoryo; }
	public String getKakakuKie() { return this.mKakakuKei; }
	public String getAllData() {
			return this.mNo + "\t"
					+ this.mHizuke + "\t"
					+ this.mKonnyusaki + "\t"
					+ this.mSyubetsu + "\t"
					+ this.mHinmei + "\t"
					+ this.mKakaku + "\t"
					+ this.mSoryo + "\t"
					+ this.mKakakuKei + "\r\n";		
	}
	
}
