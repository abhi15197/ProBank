import { BankserviceService } from './bankservice.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { ViewtransactionComponent } from './viewtransaction/viewtransaction.component';
import { DotransactionComponent } from './dotransaction/dotransaction.component';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule,ReactiveFormsModule} from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatInputModule} from '@angular/material/input';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatDividerModule} from '@angular/material/divider';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatMenuModule} from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { NavbarComponent } from './navbar/navbar.component';
import {MatTableModule} from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import {MatSelectModule} from '@angular/material/select';
import { UpdatePassbookComponent } from './update-passbook/update-passbook.component';
import {MatRippleModule} from '@angular/material/core';
import {MatDialogModule} from '@angular/material/dialog';
import { DialogComponent } from './dialog/dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ViewtransactionComponent,
    DotransactionComponent,
    AccountDetailsComponent,
    NavbarComponent,
    UpdatePassbookComponent,
    DialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatRippleModule,
    MatCheckboxModule,
    MatDividerModule,
    MatGridListModule,
    MatIconModule,
    MatMenuModule,MatDialogModule,
    MatToolbarModule,MatSelectModule,
    MatButtonModule,MatTableModule,MatCardModule
  ],
  providers: [BankserviceService],
  bootstrap: [AppComponent],
  entryComponents:[DialogComponent]
})
export class AppModule { }
