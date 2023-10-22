package ru.ssau.webcaffe.service;

import lombok.Getter;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class RepositoryService<T, ID> {
    private JpaRepository<T, ID> repository;
    public RepositoryService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T getById(ID id) {
        return repository.getReferenceById(id);
    }
}
