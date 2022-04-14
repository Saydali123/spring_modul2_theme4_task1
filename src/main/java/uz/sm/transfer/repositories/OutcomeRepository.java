package uz.sm.transfer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sm.transfer.domains.Outcome;

public interface OutcomeRepository extends JpaRepository<Outcome, Integer> {

}
