package com.endava.tx.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Employee implements Comparable<Employee> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "domain_name")
    private String domainName;

    @Column(name = "email")
    private String email;

    @Column(name = "join_date")
    private LocalDate joiningDate;

    @Override
    public int compareTo(final Employee other) {
        return domainName.compareTo(other.domainName);
    }
}
