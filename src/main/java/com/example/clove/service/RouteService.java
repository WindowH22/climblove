package com.example.clove.service;

import com.example.clove.domain.Route;
import com.example.clove.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteService {
    
    private final RouteRepository routeRepository;
    
    public Route findById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Route not found with id: " + id));
    }
}
