import { Account } from './account';
export class Transaction{
   
    transactionID:number;
    account:Account;
    transChequeId:string;
    transType:string;
    transOption:string;
    transAmount:number;
    transDate:Date;
    transFrom:string;
    transTo:string;
    transClosingBalance:number;

    constructor(transactionID:number,
        account:Account,
        transChequeId:string,
        transType:string,
        transOption:string,
        transAmount:number,
        transDate:Date,
        transFrom:string,
        transTo:string,
        transClosingBalance:number){
            this.transactionID=transactionID;
            this.account=account;
            this.transAmount=transAmount;
            this.transChequeId=transChequeId;
            this.transClosingBalance=transClosingBalance;
            this.transDate=transDate;
            this.transFrom=transFrom;
            this.transTo=transTo;
            this.transType=transType;
            this.transOption=transOption;
        }      
}