package uz.sm.transfer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.sm.transfer.dto.TransferDto;
import uz.sm.transfer.service.TransactionService;

@RestController
@RequestMapping(value = "/transfer")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferDto dto) {
        boolean b = service.transferMoney(dto);
        return ResponseEntity.ok(b);
    }

}
