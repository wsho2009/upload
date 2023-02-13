package fax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OcrFormBean implements Serializable {
//import com.fasterxml.jackson.annotation.JsonProperty;
    @JsonProperty("No")
	private String No;
    @JsonProperty("Name") String Name;
    @JsonProperty("documentId")
	private String documentId;
    @JsonProperty("documentName") String documentName;
    @JsonProperty("docsetId")
	private String docsetId;
    @JsonProperty("docsetName") String docsetName;
        
	public OcrFormBean() {
		//COL = new String[4];
	}

	public void setNo(String No) {this.No = No;	}
	public void setName(String Name) {this.Name = Name;	}
	public void setDocumentId(String documentId) {this.documentId = documentId;	}
	public void setDocumentName(String documentName)  {this.documentName = documentName;}
	public void setDocsetId(String docsetId) {this.docsetId = docsetId;}
	public void setDocsetName(String docsetName)  {this.docsetName = docsetName; }
	
}
