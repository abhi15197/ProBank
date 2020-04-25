package com.cg.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="account")
@DynamicUpdate(true)
@DynamicInsert(true)
@SequenceGenerator(name = "AccountIDGenerator",initialValue = 1000000000)
public class Account implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="account_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator ="AccountIDGenerator" )
	@NotNull(message="Account Number is Mandatory")
	private int accountId;
	
	@Column(name="account_holder_name")
	@NotNull(message="Account Holder Name is Mandatory")
	private String accountHolderName;
	
	@Column(name="account_branch_id")
	@NotNull(message="Account BranchID is Mandatory")
	private String accountBranchId;
	
	@Column(name="account_type")
	@NotNull(message="Account Type is Mandatory")
	private String accountType;
	
	@Column(name="account_status")
	@NotNull(message="Account Status is Mandatory")
	private String accountStatus;
	
	@Column(name="account_balance")
	@NotNull(message="Account Balance is Mandatory")
	@Min(value=1000L,message="Minimum Opening Balance should be 1000 INR")
	private double accountBalance;
	
	@Column(name="account_intrest")
	@Min(value=0L,message="Intreast cannot be Negative")
	private double accountIntrest;
	
	
	@OneToMany(targetEntity = Transaction.class,cascade=CascadeType.ALL,mappedBy = "account")
	@JsonIgnore
	private Set<Transaction> transactions=new HashSet<>();
}