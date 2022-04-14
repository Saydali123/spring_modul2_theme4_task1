package uz.sm.transfer.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.sm.transfer.domains.Card;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.concurrent.CancellationException;

public interface TransactionRepo extends JpaRepository<Card, Integer> {


    Optional<Card> findCardByPan(String toPan);
}
