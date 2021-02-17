package com.fangming.meituan.data;

import java.io.Serializable;

import com.fangming.dbmanager.AnnotationColumns;
import com.fangming.dbmanager.AnnotationTables;

@AnnotationTables
public class T_SjData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@AnnotationColumns
	private String storeName;//µêÆÌÃû³Æ
	@AnnotationColumns
	private Long eltNum;//ÆÀ¼ÛÐÇ¼¶
	@AnnotationColumns
	private Long evaluation;//ÆÀ¼ÛÊý
	@AnnotationColumns
	private String imgUrl;//Í¼Æ¬url
	@AnnotationColumns
	private String add;//µØÖ·
	
	@AnnotationColumns
	private String type;//Àà±ð
	
	@AnnotationColumns(isPrimaryKey=true)
	private Long _id;

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getEltNum() {
		return eltNum;
	}

	public void setEltNum(Long eltNum) {
		this.eltNum = eltNum;
	}

	public Long getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Long evaluation) {
		this.evaluation = evaluation;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public T_SjData() {
		super();
	}

}
