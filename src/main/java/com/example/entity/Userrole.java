package com.example.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2019-11-20
 */
public class Userrole extends Model<Userrole> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private Integer uid;
	private Integer rid;


	public Integer getId() {
		return id;
	}

	public Userrole setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getUid() {
		return uid;
	}

	public Userrole setUid(Integer uid) {
		this.uid = uid;
		return this;
	}

	public Integer getRid() {
		return rid;
	}

	public Userrole setRid(Integer rid) {
		this.rid = rid;
		return this;
	}

	public static final String ID = "id";

	public static final String UID = "uid";

	public static final String RID = "rid";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Userrole{" +
			", id=" + id +
			", uid=" + uid +
			", rid=" + rid +
			"}";
	}
}
