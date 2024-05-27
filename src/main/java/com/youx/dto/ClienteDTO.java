package com.youx.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.youx.model.Venda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

	private Long id;

	@NotBlank
	private String nome;

	@NotBlank
	private String email;

	@NotBlank
	private String telefone;

	@NotBlank
	private String uf;

	@NotBlank
	private String localizacao;

}
