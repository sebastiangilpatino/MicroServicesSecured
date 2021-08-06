package edu.miu.cs.cs544.service;

import java.util.List;

import edu.miu.cs.cs544.domain.CountryRegion;

public interface CountryRegionService {
	
	public CountryRegion findById(String id);

	public List<CountryRegion> findAll();

}
