package com.youx.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.youx.dto.ClienteDTO;
import com.youx.model.Cliente;
import com.youx.repository.ClienteRepository;
import com.youx.service.ClienteService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteService clienteService;

	private final ClienteRepository clienteRepository;

	@GetMapping
	public List<ClienteDTO> findAll() {

		List<ClienteDTO> result = clienteService.findAll();

		return result;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ClienteDTO salvar(@Valid @RequestBody ClienteDTO dto) {
		return clienteService.salvar(dto);
	}

	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Cliente> cadastar(@Valid @RequestBody Cliente cliente) {

		for (int pos = 0; pos < cliente.getVendas().size(); pos++) {

			cliente.getVendas().get(pos).setCliente(cliente);
		}

		Cliente clienteSalvo = clienteRepository.save(cliente);

		return new ResponseEntity<Cliente>(clienteSalvo, HttpStatus.OK);
	}

}
