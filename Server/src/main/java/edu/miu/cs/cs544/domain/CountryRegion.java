package edu.miu.cs.cs544.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Person.CountryRegion")
public class CountryRegion {
	
	@Id
	@Column(name = "CountryRegionCode")
	private String id;
	
	private String name;
	
	@OneToMany
	@JoinColumn(name = "countryRegionCode")
	private List<StateProvince> states;
	
}
