package com.tenomatic.proyectos.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Libro {
	
	

	public Libro(Long idLibros, Long isbn, String nombre, Integer paginas, String editorial, String idioma, String anio) {
		this.idlibros = idLibros;
		this.isbn = isbn;
		this.nombre = nombre;
		this.paginas = paginas;
		this.editorial = editorial;
		this.idioma = idioma;
		this.anio = anio;
		
	}
	

	private Long idlibros;
	private Long isbn;
	private String nombre;
	private Integer paginas;
	private String editorial;
	private String idioma;
	private String anio;
	private Autor autor;

}
