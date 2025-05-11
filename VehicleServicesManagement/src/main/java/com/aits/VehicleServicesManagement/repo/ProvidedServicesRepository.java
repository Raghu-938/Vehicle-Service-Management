package com.aits.VehicleServicesManagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aits.VehicleServicesManagement.entity.ProvidedServices;
import com.aits.VehicleServicesManagement.entity.TotalServices;

public interface ProvidedServicesRepository extends JpaRepository<ProvidedServices, Long> {
	@Query(value="update provided_services set quantity=:qty where id=:id",nativeQuery=true)
	void UpdateQuantity(int qty,Long id);

}
