package com.dao.humandao;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.entity.Student;

public class HumanImp implements IHuman {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;
	

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public boolean delete(Object o) {
		sqlmapclienttemplate.delete("deleteStu",o.toString());
		return true;
	}

	@SuppressWarnings("unchecked")
    public List<Student> findall() {
		return (List<Student>)sqlmapclienttemplate.queryForList("getAllStu",null);
	}

	public Object findhuman(Object o) {
		return sqlmapclienttemplate.queryForObject("getStu",o.toString());
	}

	public boolean save(Object o) {
		sqlmapclienttemplate.insert("insertStu",o);
		return true;
	}

	public boolean update(Object o) {
		sqlmapclienttemplate.update("updateStu",o);
		return true;
	}

}
