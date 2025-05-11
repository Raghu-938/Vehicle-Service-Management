package com.aits.VehicleServicesManagement.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aits.VehicleServicesManagement.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByEmail(String email);

}
