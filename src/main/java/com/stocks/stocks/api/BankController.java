package com.stocks.stocks.api;

import com.stocks.stocks.model.BankAccount;
import com.stocks.stocks.model.TransferRequest;
import com.stocks.stocks.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("bank")
@RestController
public class BankController {
    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/add")
    public void addBank(@RequestBody List<BankAccount> bankAccountList, HttpServletRequest request) {
        bankService.addBank(bankAccountList, request);
    }

    @PostMapping("/transferTo")
    public void transferToBank(@RequestBody TransferRequest transferRequest, HttpServletRequest request) {
        bankService.transferToBank(transferRequest, request);
    }

    @PostMapping("/transferFrom")
    public void transferFromBank(@RequestBody TransferRequest transferRequest, HttpServletRequest request) {
        bankService.transferFromBank(transferRequest, request);
    }
}
