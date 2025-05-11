package com.aits.VehicleServicesManagement.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class TotalServices {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String vehicleNo;
	private String customername;
	private LocalDate receivedDate; //on which date the vehicle is given to servicing by customer 
	private String status; //DUE ONGOING COMPLETED
	private LocalDate scheduled_date; // date assigned to the serviceadvisor
	
	@ManyToOne
	private ServiceAdvisor service_advisor;
	
  public TotalServices() {
	 
 }

public TotalServices(Long id, String vehicleNo, String customername, LocalDate receivedDate, String status,
		LocalDate scheduled_date, ServiceAdvisor service_advisor) {
	super();
	this.id = id;
	this.vehicleNo = vehicleNo;
	this.customername = customername;
	this.receivedDate = receivedDate;
	this.status = status;
	this.scheduled_date = scheduled_date;
	this.service_advisor = service_advisor;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getVehicleNo() {
	return vehicleNo;
}

public void setVehicleNo(String vehicleNo) {
	this.vehicleNo = vehicleNo;
}

public String getCustomername() {
	return customername;
}

public void setCustomername(String customername) {
	this.customername = customername;
}

public LocalDate getReceivedDate() {
	return receivedDate;
}

public void setReceivedDate(LocalDate receivedDate) {
	this.receivedDate = receivedDate;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public LocalDate getScheduled_date() {
	return scheduled_date;
}

public void setScheduled_date(LocalDate scheduled_date) {
	this.scheduled_date = scheduled_date;
}

public ServiceAdvisor getService_advisor() {
	return service_advisor;
}

public void setService_advisor(ServiceAdvisor service_advisor) {
	this.service_advisor = service_advisor;
}
 
}
