package com.testpackage.transaction;

import javax.management.RuntimeErrorException;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.testpackage.dto.Account;

public class TransactionDaoImpl implements TransactionDao {

	JdbcTemplate jdbcTemplate;
	DataSourceTransactionManager dataSourceTransactionManager;
	DataSource dataSource;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}

	@Override
	public void transfer(Account from,Account to, int amount) {
		// TODO Auto-generated method stub
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
		try {			
		debit(from, amount);
		System.out.println(10/0);
		credit(to, amount);
		dataSourceTransactionManager.commit(transactionStatus);
		} catch (RuntimeException e) {
			System.out.println("Error in Debiting Account Balance, rolling back");
			dataSourceTransactionManager.rollback(transactionStatus);
			throw e;
		}

	}

	@Override
	public void debit(Account account, int amount) {
		// TODO Auto-generated method stub
		String SQL = "UPDATE TRANSACTION set BALANCE=BALANCE-? WHERE NAME=?";
			jdbcTemplate.update(SQL, new Object[] {amount, account.getName()});
			System.out.println("############Debitted successfully#################");
			
	}

	@Override
	public void credit(Account account, int amount) {
		// TODO Auto-generated method stub
		String SQL = "UPDATE TRANSACTION set BALANCE=BALANCE+? WHERE NAME=?";
			jdbcTemplate.update(SQL, new Object[] {amount, account.getName()});
			System.out.println("############Credited successfully#################");
			
	}

}
