package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String state;

    private String street;

    private int apartment;

}