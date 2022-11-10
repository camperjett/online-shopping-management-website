import { FETCH_CATS, CATEGORY_GET_PRODUCTS, UPLOAD_IMG, UPLOAD_IMG_CLEAR, FORM_CHANGE } from "../actions/types";

const INTIAL_STATE = {
  allCats: [],
  catProducts: {},
  uploadUrl: "",
  form: {
    product_name: "",
    stock: 0,
    description: "",
    MRP: 0,
    brand: "",
    category_id: 0,
    photo_url: ""
  }
}
export default (state = INTIAL_STATE, action) => {
  switch (action.type) {
    case FETCH_CATS:
      return { ...state, allCats: action.payload };
    case CATEGORY_GET_PRODUCTS:
      return { ...state, catProducts: action.payload };
    case UPLOAD_IMG:
      return { ...state, uploadUrl: action.payload };
    case UPLOAD_IMG_CLEAR:
      return { ...state, uploadUrl: null };
    case FORM_CHANGE:
      return { ...state, form: action.payload }
    default:
      return state;
  }
};