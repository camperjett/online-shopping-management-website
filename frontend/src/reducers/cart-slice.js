import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  products: [],
  totalQantity: 0,
  totalPrice: 0
};
const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    addProduct(state, action) {
      const newProduct = {
        product_id: action.payload.product.product_id,
        product_name: action.payload.product.product_name,
        description: action.payload.product.description,
        photo_url: action.payload.product?.product_images?.length > 0 ? action.payload.product.product_images[0].image : "",
        MRP: action.payload.product.MRP,
        quantity: action.payload.quantity,
      };
      let added = false;

      for (let oldProduct of state.products) {
        // Check if the product already added before
        if (oldProduct.product_id === newProduct.product_id) {
          // If added before check if the same size 
          oldProduct.quantity += newProduct.quantity;
          added = true;
          break;
        }
      }
      // If not added before or not the same size push it as a new product 
      if (!added) {
        state.products.push(newProduct);
      }
      state.totalQantity += newProduct.quantity;
      state.totalPrice += newProduct.MRP * newProduct.quantity;
    },
    decrProduct(state, action) {
      const { index } = action.payload;
      state.totalQantity--;
      state.totalPrice -= state.products[index].MRP;
      if (state.products[index].quantity === 1) state.products.splice(index, 1);
      else state.products[index].quantity--;
    },
    incrProduct(state, action) {
      const { index } = action.payload;
      state.totalQantity++;
      state.totalPrice += state.products[index].MRP;
      state.products[index].quantity++;
    },
    clear(state, action) {
      return initialState;
    }
  }
});

export const { addProduct, incrProduct, decrProduct, clear } = cartSlice.actions;
export default cartSlice;