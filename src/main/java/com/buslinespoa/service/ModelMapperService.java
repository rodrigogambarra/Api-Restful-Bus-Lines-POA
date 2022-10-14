package com.buslinespoa.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ModelMapperService {

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
}
