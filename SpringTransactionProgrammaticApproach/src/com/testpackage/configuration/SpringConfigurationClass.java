package com.testpackage.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.testpackage.transaction.TransactionDao;
import com.testpackage.transaction.TransactionDaoImpl;

public class SpringConfigurationClass {
	
	@Bean
	public DriverManagerDataSource driverManagerDataSource() {
		DriverManagerDataSource driverManagerDataSource=new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/classicmodels");
		driverManagerDataSource.setPassword("root1234");
		driverManagerDataSource.setUsername("root");
		System.out.println("##########Configured DriverManagerDataSource##########");
		return driverManagerDataSource;
		
	}
	
	@Bean
	public TransactionDao transactionDao() {
		TransactionDaoImpl transactionDaoImpl=new TransactionDaoImpl();
		transactionDaoImpl.setDataSource(driverManagerDataSource());
		transactionDaoImpl.setDataSourceTransactionManager(dataSourceTransactionManager());
		System.out.println("##########Configured TransactionDao##########");
		return transactionDaoImpl;
	}
	
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager() {
		System.out.println("##########Configured DataSourceTransactionManager##########");
		return new DataSourceTransactionManager(driverManagerDataSource());
	}
	
	

}

