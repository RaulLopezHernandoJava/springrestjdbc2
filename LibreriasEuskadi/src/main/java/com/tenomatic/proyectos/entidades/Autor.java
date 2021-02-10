package com.tenomatic.proyectos.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Autor {
	
	private Long idautores;
	private String nombre;
	private String edad;
	private String descripcion;

}
