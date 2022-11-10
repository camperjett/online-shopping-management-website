import React from 'react';
import { Add, Remove } from '@mui/icons-material';
import { useDispatch } from 'react-redux';
import { incrProduct, decrProduct } from '../reducers/cart-slice';

const CartProduct = ({ product, index }) => {
  const dispatch = useDispatch();
  const incrQty = () => {
    dispatch(incrProduct({ index }));
  };
  const decrQty = () => {
    dispatch(decrProduct({ index }));
  };
  return (
    <div className='flex flex-col md:flex-row justify-between mb-4'>
      <div className='flex flex-col md:flex-row'>
        <div className='md:mr-8 mb-8 md:mb-0'>
          <img className='w-full h-full md:w-64 md:h-64' src={product.photo_url} />
        </div>
        <div>
          <div className='mb-6'>
            <span className='font-bold'>Product:</span> {product.product_name}
          </div>
          <div className='mb-6'>
            <span className='font-bold'>ID:</span> {product.product_id}
          </div>
        </div>
      </div>
      <div className='flex flex-col items-center justify-center'>
        <div className='flex items-center justify-start mb-8'>
          <div className='flex items-center justify-start'>
            <span
              className='cursor-pointer'
              onClick={decrQty}
            >
              <Remove />
            </span>
            <span className='mx-2 text-xl h-10 w-10 rounded-2xl border flex justify-center items-center'>
              {product.quantity}
            </span>
            <span
              className='cursor-pointer'
              onClick={incrQty}
            >
              <Add />
            </span>
          </div>
        </div>
        <span className='block mb-6 text-4xl'>
          ₹ {product.quantity * product.MRP}
        </span>
      </div>
    </div>
  );
};

export default CartProduct;
