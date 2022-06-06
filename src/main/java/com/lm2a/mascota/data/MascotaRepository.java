package com.lm2a.mascota.data;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.lm2a.mascota.model.Mascota;

public interface MascotaRepository extends PagingAndSortingRepository<Mascota, Long> {

}
