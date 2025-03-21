package imposto.imposto.repository;

import imposto.imposto.model.Imposto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpostoRepository extends JpaRepository<Imposto, Long> {
}
