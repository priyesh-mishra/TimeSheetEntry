package com.trg.tsu.model;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "TSUMaster")
public class TimeSheet {
	private Long id;
    private Long empId;
    private Date date;
    private String projectCode;
    private String taskDescription;
    private String fileName;
    private Double loggedHours;
    private String remarks;
    private Long supCode;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public Double getLoggedHours() {
		return loggedHours;
	}

	public void setLoggedHours(Double loggedHours) {
		this.loggedHours = loggedHours;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getSupCode() {
		return supCode;
	}

	public void setSupCode(Long supCode) {
		this.supCode = supCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
}
