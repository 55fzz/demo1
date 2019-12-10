package com.example.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2019-12-04
 */
public class Recycle extends Model<Recycle> {

    private static final long serialVersionUID = 1L;

	private Integer id;
    /**
     * 标题
     */
	private String title;
    /**
     * 作者
     */
	private String author;
    /**
     * 保存地址
     */
	private String address;
    /**
     * 大小
     */
	private String size;
    /**
     * 创建时间
     */
	private Date createDate;
	private Integer cid;
	private Integer uid;


	public Integer getId() {
		return id;
	}

	public Recycle setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Recycle setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public Recycle setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Recycle setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getSize() {
		return size;
	}

	public Recycle setSize(String size) {
		this.size = size;
		return this;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Recycle setCreateDate(Date createDate) {
		this.createDate = createDate;
		return this;
	}

	public Integer getCid() {
		return cid;
	}

	public Recycle setCid(Integer cid) {
		this.cid = cid;
		return this;
	}

	public Integer getUid() {
		return uid;
	}

	public Recycle setUid(Integer uid) {
		this.uid = uid;
		return this;
	}

	public static final String ID = "id";

	public static final String TITLE = "title";

	public static final String AUTHOR = "author";

	public static final String ADDRESS = "address";

	public static final String SIZE = "size";

	public static final String CREATEDATE = "createDate";

	public static final String CID = "cid";

	public static final String UID = "uid";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Recycle{" +
			", id=" + id +
			", title=" + title +
			", author=" + author +
			", address=" + address +
			", size=" + size +
			", createDate=" + createDate +
			", cid=" + cid +
			", uid=" + uid +
			"}";
	}
}
