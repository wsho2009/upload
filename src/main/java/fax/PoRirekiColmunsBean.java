package fax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PoRirekiColmunsBean implements Serializable {
//import com.fasterxml.jackson.annotation.JsonProperty;
    @JsonProperty("title")
	private String Title;
    @JsonProperty("name")
	private String Name;
    @JsonProperty("width")
	private String Width;
    @JsonProperty("type")
	private String Type;

	public PoRirekiColmunsBean(String title, String name, String width, String type) {
		this.Title = title;
		this.Name = name;
		this.Width = width;
		this.Type = type;
	}
}
