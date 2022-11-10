import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import CartProduct from './CartProduct';
import Slide from '@mui/material/Slide';
import Dialog from '@mui/material/Dialog';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const ShoppingCart = ({ open, setOpen, openAuth, setOpenAuth }) => {

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };
  const history = useNavigate();
  const cart = useSelector((store) => store.cart);
  const auth = useSelector((store) => store.auth);

  const continueShoppingClickHandler = () => {
    history.goBack();
  };
  const handleCheckout = () => {
    if (cart.totalQantity === 0) return;
    else if (auth.userId === null) {
      setOpenAuth(!openAuth);
      return;
    } else {
      setOpen(false);
      history('/checkout');
      console.log(cart);
    }
  }
  return (
    <Dialog
      fullScreen
      open={open}
      onClose={handleClose}
      TransitionComponent={Transition}
    >
      <>
        <section className='px-8 py-4'>
          <div style={{ display: 'flex' }}>
            <div style={{ marginLeft: 'auto' }}><IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="primary-search-account-menu"
              aria-haspopup="true"
              color="inherit"
              onClick={handleClose}
            >
              <CloseIcon />
            </IconButton>
            </div>

          </div>
          <h1 className='uppercase mt-4 mb-8 text-4xl text-center'>Shopping Bag ({cart.totalQantity})</h1>
          <div className='grid sm:grid-cols-3 gap-4 md:gap-6 lg:gap-8'>
            <div className='flex'>
            </div>
            <div>
              <a onClick={handleCheckout} className='text-sm lg:text-md cursor-pointer uppercase block p-4 border-2 hover:text-black hover:border-black hover:bg-white bg-black text-white transition ease-out duration-500'>
                checkout now
              </a>
            </div>
          </div>
          <div className='my-12 grid gap-8 lg:grid-cols-[2fr_1fr]'>
            <div>
              {cart.products.map((product, index) => (
                <CartProduct key={product.product_id} product={product} index={index} />
              ))}
            </div>
            <div>
              <div className='border rounded-xl p-4'>
                <h1 className='uppercase text-4xl mb-8'>order summary</h1>
                <div className='flex justify-between mb-8'>
                  <span className='capitalize'>subtotal</span>
                  <span>₹ {cart.totalPrice}</span>
                </div>
                <div className='flex justify-between mb-8'>
                  <span className='capitalize'>estimated shipping</span>
                  <span>₹ 00.00</span>
                </div>
                <div className='flex justify-between mb-8'>
                  <span className='capitalize'>shipping discount</span>
                  <span>-₹ 00.00</span>
                </div>
                <div className='flex justify-between mb-8'>
                  <span className='capitalize font-bold text-2xl'>Total</span>
                  <span className='font-bold text-2xl'>₹ {cart.totalPrice}</span>
                </div>
              </div>
            </div>
          </div>
        </section>
      </>
    </Dialog>
  );
};

export default ShoppingCart;
