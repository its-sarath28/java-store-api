package com.med_store.main.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.med_store.main.Model.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

}
