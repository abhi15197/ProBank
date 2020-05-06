package com.cg.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction2 {

	private String transType;
	private String transOption;
	private double transAmount;
	private String transFrom;
	private String transTo;
	
}
