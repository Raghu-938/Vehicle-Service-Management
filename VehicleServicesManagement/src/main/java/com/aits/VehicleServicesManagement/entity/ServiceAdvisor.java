package com.aits.VehicleServicesManagement.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class ServiceAdvisor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String advisorname;
	private String status;
	
	@JsonIgnore
	@OneToMany(mappedBy="service_advisor" ,cascade=CascadeType.ALL)
	private List<TotalServices> totalservices;
	
	public ServiceAdvisor() {
		
	}

	public ServiceAdvisor(Long id, String advisorname, String status,List<TotalServices> totalservices) {
		super();
		this.id = id;
		this.advisorname = advisorname;
		this.status=status;
		this.totalservices = totalservices;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdvisorname() {
		return advisorname;
	}

	public void setAdvisorname(String advisorname) {
		this.advisorname = advisorname;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TotalServices> getTotalservices() {
		return totalservices;
	}

	public void setTotalservices(List<TotalServices> totalservices) {
		this.totalservices = totalservices;
	}
	
}
