package com.sosoburger.toaster.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
@Entity
@Table(name = "roles",uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class RoleDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserDAO> users;
}
