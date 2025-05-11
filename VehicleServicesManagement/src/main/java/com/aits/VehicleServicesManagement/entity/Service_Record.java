package com.aits.VehicleServicesManagement.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Service_Record {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private TotalServices service;
	
	private double totalcost;
	
	private LocalDate completiondate; 
	
	@OneToMany(mappedBy="record")
	private List<BillOfMaterial> bill;
	
 public Service_Record() {
	 
 }

public Service_Record(Long id, TotalServices service, double totalcost, LocalDate completiondate) {
	super();
	this.id = id;
	this.service = service;
	this.totalcost = totalcost;
	this.completiondate = completiondate;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public TotalServices getService() {
	return service;
}

public void setService(TotalServices service) {
	this.service = service;
}

public double getTotalcost() {
	return totalcost;
}

public void setTotalcost(double totalcost) {
	this.totalcost = totalcost;
}

public LocalDate getCompletiondate() {
	return completiondate;
}

public void setCompletiondate(LocalDate completiondate) {
	this.completiondate = completiondate;
}
 
}
