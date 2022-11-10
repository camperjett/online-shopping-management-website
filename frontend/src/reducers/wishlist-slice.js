import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  products: []
};
const wishlistSlice = createSlice({
  name: 'wishlist',
  initialState,
  reducers: {
    addProduct(state, action) {
      const newProduct = {
        product_id: action.payload.product_id,
        product_name: action.payload.product_name,
        description: action.payload.description,
        photo_url: action.payload.photo_url,
        MRP: action.payload.MRP,
      };
      let added = false;

      for (let oldProduct of state.products) {
        // Check if the product already added before
        if (oldProduct.product_id === newProduct.product_id) {
          // If added before check if the same size 
          // oldProduct.quantity += newProduct.quantity;
          added = true;
          break;
        }
      }
      // If not added before or not the same size push it as a new product 
      if (!added) {
        state.products.push(newProduct);
      }
      // state.totalQantity += newProduct.quantity;
      // state.totalPrice += newProduct.MRP * newProduct.quantity;
    },
    removeProduct(state, action) {
      const { index } = action.payload;
      state.products.splice(index, 1);
    },
    clear(state, action) {
      return initialState;
    }
  }
});

export const { addProduct, removeProduct, clear } = wishlistSlice.actions;
export default wishlistSlice;