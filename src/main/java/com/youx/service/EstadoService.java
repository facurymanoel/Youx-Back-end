package com.youx.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.youx.dto.EstadoDTO;
import com.youx.model.Estado;
import com.youx.repository.ClienteRepository;
import com.youx.repository.EstadoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EstadoService {
	
	private final EstadoRepository estadoRepository;
	
	public List<EstadoDTO> findAll(){
		 
		  List<Estado> result = estadoRepository.findAll();
		  
		  return result.stream()
				       .map(x -> new ModelMapper()
				        .map(x,  EstadoDTO.class))
				        .collect(Collectors.toList());
	}

}
