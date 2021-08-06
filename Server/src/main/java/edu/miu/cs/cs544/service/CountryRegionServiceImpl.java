package edu.miu.cs.cs544.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.miu.cs.cs544.domain.CountryRegion;
import edu.miu.cs.cs544.repository.CountryRegionRepository;

@Service
@Transactional
public class CountryRegionServiceImpl implements CountryRegionService {
	
	@Autowired
	private CountryRegionRepository repository;

	@Override
	public List<CountryRegion> findAll() {
		List<CountryRegion> countries = repository.findAll();
		Collections.sort(countries, Comparator.comparing(CountryRegion::getName));
		
		return countries;
	}

	@Override
	public CountryRegion findById(String id) {
		return repository.findById(id).get();
	}

}
