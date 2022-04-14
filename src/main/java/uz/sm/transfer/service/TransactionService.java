package uz.sm.transfer.service;

import org.springframework.stereotype.Service;
import uz.sm.transfer.domains.Card;
import uz.sm.transfer.domains.Income;
import uz.sm.transfer.domains.Outcome;
import uz.sm.transfer.dto.TransferDto;
import uz.sm.transfer.repositories.IncomeRepository;
import uz.sm.transfer.repositories.OutcomeRepository;
import uz.sm.transfer.repositories.TransactionRepo;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public record TransactionService(TransactionRepo repository,
                                 IncomeRepository incomeRepository,
                                 OutcomeRepository outComeRepository) {

    private static final Integer companyCardId = 1;

    /**
     * @param dto Data transfer object
     * @return true if transaction done successfully
     */
    public boolean transferMoney(TransferDto dto) {
        Integer fromCardId = dto.fromCardId;
        Double amount = dto.amount;
        String toPan = dto.toPan;

        Card fromCard = getCardFromId(fromCardId);
        checkCardDetails(dto, fromCard);

        Double transaction = amount + amount * .01;
        Double balance = fromCard.getBalance() - transaction;
        fromCard.setBalance(balance);

        Card toCard = getCardFromPan(toPan);
        Double addedBalance = toCard.getBalance() + amount;
        toCard.setBalance(addedBalance);


        repository.save(fromCard);
        repository.save(toCard);

        Optional<Card> byId = repository.findById(companyCardId);
        Card card = byId.get();
        card.setBalance(card.getBalance() + (amount * 0.01));
        repository.save(card);

        incomeRepository.save(Income.builder()
                .amount(amount)
                .CardId(toCard.getId())
                .build());

        outComeRepository.save(Outcome.builder()
                .CardId(fromCardId)
                .amount(transaction)
                .build());

        return true;
    }

    private Card getCardFromPan(String toPan) {
        Optional<Card> cardByPan = repository.findCardByPan(toPan);
        return cardByPan.orElseThrow(() -> {
            throw new RuntimeException("Card not found");
        });
    }

    /**
     * @param fromCard from card should be non blocked or non expired
     */
    private void checkCardDetails(TransferDto dto, Card fromCard) {
        Double commission = dto.amount * 0.01;

        if (fromCard.isBlocked()
                || fromCard.getExpiry().isBefore(LocalDate.now())
                || fromCard.getBalance() < dto.amount + commission
                || !Objects.equals(dto.userID, fromCard.getOwner().getId())) {
            throw new RuntimeException("Bad request");
        }
    }

    private Card getCardFromId(Integer fromCardId) {
        Optional<Card> byId = repository.findById(fromCardId);

        return byId.orElseThrow(() -> {
            throw new RuntimeException("Card not found");
        });
    }
}
