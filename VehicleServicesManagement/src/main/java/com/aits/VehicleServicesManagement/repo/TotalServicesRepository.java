package com.aits.VehicleServicesManagement.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aits.VehicleServicesManagement.entity.TotalServices;

public interface TotalServicesRepository extends JpaRepository<TotalServices , Long> {
	 
	List<TotalServices> findByStatus(String status); 
	
	@Query(value = "SELECT * FROM total_services WHERE service_advisor_id = :id", nativeQuery = true)
	TotalServices findByServiceAdvisorId(@Param("id") Long id);
	
	@Query("SELECT t FROM TotalServices t WHERE t.receivedDate BETWEEN :start AND :end AND t.status = :status")
	List<TotalServices> findByReceivedDateBetweenAndStatus(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("status") String status);

	
	List<TotalServices> findByServiceAdvisorIdAndStatus(Long serviceAdvisorId, String status);




}
