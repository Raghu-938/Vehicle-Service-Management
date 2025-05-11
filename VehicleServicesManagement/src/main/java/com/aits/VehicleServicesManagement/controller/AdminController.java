package com.aits.VehicleServicesManagement.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.aits.VehicleServicesManagement.entity.BillOfMaterial;
import com.aits.VehicleServicesManagement.entity.ProvidedServices;
import com.aits.VehicleServicesManagement.entity.ServiceAdvisor;
import com.aits.VehicleServicesManagement.entity.Service_Record;
import com.aits.VehicleServicesManagement.entity.TotalServices;
import com.aits.VehicleServicesManagement.repo.BillOfMaterialRepository;
import com.aits.VehicleServicesManagement.repo.ProvidedServicesRepository;
import com.aits.VehicleServicesManagement.repo.ServiceAdvisorRepository;
import com.aits.VehicleServicesManagement.repo.ServiceRecordRepository;
import com.aits.VehicleServicesManagement.repo.TotalServicesRepository;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    ProvidedServicesRepository psr;

    @Autowired
    ServiceAdvisorRepository sar;

    @Autowired
    TotalServicesRepository tsr;

    @Autowired
    ServiceRecordRepository srr;

    @Autowired
    BillOfMaterialRepository bom;

    // Get Due Vehicles between specified dates
    @GetMapping("/dueservices")
    public List<TotalServices> getDueVehiclesInWeek(
        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return tsr.findByReceivedDateBetweenAndStatus(startDate, endDate, "DUE");
    }

    // Get Services by status (Ongoing)
    @GetMapping("/ongoingservices/{status}")
    public List<TotalServices> getOngoing(@PathVariable String status) {
        return tsr.findByStatus(status);
    }

    // Get Services by status (Completed)
    @GetMapping("/completedservices/{status}")
    public List<TotalServices> getCompleted(@PathVariable String status) {
        return tsr.findByStatus(status);
    }

    // Assign Service to Service Advisor (PUT)
    @PutMapping("/assignforservicing/{Service_id}/{Advisor_id}")
    public TotalServices assignforAdvisor(@PathVariable Long Service_id, @PathVariable Long Advisor_id) {
        TotalServices service = tsr.findById(Service_id)
            .orElseThrow(() -> new RuntimeException("Service not found"));
        
        if (!"DUE".equals(service.getStatus())) {
            throw new RuntimeException("Service is not DUE, cannot assign advisor.");
        }

        ServiceAdvisor advisor = sar.findById(Advisor_id)
            .orElseThrow(() -> new RuntimeException("Advisor not found"));

        if (!"FREE".equals(advisor.getStatus())) {
            throw new RuntimeException("Advisor is not FREE, cannot assign to service.");
        }

        // Set scheduled date, advisor and change status
        service.setScheduled_date(LocalDate.now());
        service.setService_advisor(advisor);
        service.setStatus("ONGOING");

        // Update advisor status to OCCUPIED
        advisor.setStatus("OCCUPIED");
        sar.save(advisor);

        return tsr.save(service);
    }

    // Get Service Record by ID
    @GetMapping("/servicerecord/{id}")
    public Service_Record getservicerecord(@PathVariable Long id) {
        return srr.findById(id).orElseThrow(() -> new RuntimeException("Service Record not found"));
    }

    // CRUD for Provided Services
    @PostMapping("/addservices")
    public ProvidedServices insert(@RequestBody ProvidedServices ps) {
        return psr.save(ps);
    }

    // CRUD for Service Advisors
    @PostMapping("/addserviceadvisors")
    public ServiceAdvisor insert(@RequestBody ServiceAdvisor sa) {
        return sar.save(sa);
    }

    // CRUD for Total Services
    @PostMapping("/totalservices")
    public TotalServices insert(@RequestBody TotalServices ts) {
        return tsr.save(ts);
    }

    // Get All Provided Services
    @GetMapping("/getprovidedservices")
    public List<ProvidedServices> getallprovidedservices() {
        return psr.findAll();
    }

    // Get All Service Advisors
    @GetMapping("/getserviceadvisors")
    public List<ServiceAdvisor> getallserviceadvisors() {
        return sar.findAll();
    }

    // Delete Service Advisor by ID
    @DeleteMapping("/deleteserviceadvisor/{id}")
    public String deleteserviceadvisor(@PathVariable Long id) {
        if (sar.existsById(id)) {
            sar.deleteById(id);
            return "Deleted successfully";
        }
        return "ID not found";
    }

    // Delete Provided Services with zero quantity
    @DeleteMapping("/deleteitems")
    public void deleteserviceitems() {
        List<ProvidedServices> services = psr.findAll();
        services.forEach(ps -> {
            if (ps.getQuantity() == 0) {
                psr.deleteById(ps.getId());
            }
        });
    }

    // Reduce Quantity in Provided Services after use
    @Transactional
    @PutMapping("/reducequantity")
    public void reducequantity() {
        List<BillOfMaterial> bills = bom.getAllBills();
        bills.forEach(bill -> {
            Long serviceId = bill.getService().getId();
            int qtyUsed = bill.getQuantity();
            ProvidedServices ps = psr.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Provided Service not found"));
            int updatedQty = ps.getQuantity() - qtyUsed;
            ps.setQuantity(updatedQty);
            psr.save(ps);
        });
    }
}
