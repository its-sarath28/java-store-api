package com.med_store.main.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med_store.main.Exception.MedicineNotFoundException;
import com.med_store.main.Model.Medicine;
import com.med_store.main.Repository.MedicineRepository;
import com.med_store.main.Response.MedicineResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicines")
@Validated
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @PostMapping("/create-medicine")
    public MedicineResponse createMedicine(@Valid @RequestBody Medicine medicine) {
        medicineRepository.save(medicine);
        return new MedicineResponse("Medicine created successfully");
    }

    @GetMapping("")
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    @GetMapping("/{medicineId}")
    public Medicine getMedicineById(@PathVariable Integer medicineId) {
        return medicineRepository.findById(medicineId).orElseThrow(() -> new MedicineNotFoundException(medicineId));
    }

    @PutMapping("/update-medicine/{medicineId}")
    public MedicineResponse updateMedicine(@Valid @PathVariable Integer medicineId, @RequestBody Medicine medicine) {
        medicineRepository.findById(medicineId).map(existingMedicine -> {
            existingMedicine.setMedicine_name(medicine.getMedicine_name());
            existingMedicine.setCompany_name(medicine.getCompany_name());
            existingMedicine.setPrice(medicine.getPrice());
            existingMedicine.setExpiration_date(medicine.getExpiration_date());
            existingMedicine.setStatus(medicine.getStatus());
            return medicineRepository.save(existingMedicine);
        }).orElseThrow(() -> new MedicineNotFoundException(medicineId));

        return new MedicineResponse("Medicine updated successfully");
    }

    @DeleteMapping("/delete-medicine/{medicineId}")
    public MedicineResponse deleteMedicine(@PathVariable Integer medicineId) {
        if (!medicineRepository.existsById(medicineId)) {
            throw new MedicineNotFoundException(medicineId);
        }

        medicineRepository.deleteById(medicineId);

        return new MedicineResponse("Medicine deleted successfully");
    }
}
