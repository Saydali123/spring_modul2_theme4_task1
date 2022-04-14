package uz.sm.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uz.sm.transfer.domains.AuthUser;
import uz.sm.transfer.domains.Card;
import uz.sm.transfer.repositories.AuthUserRepo;
import uz.sm.transfer.repositories.TransactionRepo;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
public class TransferApplication {


    final
    TransactionRepo repo;
    final
    AuthUserRepo userRepo;

    public TransferApplication(TransactionRepo repo, AuthUserRepo userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(TransferApplication.class, args);
    }

    @Bean
    @Transactional
    public void save() {
        AuthUser user = new AuthUser();
        user.setName("Saydali");
        AuthUser user1 = new AuthUser();
        user1.setName("Jonibek");

        Card card = new Card();
        card.setPan("123456789");
        card.setBalance(100000);
        card.setExpiry(LocalDate.now().plusYears(10));
        card.setOwner(user);

        Card card1 = new Card();
        card1.setPan("987654321");
        card1.setBalance(10000);
        card1.setExpiry(LocalDate.now().plusYears(10));
        card1.setOwner(user1);

        userRepo.saveAll(List.of(user, user1));
        repo.saveAll(List.of(card, card1));
    }


}
