package com.tenomatic.proyectos.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenomatic.proyectos.entidades.Autor;
import com.tenomatic.proyectos.repositorios.Dao;

@Service
public class AutorServiceImpl implements AutorService {
	
	@Autowired
	private Dao<Autor> dao;
	

	@Override
	public Iterable<Autor> listar() {
		return dao.obtenerTodos();
	}

	@Override
	public Autor obtenerPorId(Long id) {
		return dao.obtenerPorId(id);
	}

	@Override
	public void agregar(Autor autor) {
		dao.insertar(autor);
		
	}

	@Override
	public Autor guardar(Autor autor) {
		return dao.editar(autor);
	}

	@Override
	public void borrar(Long id) {
		dao.borrar(id);
		
	}

	
}
