package com.tenomatic.proyectos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tenomatic.proyectos.entidades.Autor;

import com.tenomatic.proyectos.servicios.AutorService;


@RestController
@RequestMapping("/api/autores")
public class AutorApiController {

	@Autowired
	private AutorService autorService;

	// Sacamos todas los autores de la base de datos
	@GetMapping
	public Iterable<Autor> listar() {
		return autorService.listar();

	}

	// Encontrar autor por id

	@GetMapping("{id}")
	public Autor obtenerPorId(@PathVariable Long id) {
		;
		return autorService.obtenerPorId(id);
	}

	// Insertar autor

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void agregar(@RequestBody Autor autor) {
		autorService.agregar(autor);
	}

	// Actualizar autor
	
	@PutMapping("{id}")
	public ResponseEntity<Autor> put(@PathVariable Long id, @RequestBody Autor autor) {
		// Si los ids coinciden
		if (id == autorService.obtenerPorId(id).getIdautores()) {
			// Si se devuelve el autor (Si el objetor autor existe)
			if (autorService.obtenerPorId(id) != null) {
				return new ResponseEntity<Autor>(autorService.guardar(autor), HttpStatus.OK);
			} else {
				return new ResponseEntity<Autor>(HttpStatus.NOT_FOUND);
			}

		} else {
			return new ResponseEntity<Autor>(HttpStatus.BAD_REQUEST);
		}
	}

	// Eliminar producto

	@DeleteMapping("{id}")
	public ResponseEntity<Autor> delete(@PathVariable Long id) {
		if (autorService.obtenerPorId(id) != null) {
			autorService.borrar(id);
			return new ResponseEntity<Autor>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Autor>(HttpStatus.NOT_FOUND);
		}
	}
}
