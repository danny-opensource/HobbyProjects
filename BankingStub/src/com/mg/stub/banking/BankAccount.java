package com.mg.stub.banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BankAccount {

	private AccountType mAccountType;
	private String mAccountHolderName;
	private double mBalance;
	private List<Transaction> mPastTransactions;
	private long mBankAccountId;

	public BankAccount(AccountType accountType, String holderName) {
		mAccountHolderName = holderName;
		mAccountType = accountType;
		mPastTransactions = new ArrayList<Transaction>();
		mBankAccountId = new Random().nextLong();
	}
	
	public void withdraw(double amount)
	{
		Transaction withDrawTransaction = new Transaction();
		
	}

}
