package com.aits.VehicleServicesManagement.entity;

import jakarta.persistence.*;

@Entity
public class ProvidedServices {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	private String servicename;
	
	private double price;
	
	private int quantity;
	
	private int daystocomplete;
	
	public ProvidedServices(){
		
	}

	public ProvidedServices(Long id, String servicename, double price, int quantity, int daystocomplete) {
		super();
		this.id = id;
		this.servicename = servicename;
		this.price = price;
		this.quantity = quantity;
		this.daystocomplete = daystocomplete;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getDaystocomplete() {
		return daystocomplete;
	}

	public void setDaystocomplete(int daystocomplete) {
		this.daystocomplete = daystocomplete;
	}
}
