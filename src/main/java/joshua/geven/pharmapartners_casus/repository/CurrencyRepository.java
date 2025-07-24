package joshua.geven.pharmapartners_casus.repository;


import joshua.geven.pharmapartners_casus.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Long> {

}
