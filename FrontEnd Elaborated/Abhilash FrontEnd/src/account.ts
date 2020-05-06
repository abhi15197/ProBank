import { Transaction } from './transaction';

export class Account{
   
    accountId:string;
    accountHolderName:string;
    accountBranchId:string;
    accountType:string;
    accountStatus:string;
    accountBalance:number;
    accountIntrest:number;
    
    constructor(accountId:string,
                accountHolderName:string,
                accountBranchId:string,
                accountType:string,
                accountStatus:string,
                accountBalance:number,
                accountIntrest:number,
    ){
    this.accountHolderName=accountHolderName;
    this.accountId=accountId;
    this.accountBalance=accountBalance;
    this.accountBranchId=accountBranchId;
    this.accountType=accountType;
    this.accountStatus=accountStatus;
    this.accountIntrest=accountIntrest;
    
    }
}