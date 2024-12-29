package com.devsuperior.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.exceptions.DatabaseException;
import com.devsuperior.demo.exceptions.ResourceNotFoundException;
import com.devsuperior.demo.repositories.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository repository;

	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		return repository.findAll(Sort.by("name")).stream().map(x -> new CityDTO(x)).toList();
	}

	@Transactional
	public CityDTO insert(CityDTO cityDto) {
		City city = new City();
		city.setName(cityDto.getName());
		return new CityDTO(repository.save(city));
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado!");
		}
		try {
		repository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha na integridade referencial!");
		}
	}
}
