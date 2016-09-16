import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent }  from './app.component';
import {FormsModule} from "@angular/forms";
import { Store, StoreModule } from '@ngrx/store';
import {clientPosition, clientLights} from "./anki/anki.store";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    StoreModule.provideStore({ clientPosition: clientPosition, clientLights:clientLights }, { })],
  declarations: [ AppComponent ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
