import { UpdatePassbookComponent } from './update-passbook/update-passbook.component';
import { ViewtransactionComponent } from './viewtransaction/viewtransaction.component';
import { DotransactionComponent } from './dotransaction/dotransaction.component';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { HomeComponent } from './home/home.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {path:"",redirectTo:"home",pathMatch:"full"},
  {path:"home",component:HomeComponent},
  {path:"accountdetails",component:AccountDetailsComponent},
  {path:"dotransaction",component:DotransactionComponent},
  {path:"viewtransaction",component:ViewtransactionComponent},
  {path:"updatepassbook",component:UpdatePassbookComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
