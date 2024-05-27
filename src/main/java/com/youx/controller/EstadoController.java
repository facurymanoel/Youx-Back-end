package com.youx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youx.dto.EstadoDTO;
import com.youx.model.Estado;
import com.youx.repository.ClienteRepository;
import com.youx.repository.EstadoRepository;
import com.youx.service.ClienteService;
import com.youx.service.EstadoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController {

	private final EstadoService estadoService;

	@GetMapping(value = "/", produces = "application/json")
	public List<EstadoDTO> estados() {

		List<EstadoDTO> result = estadoService.findAll();
		return result;

	}

}
