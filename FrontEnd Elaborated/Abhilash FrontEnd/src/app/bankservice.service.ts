import { Account } from './../account';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transaction2 } from 'src/transaction2';

@Injectable({
  providedIn: 'root'
})
export class BankserviceService {

  constructor(private http:HttpClient) { }

  public doTransaction(transaction,accountId):Observable<any>{
    return this.http.post("http://127.0.0.1:9090/"+accountId+"/newTransaction",transaction,{responseType:'text' as 'json'});
  }

  public getAccount(accountId):Observable<any>{
    return this.http.get("http://127.0.0.1:9090/account/"+accountId);
  }

  public getTransactions(accountId):Observable<any>{
    return this.http.get("http://127.0.0.1:9090/"+accountId+"/viewTransaction");
  }

  public updatePassbook(accountId):Observable<any>{
    return this.http.get("http://127.0.0.1:9090/"+accountId+"/updatePassbook");
  }

}
