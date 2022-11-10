import api from '../api';
import { UPLOAD_IMG, SIGN_IN, SIGN_OUT, SIGN_IN_FAIL, CATEGORY_GET_PRODUCTS, FETCH_CATS, SELECT_ADDRESS, EDIT_ADDRESS, DELETE_ADDRESS, ADD_ADDRESS, FETCH_ADDRESSES, UPLOAD_IMG_CLEAR } from './types';


export const signOut = () => {
  return {
    type: SIGN_OUT
  };
};

export const signIn = userData => async dispatch => {
  const { data } = await api.post('/auth/signin', {
    username: userData.username,
    password: userData.password,
    role: userData.role + 1
  })
  console.log(data);
  if (data.isSuccess === 3) {
    dispatch({ type: SIGN_IN, payload: { user_id: data.user_id, role: userData.role + 1 } });
  }
  else
    dispatch({ type: SIGN_IN_FAIL, payload: data.isSuccess });
}

export const fetchCategories = () => async dispatch => {
  const data = await api.get('/category');
  dispatch({ type: FETCH_CATS, payload: data.data })
}

export const fetchCatProducts = req => async dispatch => {
  const { data } = await api.post(`/category/${req.cid}/products`, { price_low: 0 });
  dispatch({ type: CATEGORY_GET_PRODUCTS, payload: data });
}

export const fetchAddresses = req => async dispatch => {
  const { data } = await api.get(`/dashboard/address`, {
    headers: {
      'user-id': req.user_id,
      'Content-Type': 'application/json'
    }
  })
  dispatch({ type: FETCH_ADDRESSES, payload: data });
}

export const uploadImg = (url) => {
  console.log("OK", url);
  return {
    type: UPLOAD_IMG,
    payload: url
  }
}
export const uploadImgClear = () => {
  return {
    type: UPLOAD_IMG_CLEAR
  }
}