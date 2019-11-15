package com.example.demo.recursos;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Evento;

@RestController
public class TesteRepository{

	@Autowired
	private EntityManager em;
	

	
	@RequestMapping(value="/teste", method=RequestMethod.GET)
	public ResponseEntity<Evento> getStudentByName() {
		Criteria crit = em.unwrap(Session.class).createCriteria(Evento.class);
        crit.add(Restrictions.like("nome", "show zé ramalho"));
        List<Evento> list = crit.list();
        return ResponseEntity.ok().body(list.get(0));
				
		
	}


}
