package br.com.upa.estoque.repository;

import br.com.upa.estoque.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;

public interface MateriaRepository extends JpaRepository<Material, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from Material m where m.id = :id")
    Optional<Material> findByIdForUpdate(@Param("id") Long id);

    List<Material> findByNomeContainingIgnoreCase(String nome);


    List<Material> findAllByOrderByQuantidadeAsc();

    List<Material> findAllByOrderByQuantidadeDesc();
}
