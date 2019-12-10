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
 * @since 2019-11-13
 */
public class Test extends Model<Test> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String phone;
	private String pwd;



	public Integer getId() {
		return id;
	}

	public Test setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public Test setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getPwd() {
		return pwd;
	}

	public Test setPwd(String pwd) {
		this.pwd = pwd;
		return this;
	}





	public static final String ID = "id";

	public static final String PHONE = "phone";

	public static final String PWD = "pwd";



	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Test{" +
			", id=" + id +
			", phone=" + phone +
			", pwd=" + pwd +
			"}";
	}
}
