import { ActionReducer, Action } from '@ngrx/store';

export const SET_CAR_NAME = 'SET_CAR_NAME';
export const SET_SPEED = 'SET_SPEED';
export const SET_POSITION = 'SET_POSITION';

export const clientPosition: ActionReducer<Position> = (position: Position = {}, action: Action) => {
  switch (action.type) {
    case SET_CAR_NAME:
      return Object.assign({}, position, {carName: action.payload});
    case SET_SPEED:
      return Object.assign({}, position, {speed: action.payload});
    case SET_POSITION:
      return Object.assign({}, position, {position: action.payload});
//    case PositionUpdate:
  //    return Object.assign({}, state, this.overwrite(state, action.payload));
    default:
      return position;
  }
};

export const SWITCH_LIGHT = 'SWITCH_LIGHT';
export const clientLights: ActionReducer<Light[]> =
  (lights: Light[] = [{name:'HEADLIGHTS',on:false},{name:'BRAKELIGHTS',on:false}, {name:'FRONTLIGHTS',on:false}, {name:'ENGINE',on:false}],
   action: Action) => {
    switch (action.type) {
    case SWITCH_LIGHT:
      return lights.map(l => switchLight(l, action.payload));
    default:
      return lights;
  }
};

const switchLight = (l: Light, lightName: any) => {
  if (l.name == lightName) {
    return Object.assign({}, l, {on: !l.on});
  }
  return l;
};

/*
const overwrite = (state = {}, payload:Position) => {
  let positions:{[carName:string]:Position[]} = state['streamPositions'];
  if (!positions[payload.carName]) {
    positions[payload.carName] = [];
  }

  if (!positions.find(e => e.carName = payload.carName)) {
    positions = [
      ...positions,
      new Position(payload.carName)
    ]
  }

  return {'positions': positions};
};
*/
export interface AppStore {
  clientPosition:Position;
  clientLights:Light[];
  streamPositions:{[carName:string]:Position[]};
}

export class Position {
  constructor(public carName?:string, public position?:number, public speed?:number){}
}

export class Light {
  constructor(public name:string, public on:boolean){}
}
