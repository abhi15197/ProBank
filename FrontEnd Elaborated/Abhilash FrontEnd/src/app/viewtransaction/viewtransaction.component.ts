import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { BankserviceService } from '../bankservice.service';

@Component({
  selector: 'app-viewtransaction',
  templateUrl: './viewtransaction.component.html',
  styleUrls: ['./viewtransaction.component.css']
})
export class ViewtransactionComponent implements OnInit {
  
 transaction:any;
  accountId:number;
  errormsg:string;
  vAccountID=new FormControl('',[Validators.required]);


  constructor(private service :BankserviceService) { }

  getErrorMessage(){
    return this.vAccountID.hasError('required') ?
    'you must enter the account number' : '';
  }

  public getTransaction(){
    let resp=this.service.getTransactions(this.accountId);
    resp.subscribe((data)=>{console.log("data",data);
    this.transaction=data;
    this.errormsg=undefined; },
    error=>{console.log("error",error.error);
      this.errormsg=JSON.parse(JSON.stringify(error.error)).message;
      this.transaction=[]});
    }

  ngOnInit(): void {
  }

}
