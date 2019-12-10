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
public class Classification extends Model<Classification> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String name;


	public Integer getId() {
		return id;
	}

	public Classification setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Classification setName(String name) {
		this.name = name;
		return this;
	}

	public static final String ID = "id";

	public static final String NAME = "name";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Classification{" +
			", id=" + id +
			", name=" + name +
			"}";
	}
}
