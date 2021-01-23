package com.testpackage.transaction;

import com.testpackage.dto.Account;

public interface TransactionDao {

	public void transfer(Account from,Account to, int amount);
	
	public void debit(Account account,int amount);
	
	public void credit(Account account,int amount);

}
