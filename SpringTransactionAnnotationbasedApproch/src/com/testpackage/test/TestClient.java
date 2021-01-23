package com.testpackage.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.testpackage.dto.Account;
import com.testpackage.exception.InsufficientFundsException;
import com.testpackage.transaction.TransactionDao;

public class TestClient {
	
	public static void main(String[] args) throws InsufficientFundsException {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("com\\testpackage\\configuration\\applicationContext.xml");
		TransactionDao transactionDao=(TransactionDao) applicationContext.getBean("transactionDao");
		Account from=new Account();
		from.setName("STAVAN");
		Account to=new Account();
		to.setName("ARADHANA");
		transactionDao.transfer(from, to, 5000);
		
	
	}

}
