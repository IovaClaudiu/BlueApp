import { AppRoutingModule } from './app-routing.module';
import { GroupComponent } from './groups/group/group.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { JwtInterceptor } from './_helpers';
import { AppComponent } from './app.component';
import { HomeComponent } from './home';
import { LoginComponent } from './login';
import { RegisterComponent } from './register';
import { AlertComponent } from './alert';
import { GroupsComponent } from './groups';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AddGroupComponent } from './modals/add-group/add-group.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import {
  AddToGroupComponent,
  ModalAddToGroupComponent,
} from './modals/add-to-group/add-to-group.component';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [
    ModalModule.forRoot(),

    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    AlertComponent,
    GroupsComponent,
    GroupComponent,
    AddGroupComponent,
    AddToGroupComponent,
    ModalAddToGroupComponent,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
