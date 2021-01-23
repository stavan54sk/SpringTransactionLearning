package com.testpackage.transaction;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.testpackage.dto.Account;

public class TransactionDaoImpl implements TransactionDao {

	JdbcTemplate jdbcTemplate;
	PlatformTransactionManager platformTransactionManager;
	DataSource dataSource;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.platformTransactionManager = dataSourceTransactionManager;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}

	@Override
	public void transfer(Account from,Account to, int amount) {
		// TODO Auto-generated method stub
		debit(from, amount);
		credit(to, amount);

	}

	@Override
	public void debit(Account account, int amount) {
		// TODO Auto-generated method stub
		String SQL = "UPDATE TRANSACTION set BALANCE=BALANCE-? WHERE NAME=?";
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
		try {
			jdbcTemplate.update(SQL, new Object[] {amount, account.getName()});
			System.out.println("############Debitted successfully#################");
			platformTransactionManager.commit(transactionStatus);
		} catch (RuntimeException e) {
			System.out.println("Error in Debiting Account Balance, rolling back");
			platformTransactionManager.rollback(transactionStatus);
			throw e;
		}
	}

	@Override
	public void credit(Account account, int amount) {
		// TODO Auto-generated method stub
		String SQL = "UPDATE TRANSACTION set BALANCE=BALANCE+? WHERE NAME=?";
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
		try {
			jdbcTemplate.update(SQL, new Object[] {amount, account.getName() });
			System.out.println("############Credited successfully#################");
			platformTransactionManager.commit(transactionStatus);
		} catch (RuntimeException e) {
			System.out.println("Error in Crediting Account Balance, rolling back");
			platformTransactionManager.rollback(transactionStatus);
			throw e;
		}
	}

}
