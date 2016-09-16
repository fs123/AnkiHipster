
import {Injectable} from "@angular/core";
import {WebSocketService, WebSocketMessage} from "../websocket/websocket.service";
import {Observable} from "rxjs";
import {WebSocketSubject} from "rxjs/observable/dom/WebSocketSubject";
import any = jasmine.any;

@Injectable()
export class AnkiService {

  constructor(private webSocketService:WebSocketService) { }

  positionUpdate(): Observable<any> {
    return this.webSocketService
      .rawDataStream()
      .scan((accum:any[], msg:any) => {
          accum.unshift(msg);
          if(accum.length > 10) {
            accum.pop();
          }
          return accum;
        }, []);
  }

}
