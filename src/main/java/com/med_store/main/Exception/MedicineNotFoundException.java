package com.med_store.main.Exception;

public class MedicineNotFoundException extends RuntimeException {
    public MedicineNotFoundException(Integer id) {
        super("Medicine not found with id: " + id);
    }
}
