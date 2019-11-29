package com.stocks.stocks.service;

import com.stocks.stocks.dao.BankDao;
import com.stocks.stocks.model.BankAccount;
import com.stocks.stocks.model.TransferRequest;
import org.bson.types.ObjectId;
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
        HttpSession session = request.getSession();
        if (session != null) {
            String userId = (String)session.getAttribute("USER_ID");
            if (userId != null)
                bankDao.addBank(bankAccountList, userId);
        }
    }

    public void transferToBank(TransferRequest transferRequest, HttpServletRequest request) {
        if (transferRequest != null) {
            HttpSession session = request.getSession();
            if (session != null) {
                String userId = (String) session.getAttribute("USER_ID");
                if (userId != null)
                    bankDao.transferToBank(transferRequest.getBankId(), transferRequest.getAmount(), userId);
            }
        }
    }

    public void transferFromBank(TransferRequest transferRequest, HttpServletRequest request) {
        if (transferRequest != null) {
            HttpSession session = request.getSession();
            if (session != null) {
                String userId = (String) session.getAttribute("USER_ID");
                if (userId != null)
                    bankDao.transferFromBank(transferRequest.getBankId(), transferRequest.getAmount(), userId);
            }
        }
    }
}
