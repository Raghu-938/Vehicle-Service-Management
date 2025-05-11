package com.aits.VehicleServicesManagement.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aits.VehicleServicesManagement.entity.BillOfMaterial;
import com.aits.VehicleServicesManagement.entity.ProvidedServices;
import com.aits.VehicleServicesManagement.entity.ServiceAdvisor;
import com.aits.VehicleServicesManagement.entity.Service_Record;
import com.aits.VehicleServicesManagement.entity.TotalServices;
import com.aits.VehicleServicesManagement.repo.BillOfMaterialRepository;
import com.aits.VehicleServicesManagement.repo.ProvidedServicesRepository;
import com.aits.VehicleServicesManagement.repo.ServiceRecordRepository;
import com.aits.VehicleServicesManagement.repo.TotalServicesRepository;
import com.aits.VehicleServicesManagement.repo.ServiceAdvisorRepository;

@RestController
@RequestMapping("/ServiceAdvisor")
public class ServiceAdviosrController {

    @Autowired
    ProvidedServicesRepository psr;

    @Autowired
    TotalServicesRepository tsr;

    @Autowired
    ServiceRecordRepository srr;

    @Autowired
    BillOfMaterialRepository bom;

    @Autowired
    ServiceAdvisorRepository sar;  // Inject the ServiceAdvisorRepository

    // Endpoint to get assigned vehicle to the service advisor (ONGOING status)
    @GetMapping("/getvehicles/{id}")
    public List<TotalServices> getvehicles(@PathVariable Long id) {
        List<TotalServices> services = tsr.findByServiceAdvisorIdAndStatus(id, "ONGOING");
        return services;
    }

    // Endpoint to add service record and bill, update vehicle status to COMPLETED and advisor to FREE
    @PostMapping("/bill")
    public Service_Record addingintoservicerecord(@RequestParam Long service_id, 
                                                  @RequestParam List<Long> items_id, 
                                                  @RequestParam List<Integer> quantity) {
        // Retrieve the service based on service_id
        Optional<TotalServices> serviceOptional = tsr.findById(service_id);
        if (!serviceOptional.isPresent()) {
            throw new IllegalArgumentException("Service not found with ID: " + service_id);
        }

        TotalServices service = serviceOptional.get();
        Service_Record new_record = new Service_Record();
        new_record.setService(service); // Setting the service to the record
        srr.save(new_record); // Save the service record initially

        double totalcost = 0;
        int maxDays = 0;

        // Iterate over items to create bill entries
        for (int i = 0; i < items_id.size(); i++) {
            Long itemsId = items_id.get(i);
            int qty = quantity.get(i);

            BillOfMaterial bill = createbill(itemsId, qty, new_record);
            totalcost += bill.getTotalcost();

            // Calculate the maximum number of days to complete
            int daystocomplete = bill.getService().getDaystocomplete();
            if (daystocomplete > maxDays) {
                maxDays = daystocomplete;
            }
        }

        // Update the service record with total cost and completion date
        new_record.setTotalcost(totalcost);

        // Ensure the scheduled date is valid before adding days
        if (service.getScheduled_date() != null) {
            LocalDate completiondate = service.getScheduled_date().plusDays(maxDays);
            new_record.setCompletiondate(completiondate); // Setting the completion date
        }

        service.setStatus("COMPLETED"); // Marking the service as completed
        ServiceAdvisor advisor = service.getService_advisor();
        advisor.setStatus("FREE"); // Marking the advisor as free

        // Save the updated service record and status
        srr.save(new_record);
        tsr.save(service); // Save updated service status
        sar.save(advisor); // Save updated advisor status

        return new_record; 
    }

    // Helper method to create BillOfMaterial for a given item and quantity
    public BillOfMaterial createbill(Long id, int quantity, Service_Record new_record) {
        BillOfMaterial bill = new BillOfMaterial();
        Optional<ProvidedServices> ps = psr.findById(id);
        if (ps.isPresent()) {
            bill.setService(ps.get());
            bill.setRecord(new_record);
            bill.setQuantity(quantity);
            bill.setTotalcost(quantity * ps.get().getPrice());
            bom.save(bill);
        }
        return bill;
    }
}
