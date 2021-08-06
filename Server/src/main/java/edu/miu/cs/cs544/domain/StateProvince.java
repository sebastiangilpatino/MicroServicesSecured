package edu.miu.cs.cs544.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "Person.StateProvince")
public class StateProvince {
	
	@Id
	@Column(name = "StateProvinceID")
	@EqualsAndHashCode.Include
	private Long id;
	
	private String stateProvinceCode;
	
	private String name;
	
}
