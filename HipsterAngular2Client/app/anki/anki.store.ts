import { ActionReducer, Action } from '@ngrx/store';

export const SET_CAR_NAME = 'SET_CAR_NAME';
export const SET_SPEED = 'SET_SPEED';
export const SET_POSITION = 'SET_POSITION';

export const clientPosition: ActionReducer<Position> = (state: Position = {}, action: Action) => {
  switch (action.type) {
    case SET_CAR_NAME:
      return Object.assign({}, state, setCarName(state, action.payload));
    case SET_SPEED:
      return Object.assign({}, state, setSpeed(state, action.payload));
    case SET_POSITION:
      return Object.assign({}, state, setPosition(state, action.payload));
//    case PositionUpdate:
  //    return Object.assign({}, state, this.overwrite(state, action.payload));
    default:
      return state;
  }
};

const setCarName = (state = {}, carName:string) => {
  return {clientPosition: Object.assign({}, state['clientPosition'], {carName:carName})};
};

const setSpeed = (state = {}, speed:number) => {
  return {clientPosition: Object.assign({}, state['clientPosition'], {speed:speed})};
};

const setPosition = (state = {}, position:number) => {
  return {clientPosition: Object.assign({}, state['clientPosition'], {position:position})};
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
  streamPositions:{[carName:string]:Position[]};
}

export class Position {
  constructor(public carName?:string, public position?:number, public speed?:number){}
}

//  */
