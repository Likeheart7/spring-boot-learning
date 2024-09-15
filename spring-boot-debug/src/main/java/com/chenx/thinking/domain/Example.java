package com.chenx.thinking.domain;

public class Example {
	private String title;
	private String desc;

	@Override
	public String toString() {
		return "Example{" +
				"title='" + title + '\'' +
				", desc='" + desc + '\'' +
				'}';
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
