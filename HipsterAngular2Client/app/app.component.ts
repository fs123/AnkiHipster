import { Component } from '@angular/core';
import {AnkiService} from "./anki/anki.service";
import {Observable, Subscription} from "rxjs/Rx";
import {WebSocketService, WebSocketMessage} from "./websocket/websocket.service";
import {Store} from "@ngrx/store";
import {AppStore, Position, SET_CAR_NAME, SET_POSITION, SET_SPEED, Light, SWITCH_LIGHT} from "./anki/anki.store";

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
  private clientLights:Observable<Light[]>;

  private subs:Subscription;

  constructor(private ankiService:AnkiService,
              private webSocketService:WebSocketService,
              public store: Store<AppStore>) {
    this.positionUpdate = ankiService.positionUpdate();
    this.clientPosition = store.select('clientPosition') as Observable<Position>;
    this.clientLights = store.select('clientLights') as Observable<Light[]>;
  }

  ngOnInit() {
    this.changeLight('HEADLIGHTS');
    this.subs = Observable.combineLatest(this.clientPosition, this.clientLights).subscribe(newState => this.clientPositionChanged(newState));
  }

  clientPositionChanged(newState:any[]) {

    let clientPosition:Position = newState[0] as Position;
    let clientLights:Light[] = newState[1] as Light[];
    if (!clientPosition) {
      return
    }
    if (!clientPosition.carName) {
      return
    }

    let update = Object.assign({}, clientPosition, {lights: clientLights});
    let message = new WebSocketMessage("CarUpdate", update);
    console.log("send WS message: ");
    console.log("  " + JSON.stringify(message));

    this.webSocketService.sendMessage(message);
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

  changeLight(lightName:string) {
    this.store.dispatch({ type: SWITCH_LIGHT, payload: lightName});
  }
}

export class CarUpdate {
  constructor(public carName:string="", public position?:number, public speed?:number){}
}
