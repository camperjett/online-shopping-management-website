import { FETCH_ADDRESSES, ADD_ADDRESS, SELECT_ADDRESS, PAY_MODE } from "../actions/types";

const INTIAL_STATE = {
  list: [],
  selected: null,
  payMode: null,
};
export default (state = INTIAL_STATE, action) => {
  switch (action.type) {
    case FETCH_ADDRESSES:
      return { ...state, list: action.payload };
    case ADD_ADDRESS:
      return;
    case SELECT_ADDRESS:
      return { ...state, selected: action.payload };
    case PAY_MODE:
      return { ...state, payMode: action.payload };
    default:
      return state;
  }
};