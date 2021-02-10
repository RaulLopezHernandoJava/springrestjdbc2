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

import com.tenomatic.proyectos.entidades.Libro;

import com.tenomatic.proyectos.repositorios.LibrosDaoMySql;

import com.tenomatic.proyectos.servicios.LibroService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/api/libros")
public class LibroApiController {

	@Autowired
	private LibroService libroService;
	
	@Autowired
	private LibrosDaoMySql metodosLibros;

	// Sacamos todas los libros de la base de datos
	@GetMapping
	public Iterable<Libro> listar() {
		return libroService.listar();

	}

	// Encontrar libro por id

	@GetMapping("{id}")
	public Libro obtenerPorId(@PathVariable Long id) {
		;
		return libroService.obtenerPorId(id);
	}

	// Insertar libro

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void agregar(@RequestBody Libro libro) {
		libroService.agregar(libro);
	}

	// Actualizar libro

	@PutMapping("{id}")
	public ResponseEntity<Libro> put(@PathVariable Long id, @RequestBody Libro libro) {
		// Si los ids coinciden
		if (id == libroService.obtenerPorId(id).getIdlibros()) {
			// Si se devuelve el libro (Si el objetor libro existe)
			if (libroService.obtenerPorId(id) != null) {
				log.info("Libro actualizado correctamente");
				return new ResponseEntity<Libro>(libroService.guardar(libro), HttpStatus.OK);
			} else {
				log.info("Libro no encontrado");
				return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
			}

		} else {
			return new ResponseEntity<Libro>(HttpStatus.BAD_REQUEST);
		}
	}

	// Eliminar libro

	@DeleteMapping("{id}")
	public ResponseEntity<Libro> delete(@PathVariable Long id) {
		if (libroService.obtenerPorId(id) != null) {
			log.info("Libro eliminado correctamente");
			libroService.borrar(id);
			return new ResponseEntity<Libro>(HttpStatus.NO_CONTENT);
		} else {
			log.info("No se ha encontrado ningun libro con esa id");
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	//Obtener listado de libros con su autor
	
		@GetMapping("/autor")
		public Iterable<Libro> listadoDeLibrosConSuAutor(){
			log.info("El listado de libros junto con su autor se realizo correctamente");
			return metodosLibros.listadoDeLibrosConSuAutor();
			
		}
	
	//Buscar libros por su nombre
		@GetMapping("/libro/{nombre}")
		public ResponseEntity<Libro> buscarLibrosPorSuNombre(@PathVariable String nombre) {
			if (metodosLibros.buscarLibrosPorSuNombre(nombre) ==null) {
				log.info("No se encontro ningun libro con ese nombre");
				return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
			}else {
				log.info("La base de datos arrojo un resultado para ese libro");
				return new ResponseEntity<Libro>(metodosLibros.buscarLibrosPorSuNombre(nombre), HttpStatus.OK);
				
			}
		
		}

}
