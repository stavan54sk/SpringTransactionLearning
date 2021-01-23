package com.testpackage.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.testpackage.dto.Account;
import com.testpackage.transaction.TransactionDao;

public class TestClient {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("com\\testpackage\\configuration\\applicationContext.xml");
		TransactionDao transactionDao=(TransactionDao) applicationContext.getBean("transactionDao");
		Account from=new Account();
		from.setName("TEST2");
		Account to=new Account();
		to.setName("TEST1");
		transactionDao.transfer(from, to, 5000);
		
	
	}

}
