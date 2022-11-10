import { Card } from '@mui/material';
import { Typography } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import LockIcon from '@mui/icons-material/Lock';
import { useEffect, useState } from 'react';
import Addresses from './Addresses';
import AddIcon from '@mui/icons-material/Add';
import AddressModal from './AddressModal';
import PaymentModes from './PaymentModes';
import { useDispatch, useSelector } from 'react-redux';
import CartProduct from './CartProduct';
import api from '../api';
import { useNavigate } from 'react-router-dom';
import { clear } from '../reducers/cart-slice';

const Checkout = (props) => {
  const [openAddressModal, setOpenAddressModal] = useState(false);
  const handleOpenAddressModal = () => setOpenAddressModal(!openAddressModal);
  const { cart, auth, address } = useSelector(store => store);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handlePlaceOrder = async () => {
    const payload = {
      "tran": {
        "mode": address.payMode
      },
      "items": cart.products.map(item => {
        return { "product_id": item.product_id, "quantity": item.quantity };
      }),
      "ad_id": address.selected
    };
    const { data } = await api.post(`/orders/place`, payload, {
      headers: {
        'user-id': auth.userId,
        'Content-Type': 'application/json'
      }
    });
    navigate(`/post-order/${data}`);
    dispatch(clear());
  }
  return (
    <div>
      <Card sx={{ p: 1, textAlign: "center", display: 'flex', justifyContent: 'space-between' }}>
        <Typography
          variant="h6"
          noWrap
          sx={{
            m: 1,
            display: { xs: 'none', md: 'flex' },
            fontFamily: 'monospace',
            fontWeight: 700,
            letterSpacing: '.3rem',
            color: 'inherit',
            textDecoration: 'none',
          }}
        >
          WHOLESAILOR
        </Typography>
        <Typography variant="h4">
          CHECKOUT
        </Typography>
        <IconButton
          size="large"
          color="inherit"
        >
          <LockIcon />
        </IconButton>
      </Card>
      <Card sx={{ p: 1, m: 1, display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
        <div style={{ display: 'flex' }}>
          <Typography variant="h4">
            1. Select Address
          </Typography>
          <IconButton
            edge="start"
            color="inherit"
            aria-label="open drawer"
            sx={{ ml: 5 }}
            onClick={handleOpenAddressModal}
          >
            <AddIcon sx={{ fontSize: 40 }} />
          </IconButton>
        </div>
        <Addresses />
      </Card>
      <Card sx={{ p: 1, m: 1, textAlign: "center", display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
        <div style={{ display: 'flex' }}>
          <Typography variant="h4" gutterBottom>
            2. Payment Method
          </Typography>
        </div>
        <PaymentModes />
      </Card>
      <Card sx={{ p: 1, m: 1, textAlign: "center", display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
        <div style={{ display: 'flex' }}>
          <Typography variant="h4" gutterBottom>
            3. Review your Order
          </Typography>
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
                <span className='font-bold text-2xl'>$ {cart.totalPrice}</span>
              </div>
              <div>
                <a onClick={handlePlaceOrder} className='text-md lg:text-lg cursor-pointer uppercase block p-4 border-2 hover:text-black hover:border-black hover:bg-white bg-black text-white transition ease-out duration-500'>
                  Place Order
                </a>
              </div>
            </div>
          </div>
        </div>
      </Card>
      <AddressModal type={"Add"} open={openAddressModal} setOpen={setOpenAddressModal} />

    </div>
  );
}

export default Checkout;