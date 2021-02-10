package com.tenomatic.proyectos.servicios;

import com.tenomatic.proyectos.entidades.Libro;

public interface LibroService {

	Iterable<Libro> listar();

	Libro obtenerPorId(Long id);

	void agregar(Libro libro);

	Libro guardar(Libro libro);

	void borrar(Long id);
}
