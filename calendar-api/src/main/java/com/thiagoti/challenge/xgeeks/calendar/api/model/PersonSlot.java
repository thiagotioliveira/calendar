package com.thiagoti.challenge.xgeeks.calendar.api.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "person_slot")
@NoArgsConstructor
@AllArgsConstructor
public class PersonSlot implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @Column(name = "person_id")
  private String personId;

  @Column(name = "start_date")
  private Timestamp startDate;

  @Column(name = "end_date")
  private Timestamp endDate;
}
