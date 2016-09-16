import { Component } from '@angular/core';
import {AnkiService} from "./anki/anki.service";
import {Observable, Subscription} from "rxjs/Rx";
import {WebSocketService, WebSocketMessage} from "./websocket/websocket.service";
import {Store} from "@ngrx/store";
import {AppStore, Position, SET_CAR_NAME, SET_POSITION, SET_SPEED} from "./anki/anki.store";

@Component({
  selector: 'my-app',
  templateUrl: 'app/app.component.html',
  providers: [
    WebSocketService,
    AnkiService
  ]
})
export class AppComponent {

  private positionUpdate:Observable<any>;
  private updater:CarUpdate = new CarUpdate();

  private clientPosition:Observable<Position>;
  private subs:Subscription;

  constructor(private ankiService:AnkiService,
              private webSocketService:WebSocketService,
              public store: Store<AppStore>) {
    this.positionUpdate = ankiService.positionUpdate();
    this.clientPosition = store.select('clientPosition') as Observable<Position>;
  }

  ngOnInit() {
    this.subs = this.clientPosition.subscribe(store => this.clientPositionChanged(store));
  }

  clientPositionChanged(store:any) {
    console.log("store: " + store);
    if (!store.clientPosition) {
      return
    }
    if (!store.clientPosition.carName) {
      return
    }
    if (!store.clientPosition.position && !store.clientPosition.speed) {
      return
    }
    this.webSocketService.sendMessage(new WebSocketMessage("CarUpdate", store.clientPosition));
  }

  ngOnDestroy() {
    this.subs.unsubscribe();
  }

  sendUpdater() {
  }

  setCarName(name:string) {
    this.store.dispatch({ type: SET_CAR_NAME, payload: name});
  }

  sendPosition(pos:number) {
    this.store.dispatch({ type: SET_POSITION, payload: pos});
    //this.webSocketService.sendMessage(new WebSocketMessage("CarUpdate", {carName: this.updater.carName, position: pos} ));
  }

  sendSpeed(speed:number) {
    this.store.dispatch({ type: SET_SPEED, payload: speed});
    //this.webSocketService.sendMessage(new WebSocketMessage("CarUpdate", {carName: this.updater.carName, speed: speed} ));
  }
}

export class CarUpdate {
  constructor(public carName:string="", public position:number=0, public speed:number=0){}
}
