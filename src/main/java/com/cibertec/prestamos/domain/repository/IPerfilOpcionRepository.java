package com.cibertec.prestamos.domain.repository;

import com.cibertec.prestamos.domain.model.PerfilOpcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPerfilOpcionRepository extends JpaRepository<PerfilOpcion, Integer> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
