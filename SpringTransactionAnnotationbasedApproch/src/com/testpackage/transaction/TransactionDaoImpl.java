package com.testpackage.transaction;

import javax.management.RuntimeErrorException;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.testpackage.dto.Account;
import com.testpackage.exception.InsufficientFundsException;

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

	@Transactional(rollbackFor=InsufficientFundsException.class)
	@Override
	public void transfer(Account from,Account to, int amount) throws InsufficientFundsException {
		// TODO Auto-generated method stub
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
		try {
			debit(from, amount);
			credit(to, amount);
			dataSourceTransactionManager.commit(transactionStatus);
		} catch (Exception e) {
			dataSourceTransactionManager.rollback(transactionStatus);
		}
		
	}

	@Override
	public void debit(Account account, int amount) throws InsufficientFundsException {
		// TODO Auto-generated method stub
		String SQL = "UPDATE TRANSACTION set BALANCE=BALANCE-? WHERE NAME=?";
			jdbcTemplate.update(SQL, new Object[] {amount, account.getName()});
			System.out.println("############Debitted successfully#################");
			throw new InsufficientFundsException("INsufficient Funds Exception");
	}

	@Override
	public void credit(Account account, int amount) {
		// TODO Auto-generated method stub
		String SQL = "UPDATE TRANSACTION set BALANCE=BALANCE+? WHERE NAME=?";
			jdbcTemplate.update(SQL, new Object[] {amount, account.getName()});
			System.out.println("############Credited successfully#################");
			
	}

}
