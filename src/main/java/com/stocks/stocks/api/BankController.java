package com.stocks.stocks.api;

import com.stocks.stocks.model.BankAccount;
import com.stocks.stocks.model.BankAccountWrapper;
import com.stocks.stocks.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("bank")
@RestController
public class BankController {
    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/add")
    public void addBank(@RequestBody BankAccountWrapper bankAccountWrapper, HttpServletRequest request) {
        bankService.addBank(bankAccountWrapper.getBankAccountList(), request);
    }

    @PostMapping("/transferTo")
    public void transferToBank(@RequestParam("bankAccount") BankAccount bankAccount, @RequestParam("amount") double amount, HttpServletRequest request) {
        bankService.transferToBank(bankAccount, amount, request);
    }

    @PostMapping("/transferFrom")
    public boolean transferFromBank(@RequestParam("bankAccount") BankAccount bankAccount, @RequestParam("amount") double amount, HttpServletRequest request) {
        return bankService.transferFromBank(bankAccount, amount, request);
    }
}
