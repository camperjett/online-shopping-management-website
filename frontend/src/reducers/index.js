import { combineReducers } from "redux";
import authReducers from "./authReducers";
import catReducers from "./categoryReducers";
import cartSlice from './cart-slice';
import addressReducers from './addressReducers';
import wishlistSlice from './wishlist-slice';


export default combineReducers({
  cats: catReducers,
  auth: authReducers,
  cart: cartSlice.reducer,
  address: addressReducers,
  wishlist: wishlistSlice.reducer,
})