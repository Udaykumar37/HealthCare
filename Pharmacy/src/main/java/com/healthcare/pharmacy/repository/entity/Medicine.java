package com.healthcare.pharmacy.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "MEDICINE")
public class Medicine {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private Double price;

  @Column(name = "description")
  private String description;
}
