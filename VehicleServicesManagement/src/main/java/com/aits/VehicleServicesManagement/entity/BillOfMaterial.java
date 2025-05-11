package com.aits.VehicleServicesManagement.entity;

import jakarta.persistence.*;

@Entity
public class BillOfMaterial {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="providedservice_id")
	private ProvidedServices service;
	
	@ManyToOne
	@JoinColumn(name="record_id")
	private Service_Record record;
	
	private int quantity;
	private double totalcost;
	
	public BillOfMaterial() {
		
	}

	public BillOfMaterial(Long id, ProvidedServices service, Service_Record record, int quantity, double totalcost) {
		super();
		this.id = id;
		this.service = service;
		this.record = record;
		this.quantity = quantity;
		this.totalcost = totalcost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProvidedServices getService() {
		return service;
	}

	public void setService(ProvidedServices service) {
		this.service = service;
	}

	public Service_Record getRecord() {
		return record;
	}

	public void setRecord(Service_Record record) {
		this.record = record;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(double totalcost) {
		this.totalcost = totalcost;
	}
	
	
	

}
