package com.tenomatic.proyectos.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenomatic.proyectos.entidades.Libro;
import com.tenomatic.proyectos.repositorios.Dao;

@Service
public class LibroServiceImpl implements LibroService {

	@Autowired
	private Dao<Libro> dao;

	@Override
	public Iterable<Libro> listar() {
		return dao.obtenerTodos();
	}

	@Override
	public Libro obtenerPorId(Long id) {
		return dao.obtenerPorId(id);
	}

	@Override
	public void agregar(Libro libro) {
		dao.insertar(libro);

	}

	@Override
	public Libro guardar(Libro libro) {
		return dao.editar(libro);
	}

	@Override
	public void borrar(Long id) {
		dao.borrar(id);

	}

}
