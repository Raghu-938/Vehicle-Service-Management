package com.aits.VehicleServicesManagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aits.VehicleServicesManagement.entity.Service_Record;

public interface ServiceRecordRepository extends JpaRepository<Service_Record, Long> {

}
