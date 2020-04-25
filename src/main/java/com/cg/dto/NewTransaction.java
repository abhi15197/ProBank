package com.cg.dto;
import com.cg.entity.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewTransaction {
	
	
	private Transaction transaction;

}
