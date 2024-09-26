package com.amblessed.universitymanagementsystem.service;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 23-Sep-24
 */

import com.amblessed.universitymanagementsystem.entity.Bank;
import com.amblessed.universitymanagementsystem.exception.AlreadyExistsException;
import com.amblessed.universitymanagementsystem.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    public Bank createBank(String sortCode, String bankName) {
        Optional<Bank> savedBank = bankRepository.findBySortCode(sortCode);
        if(savedBank.isPresent()) {
            throw new AlreadyExistsException(String.format("Bank with Sort Code %s already exists", sortCode));
        }
        Bank bank = new Bank(sortCode, bankName);
        return bankRepository.save(bank);
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }



}
