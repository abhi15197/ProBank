package com.cg.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="transaction")

@DynamicUpdate(true)
@DynamicInsert(true)
@SequenceGenerator(name = "transactionIDGenerator",initialValue = 100000)
public class Transaction implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull(message="Transaction ID is Mandatory")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator ="transactionIDGenerator" )
	@Column(name="transaction_id")
	private int transactionID;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="account_id")
	@NotNull(message="Account is Mandatory for Transaction")
	private Account account;
	
	@Column(name="transaction_type")
	@NotNull(message="Transaction Type is Mandatory")
	private String transType;
	
	@Column(name="transaction_Amount")
	@NotNull(message="Transaction Amount is Mandatory")
	@Min(value=1L,message="Minimum Amount should be 1.00 INR")
	private double transAmount;
	
	@Column(name="transaction_Option")
	@NotNull(message="Transaction Option is Mandatory")
	private String transOption;
	
	@Column(name="transaction_Date")
	@NotNull(message="Transaction Date is Mandatory")
	private Date transDate;
	
	@Column(name="transaction_cheque_id")
	private String transChequeId;
	
	@Column(name="transaction_From")
	@NotNull(message="Transaction From is Mandatory")
	private String transFrom;
	
	@Column(name="transaction_to")
	@NotNull(message="Transaction To is Mandatory")
	private String transTo;
	
	@Column(name="transaction_closing_balance")
	@NotNull(message="Closing Balance is Mandatory")
	@Min(value=1000L,message="Minimum Reamining Balance should be 1000.00 INR")
	private double transClosingBalance;
}
