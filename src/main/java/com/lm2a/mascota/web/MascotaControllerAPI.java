package com.lm2a.mascota.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lm2a.mascota.data.MascotaRepository;
import com.lm2a.mascota.model.Mascota;

@RestController  //Asume que todos los objetos tienen que estar en JSON
@RequestMapping(path="/rest", produces = "application/json")
public class MascotaControllerAPI {
	
	@Autowired
	private MascotaRepository mascotaRepo;
	
	@GetMapping("/{id}") 
	public ResponseEntity<Mascota> mascotaById(@PathVariable("id") Long id) {
		Optional<Mascota>optMascota = mascotaRepo.findById(id);
		if (optMascota.isPresent()) {
			return new ResponseEntity<>(optMascota.get(), HttpStatus.OK);  //Si todo va bien  nos devuelve un 200 OK al hacer GET
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);          //Si la mascota no existe
	}
	
	@PostMapping(path = "/mascota", consumes = "application/json" )  // Para meterle algo es /rest/mascota
	@ResponseStatus(HttpStatus.CREATED)                              // Con esto le indicamos que nos lo ha creado
	public Mascota postMascota(@RequestBody Mascota mascota) {       // Que saque del cuerpo de la request del objeto mascota. Transforma JSON a Objeto .java
		return mascotaRepo.save(mascota);                            // Pasa a persistencia
	}
	
	//Vamos a crear una peticion que me devuelva todas las mascotas en un GET
	@GetMapping("/todos")
	public Iterable<Mascota> getTodasMascotas(){
		return mascotaRepo.findAll(); 
	}
	
	//Vamos a crear una peticion que me devuelva las 20 mascotas por ID en un GET
	@GetMapping("/20jovenes")
	public Iterable<Mascota> getRecentMascotas(){
		PageRequest page = PageRequest.of(0, 20, Sort.by("fechaNac").descending());   //Ordenamos los mas jovenes hacia las mascotas mas viejas
		return mascotaRepo.findAll(page).getContent();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT) 		//Mensaje para indicar que el recurso ese ya no existe
	public void deleteMascota(@PathVariable("id") Long id) { //Con esto borramos la mascota con el id
		mascotaRepo.deleteById(id); 
	}
	
}
