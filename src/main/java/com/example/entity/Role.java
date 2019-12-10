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
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String name;
    /**
     * 流量额度M
     */
	private String line;


	public Integer getId() {
		return id;
	}

	public Role setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Role setName(String name) {
		this.name = name;
		return this;
	}

	public String getLine() {
		return line;
	}

	public Role setLine(String line) {
		this.line = line;
		return this;
	}

	public static final String ID = "id";

	public static final String NAME = "name";

	public static final String LINE = "line";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Role{" +
			", id=" + id +
			", name=" + name +
			", line=" + line +
			"}";
	}
}
