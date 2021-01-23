package com.testpackage.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.testpackage.configuration.SpringConfigurationClass;
import com.testpackage.dto.Account;
import com.testpackage.transaction.TransactionDao;

public class TestClient {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringConfigurationClass.class);
		TransactionDao transactionDao=(TransactionDao) applicationContext.getBean("transactionDao");
		Account from=new Account();
		from.setName("dfdfs");
		Account to=new Account();
		to.setName("STAVAN");
		transactionDao.transfer(from, to, 5000);
		
	
	}

}
