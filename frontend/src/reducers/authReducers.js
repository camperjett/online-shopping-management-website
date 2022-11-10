import { SIGN_IN, SIGN_OUT, SIGN_IN_FAIL, SET_NEW } from "../actions/types";

const INTIAL_STATE = {
  signStatus: 4,
  userId: null,
  role: null,
  register: {}
};
// signStatus
/***
 * 0: form not good, password or/and username empty, or role not in range
 * 1: not exist
 * 2: not right credentials
 * 3: success
 * 4: signed out
 */
export default (state = INTIAL_STATE, action) => {
  switch (action.type) {
    case SIGN_IN_FAIL:
      return { ...state, signStatus: action.payload, userId: null };
    case SIGN_IN:
      return { ...state, signStatus: 3, userId: action.payload.user_id, role: action.payload.role };
    case SIGN_OUT:
      return { ...state, signStatus: 4, userId: null, role: null };
    case SET_NEW:
      return { ...state, register: action.payload };
    default:
      return state;
  }
};