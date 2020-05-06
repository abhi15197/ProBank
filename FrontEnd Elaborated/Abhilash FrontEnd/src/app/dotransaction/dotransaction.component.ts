import { DialogComponent } from './../dialog/dialog.component';
import { Transaction2 } from 'src/transaction2';
import { BankserviceService } from './../bankservice.service';
import { Component, OnInit, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import {MatDialog,MatDialogConfig} from '@angular/material/dialog';
import { DialogData } from 'src/DialogData';


interface TransType {
  value: string;
  viewValue: string;
}
interface TransOption {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-dotransaction',
  templateUrl: './dotransaction.component.html',
  styleUrls: ['./dotransaction.component.css']
})
export class DotransactionComponent implements OnInit {

  transaction2: Transaction2=new Transaction2('','',0,'','');
  message:any;
  errormsg:string;
  accountId:string;
  
  transtypes: TransType[] = [
    {value: 'Debit', viewValue: 'Debit'},
    {value: 'Credit', viewValue: 'Credit'}
  ];

  transoptions: TransOption[] = [
    {value: 'Transfer', viewValue: 'Transfer'},
    {value: 'Cash', viewValue: 'Cash'}
  ];

 
    transferForm=new FormGroup({
    transfrom:new FormControl('',[Validators.required, Validators.maxLength(12),Validators.minLength(12)]),
    transto:new FormControl('',[Validators.required, Validators.maxLength(12),Validators.minLength(12)]),
    transtype:new FormControl('',[Validators.required]),
    transoption:new FormControl('',[Validators.required]),
    transamount:new FormControl('',[Validators.required, Validators.maxLength(15)])    
  });

  constructor(private service:BankserviceService,private location:Location,public dialog: MatDialog) { }

  ngOnInit(): void {
    
  }

  public hasError=(controlName:string,errorname:string)=>{
      return this.transferForm.controls[controlName].hasError(errorname);
  }

  public onCancel=()=>{
    this.location.back();
  }

  openDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.width='60%';
    console.log(this.errormsg);
    dialogConfig.data = {
      message:this.message,errormsg:this.errormsg
    };
    const dialogRef =this.dialog.open(DialogComponent,dialogConfig);

    dialogRef.afterClosed().subscribe(
      data => console.log("Dialog output:", data)
  );
  }

  

  public createTransaction(){
    
    if(this.transferForm.valid){
      this.accountId=this.transferForm.value.transfrom;
      this.transaction2.transFrom=this.transferForm.value.transfrom;
      this.transaction2.transAmount=this.transferForm.value.transamount;
      this.transaction2.transTo=this.transferForm.value.transto;
      this.transaction2.transOption=this.transferForm.value.transoption;
      this.transaction2.transType=this.transferForm.value.transtype;
      this.doTransaction();
  }
}
  

  public doTransaction(){

    let resp=this.service.doTransaction(this.transaction2,this.accountId);
    resp.subscribe((data)=>{console.log("data",data);
    this.message=data;
    this.errormsg=undefined; 
    this.openDialog();},
    error=>{console.log("error",error.error);
      this.errormsg=JSON.parse((error.error)).message;
      this.message=undefined
      this.openDialog();
    }
      );
    this.transferForm.reset();

      
  }

}

