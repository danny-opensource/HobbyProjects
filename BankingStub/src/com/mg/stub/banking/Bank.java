package com.mg.stub.banking;

import java.util.List;

public class Bank {

	private String mBankName;
	private int mBalance;
	private int mTransactionCount;

	private List<BankAccount> mBankAccountList;

	public Bank(String bankName) {
		mBankName = bankName;
	}
	
	

}
