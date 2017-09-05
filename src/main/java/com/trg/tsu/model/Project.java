package com.trg.tsu.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

	@Entity
	@Table(name = "project")
	public class Project {
		private Long id;
	    private String projCode;
	    private String projName;
	    private String projDesc;
	    private String location;
	    private User projOwner;
	    private Date startDate;
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getProjCode() {
			return projCode;
		}
		public void setProjCode(String projCode) {
			this.projCode = projCode;
		}
		
		public String getProjName() {
			return projName;
		}
		public void setProjName(String projName) {
			this.projName = projName;
		}
		public String getProjDesc() {
			return projDesc;
		}
		public void setProjDesc(String projDesc) {
			this.projDesc = projDesc;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "owner_id", nullable = false)
		public User getProjOwner() {
			return projOwner;
		}
		public void setProjOwner(User projOwner) {
			this.projOwner = projOwner;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
	    
	    
}
