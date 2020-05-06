import { Account } from './../../account';
import { BankserviceService } from './../bankservice.service';
import { Component, OnInit } from '@angular/core';
import { FormControl,Validators } from '@angular/forms';


@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {


  account:any;
  accountId:number;
  errormsg:string;
  vAccountID=new FormControl('',[Validators.required]);

  constructor(private service :BankserviceService) { }

  getErrorMessage(){
    return this.vAccountID.hasError('required') ?
    'you must enter the account number' : '';
  }

    public getAccount(){
      
    let resp=this.service.getAccount(this.accountId);
    resp.subscribe((data)=>{console.log("data",data);
    this.account=data;
    this.errormsg=undefined;
   },
    error=>{console.log("error",error.error);
      this.errormsg=JSON.parse(JSON.stringify(error.error)).message;
      this.account=undefined});

    }
    
  ngOnInit(): void {
  }
}
