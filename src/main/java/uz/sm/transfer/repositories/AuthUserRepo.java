package uz.sm.transfer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sm.transfer.domains.AuthUser;

import javax.persistence.criteria.CriteriaBuilder;

public interface AuthUserRepo extends JpaRepository<AuthUser, Integer> {
}
