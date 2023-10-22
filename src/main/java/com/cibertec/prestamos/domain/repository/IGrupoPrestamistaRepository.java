package com.cibertec.prestamos.domain.repository;

import com.cibertec.prestamos.domain.model.GrupoPrestamista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGrupoPrestamistaRepository extends JpaRepository<GrupoPrestamista, Integer> {
}
