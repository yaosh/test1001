package com.entity;

/**
 * student entity. @author MyEclipse Persistence Tools
 */

public class Student implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Integer score;
	private String bak;

	// Constructors

	public Student(String id) {
		super();
		this.id = id;
	}

	/** default constructor */
	public Student() {
	}

	/** full constructor */
	public Student(String id, String name, Integer score, String bak) {
		this.id = id;
		this.name = name;
		this.score = score;
		this.bak = bak;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getBak() {
		return this.bak;
	}

	public void setBak(String bak) {
		this.bak = bak;
	}

}