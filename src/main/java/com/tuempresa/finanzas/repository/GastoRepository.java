package com.tuempresa.finanzas.repository;

import com.tuempresa.finanzas.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

    List<Gasto> findByUserIdOrderByFechaDesc(Long userId);

    List<Gasto> findByUserIdAndFechaBetweenOrderByFechaDesc(
            Long userId, LocalDate fechaInicio, LocalDate fechaFin
    );

    @Query("SELECT g.categoria, SUM(g.monto) FROM Gasto g " +
            "WHERE g.user.id = :userId GROUP BY g.categoria")
    List<Object[]> sumByCategoria(Long userId);
}