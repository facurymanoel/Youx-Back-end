package com.youx.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youx.dto.ClienteDTO;
import com.youx.model.Cliente;
import com.youx.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClienteService implements UserDetailsService {

	private final ClienteRepository clienteRepository;

	public List<ClienteDTO> findAll() {

		List<Cliente> result = clienteRepository.findAll();

		return result.stream().map(x -> new ModelMapper().map(x, ClienteDTO.class)).collect(Collectors.toList());

	}

	@Transactional
	public ClienteDTO salvar(ClienteDTO clienteDTO) {

		ModelMapper mapper = new ModelMapper();
		Cliente cliente = mapper.map(clienteDTO, Cliente.class);
		cliente = clienteRepository.save(cliente);
		clienteDTO.setId(cliente.getId());
		return clienteDTO;

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Cliente cliente = clienteRepository.findUserByLogin(username);

		if (cliente == null) {

			throw new UsernameNotFoundException("Cliente não foi encontrado!");
		}

		return new User(cliente.getLogin(), cliente.getPassword(), cliente.getAuthorities());
	}

}
