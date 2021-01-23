package com.testpackage.transaction;

import com.testpackage.dto.Account;
import com.testpackage.exception.InsufficientFundsException;

public interface TransactionDao {

	public void transfer(Account from,Account to, int amount) throws InsufficientFundsException;
	
	public void debit(Account account,int amount) throws InsufficientFundsException;
	
	public void credit(Account account,int amount);

}
