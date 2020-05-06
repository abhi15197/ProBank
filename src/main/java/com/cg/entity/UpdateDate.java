package com.cg.entity;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

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
@Table(name="update_date")
@DynamicUpdate(true)
@DynamicInsert(true)
public class UpdateDate implements Serializable{
	
	private static final long serialVersionUID = -7268158108096232991L;

	@Id
	@Column(name="account_id")
	@NotNull(message="Account Number is Mandatory")
	@Length(min = 12, message = "The field must have 12 numbers")
	@Length(max = 12, message = "The field must have 12 numbers")
	private String accountId;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY,cascade =  CascadeType.ALL,mappedBy = "updateDate")
	private Account account;
	
	@Column(name="last_update_date")
	@NotNull(message="Date is Mandatory")
	private Date lastUpdateDate;

}
