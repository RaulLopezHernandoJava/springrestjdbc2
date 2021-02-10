package com.tenomatic.proyectos.servicios;

import com.tenomatic.proyectos.entidades.Autor;

public interface AutorService {

	Iterable<Autor> listar();

	Autor obtenerPorId(Long id);

	void agregar(Autor autor);

	Autor guardar(Autor autor);

	void borrar(Long id);
}
