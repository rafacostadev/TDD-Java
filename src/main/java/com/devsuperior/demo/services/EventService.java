package com.devsuperior.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.exceptions.ResourceNotFoundException;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;

	@Autowired
	private CityRepository cityRepository;

	@Transactional
	public EventDTO update(Long id, EventDTO eventDto) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado!");
		}
		Event event = repository.getReferenceById(id);
		event.setCity(cityRepository.getReferenceById(eventDto.getCityId()));
		event.setDate(eventDto.getDate());
		event.setName(eventDto.getName());
		event.setUrl(eventDto.getUrl());
		return new EventDTO(repository.save(event));
	}
}
