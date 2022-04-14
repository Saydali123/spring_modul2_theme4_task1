package uz.sm.transfer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sm.transfer.domains.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {
}
