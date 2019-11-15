package com.example.demo.repostory;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Evento;


public interface EventoRepository extends CrudRepository<Evento, String>{
		
	
	Evento findByCodigo(Long codigo);
}
