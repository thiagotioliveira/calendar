package com.thiagoti.challenge.xgeeks.calendar.api.integration;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String name;

  private PersonRole type;
}
