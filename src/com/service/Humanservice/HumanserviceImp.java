package com.service.Humanservice;

import java.util.List;

import com.dao.humandao.IHuman;
import com.entity.Student;

public class HumanserviceImp implements IHumanservice {
	
	private IHuman human=null;
	
	

	public void setHuman(IHuman human) {
		this.human = human;
	}

	public boolean delete(String id) {
		// TODO Auto-generated method stub
		human.delete(id);
		return true;
	}

	public List findall() {
		// TODO Auto-generated method stub
		return human.findall();
	}

	public Object findhuman(String id) {
		// TODO Auto-generated method stub
		return (Student)human.findhuman(id);
	}

	public boolean save(String id, String name, int score, String bak) {
		// TODO Auto-generated method stub
		Student stu=new Student(id,name,score,bak);
		human.save(stu);
		return true;
	}

	public boolean update(String id, String name, int score, String bak) {
		// TODO Auto-generated method stub
		Student stu=new Student(id,name,score,bak);
		human.update(stu);
		return true;
	}

}
