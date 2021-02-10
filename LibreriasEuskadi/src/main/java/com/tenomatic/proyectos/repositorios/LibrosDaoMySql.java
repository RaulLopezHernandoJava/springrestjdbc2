package com.tenomatic.proyectos.repositorios;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.tenomatic.proyectos.entidades.Autor;
import com.tenomatic.proyectos.entidades.Libro;

@Repository
public class LibrosDaoMySql implements Dao<Libro> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;

	// Obtener todos los libros

	public Iterable<Libro> obtenerTodos() {
		return jdbcTemplate.query("SELECT * FROM libros",
				(rs, rowNum) -> new Libro(rs.getLong("idlibros"), rs.getLong("isbn"), rs.getString("nombre"),
						rs.getInt("paginas"), rs.getString("editorial"), rs.getString("idioma"),
						rs.getString("anioEdicion")));

	}

	// Obtener libro por Id
	@Override
	public Libro obtenerPorId(Long id) {
		return jdbcTemplate.queryForObject("select * from libros l where l.idlibros = ?",
				(rs, rowNum) -> new Libro(rs.getLong("l.idlibros"), rs.getLong("l.isbn"), rs.getString("l.nombre"),
						rs.getInt("l.paginas"), rs.getString("l.editorial"), rs.getString("l.idioma"),
						rs.getString("l.anioEdicion")),
				id);

	}

	// Insertar libro

	@Override
	public Libro insertar(Libro libro) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("libros")
				.usingGeneratedKeyColumns("id");
		Map<String, Object> parameters = new HashMap<>(1);

		parameters.put("isbn", libro.getIsbn());
		parameters.put("nombre", libro.getNombre());
		parameters.put("paginas", libro.getPaginas());
		parameters.put("editorial", libro.getEditorial());
		parameters.put("idioma", libro.getIdioma());
		parameters.put("anioEdicion", libro.getAnio());

		Long newId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
		libro.setIdlibros(newId);

		return libro;
	}

	// Editar Libro

	@Override
	public Libro editar(Libro libro) {
		jdbcTemplate.update(
				"UPDATE libros SET isbn = ?, nombre = ?, paginas = ? , editorial= ?, idioma = ? , anioEdicion = ?  WHERE idlibros  = ?",
				new Object[] { libro.getIsbn(), libro.getNombre(),libro.getPaginas(), libro.getEditorial(), libro.getIdioma(),
						libro.getAnio(), libro.getIdlibros() });
		return libro;
	}

	// Eliminar Libro
	@Override
	public void borrar(Long id) {
		jdbcTemplate.update("DELETE FROM libros WHERE idlibros = ?", new Object[] { id });
	}

	// Listado de libros con su autor

	public Iterable<Libro> listadoDeLibrosConSuAutor() {
		return jdbcTemplate.query(
				"SELECT l.*,a.* FROM libros l JOIN autores_has_libros au ON l.idlibros = au.libros_idlibros JOIN autores a ON a.idautores = au.autores_idautores",
				(rs, rowNum) -> {
					Autor autor = new Autor(rs.getLong("a.idautores"), rs.getString("a.nombre"), rs.getString("a.edad"),
							rs.getString("a.descripcion"));
					Libro libro = new Libro(rs.getLong("l.idlibros"), rs.getLong("l.isbn"), rs.getString("l.nombre"),
							rs.getInt("l.paginas"), rs.getString("l.editorial"), rs.getString("l.idioma"),
							rs.getString("l.anioEdicion"), autor);
					return libro;
				});

	}

	// Buscar libros por su nombre

	public Libro buscarLibrosPorSuNombre(String nombreLibro) {
		try {
			return jdbcTemplate.queryForObject("select * from libros l where l.nombre= ?",
					(rs, rowNum) -> new Libro(rs.getLong("l.idlibros"), rs.getLong("l.isbn"), rs.getString("l.nombre"),
							rs.getInt("l.paginas"), rs.getString("l.editorial"), rs.getString("l.idioma"),
							rs.getString("l.anioEdicion")),
					nombreLibro);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

}
