package com.endava.tx.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.endava.tx.domain.Employee;

@Repository
public class EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Employee save(final Employee employee) {
        entityManager.persist(employee);
        return employee;
    }

    public Iterable<Employee> saveAll(final Iterable<Employee> iterable) {
        iterable.forEach(this::save);
        return iterable;
    }

    public Optional<Employee> findById(final long id) {
        return Optional.ofNullable(entityManager.find(Employee.class, id));
    }

    public Iterable<Employee> findAll() {
        return entityManager.createQuery("select e from Employee e", Employee.class).getResultList();
    }

    public void delete(final Employee employee) {
        entityManager.remove(employee);
    }

    public void deleteAll() {
        entityManager.createQuery("delete from Employee e").executeUpdate();
    }
}
