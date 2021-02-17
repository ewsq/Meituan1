package com.fangming.meituan.data;

public class TwoLevelMenu {

	private String name;//²Ëµ¥Ãû³Æ
	
	private int id;//Í¼±êid

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TwoLevelMenu(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	
}
