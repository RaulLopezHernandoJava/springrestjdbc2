package com.tenomatic.proyectos.repositorios;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.tenomatic.proyectos.entidades.Autor;

@Repository
public class AutoresDaoMySql implements Dao<Autor> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;

	// Obtener todos los autores

	public Iterable<Autor> obtenerTodos() {
		return jdbcTemplate.query("SELECT * FROM autores",
				(rs, rowNum) -> new Autor(rs.getLong("idautores"), rs.getString("nombre"),
						rs.getString("edad"), rs.getString("descripcion")));

	}

	// Obtener utor por Id 
	@Override
	public Autor obtenerPorId(Long id) {
		return jdbcTemplate.queryForObject("select * from autores a where a.idautores = ?",
				(rs, rowNum) -> new Autor(rs.getLong("a.idautores"), rs.getString("a.nombre"),
						rs.getString("a.edad"), rs.getString("a.descripcion")),
				id);

	}

	// Insertar autor
	@Override
	public Autor insertar(Autor autor) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("autores")
				.usingGeneratedKeyColumns("id");
		Map<String, Object> parameters = new HashMap<>(1);

		parameters.put("nombre", autor.getNombre());
		parameters.put("edad", autor.getEdad());
		parameters.put("descripcion", autor.getDescripcion());

		Long newId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
		autor.setIdautores(newId);

		return autor;
	}

	// Editar Autor
	
	@Override
	public Autor editar(Autor autor) {
		jdbcTemplate.update(
				"UPDATE autores SET nombre = ?, edad = ?, descripcion = ?  WHERE idautores  = ?",
				new Object[] { autor.getNombre(), autor.getEdad(), autor.getDescripcion(),
						 autor.getIdautores() });
		return autor;
	}

	// Eliminar Autor 
	@Override
	public void borrar(Long id) {
		jdbcTemplate.update("DELETE FROM autores WHERE idautores = ?", new Object[] { id });
	}


}
