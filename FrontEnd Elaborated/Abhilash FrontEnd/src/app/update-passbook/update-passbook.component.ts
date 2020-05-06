import { Component, OnInit } from '@angular/core';
import { BankserviceService } from '../bankservice.service';
import { Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-update-passbook',
  templateUrl: './update-passbook.component.html',
  styleUrls: ['./update-passbook.component.css']
})
export class UpdatePassbookComponent implements OnInit {
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
    let resp=this.service.updatePassbook(this.accountId);
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
