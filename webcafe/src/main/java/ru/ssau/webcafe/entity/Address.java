package ru.ssau.webcafe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data public class Address {
    @Id
    private long id;

    private String state;

    private String street;

    private int apartment;

}