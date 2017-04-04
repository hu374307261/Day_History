package com.baway.day_history.bean;

import org.litepal.crud.DataSupport;

public class Persontos extends DataSupport{
	
	private String title_name;
	private String content_vt;
	private String url;
    private  String dataTime;
	private String e_id;


	@Override
	public String toString() {
		return "Persontos{" +
				"title_name='" + title_name + '\'' +
				", content_vt='" + content_vt + '\'' +
				", url='" + url + '\'' +
				", dataTime='" + dataTime + '\'' +
				'}';
	}

	public String getE_id() {
		return e_id;
	}

	public void setE_id(String e_id) {
		this.e_id = e_id;
	}

	public String getTitle_name() {
		return title_name;
	}

	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}

	public String getContent_vt() {
		return content_vt;
	}

	public void setContent_vt(String content_vt) {
		this.content_vt = content_vt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public Persontos() {

		super();

	}


}
