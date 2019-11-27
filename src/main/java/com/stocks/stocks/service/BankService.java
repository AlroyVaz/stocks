package com.stocks.stocks.service;

import com.stocks.stocks.dao.BankDao;
import com.stocks.stocks.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class BankService {

    private final BankDao bankDao;

    @Autowired
    public BankService(@Qualifier("bankDao") BankDao bankDao) {
        this.bankDao = bankDao;
    }

    public void addBank(List<BankAccount> bankAccountList, HttpServletRequest request) {
        if (bankAccountList.size() > 0) {
            HttpSession session = request.getSession();
            if (session != null) {
                Object username = session.getAttribute("USERNAME");
                if (username != null)
                    bankDao.addBank(username.toString(), bankAccountList);
            }
        }
    }

    public void transferToBank(BankAccount bankAccount, double amount, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Object username = session.getAttribute("USERNAME");
            if (username != null)
                bankDao.transferToBank(username.toString(), bankAccount, amount);
        }
    }

    public boolean transferFromBank(BankAccount bankAccount, double amount, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Object username = session.getAttribute("USERNAME");
            if (username != null)
                return bankDao.transferFromBank(username.toString(), bankAccount, amount);
        }
        return false;
    }
}
