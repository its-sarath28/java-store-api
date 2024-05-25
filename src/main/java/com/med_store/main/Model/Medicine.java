package com.med_store.main.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Medicine name is required")
    @Size(min = 3, max = 15)
    @Column(name = "medicine_name")
    private String medicine_name;

    @NotNull(message = "Company name is required")
    @Size(min = 3, max = 15)
    @Column(name = "company_name")
    private String company_name;

    @NotNull(message = "Price is required")
    @Column(name = "price")
    private Float price;

    @NotNull(message = "Status is required")
    @Column(name = "status")
    private Boolean status;

    @NotNull(message = "Expiration date is required")
    @Column(name = "expiration_date")
    private Date expiration_date;
}
