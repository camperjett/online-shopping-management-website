import { Typography } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import { styled, alpha } from '@mui/material/styles';
import InputBase from '@mui/material/InputBase';
import * as React from 'react';
import { useNavigate, Link } from "react-router-dom";
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import api from '../api';
import { useDispatch, useSelector } from 'react-redux';
import OrderItem from './OrderItem';

const Search = styled('div')(({ theme }) => ({
  position: 'relative',
  borderRadius: theme.shape.borderRadius,
  backgroundColor: alpha(theme.palette.common.white, 0.15),
  '&:hover': {
    backgroundColor: alpha(theme.palette.common.white, 0.25),
  },
  marginRight: theme.spacing(2),
  marginLeft: 0,
  width: '100%',
  [theme.breakpoints.up('sm')]: {
    marginLeft: theme.spacing(3),
    width: 'auto',
  },
}));
const SearchIconWrapper = styled('div')(({ theme }) => ({
  padding: theme.spacing(0, 2),
  height: '100%',
  position: 'absolute',
  pointerEvents: 'none',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
}));

const StyledInputBase = styled(InputBase)(({ theme }) => ({
  color: 'inherit',
  '& .MuiInputBase-input': {
    padding: theme.spacing(1, 1, 1, 0),
    // vertical padding + font size from searchIcon
    paddingLeft: `calc(1em + ${theme.spacing(4)})`,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('md')]: {
      width: '20ch',
    },
  },
}));

const Orders = () => {
  const dispatch = useDispatch();
  const store = useSelector(store => store);
  const navigate = useNavigate();
  const [form, setForm] = React.useState({
    searchTerm: "",
    status: 0
  })
  const [orders, setOrders] = React.useState([]);
  const getData = async () => {
    const { data } = await api.get(`orders/user_id/${store.auth.userId}`);
    setOrders(data);
  }
  const switchData = async (e, v) => {
    await setForm({ ...form, status: v });
    /***
     * 0: just placed, yet to be taken action on 
     * 1: on the way
     * 2: delivered
     * 3: cancelled
     */
    const { data } = await api.get(`orders/status/${v}/show`, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    })
    console.log(data);
    setOrders(data);
    console.log(orders);
  }
  React.useEffect(() => {
    getData();
  }, []);
  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant="h5" gutterBottom>
          Your Orders
        </Typography>
        <Search >
          <SearchIconWrapper>
            <SearchIcon />
          </SearchIconWrapper>
          <StyledInputBase
            placeholder="Search your Orders ..."
            inputProps={{ 'aria-label': 'search' }}
            value={form.searchTerm}
            onChange={(e) => setForm({ ...form, searchTerm: e.target.value })}
            onKeyPress={(e) => {
              if (e.key === "Enter" && form.searchTerm) console.log('okay searching');
            }}
          />
        </Search>
      </div>
      <Tabs value={form.status} onChange={switchData} centered>
        <Tab label="All" />
        <Tab label="On the way" />
        <Tab label="Delivered" />
        <Tab label="Cancelled" />
      </Tabs>
      {orders.map(order => <OrderItem getData={getData} key={order.pl_order.order_id} order={order} />)}
    </div>
  );
}
export default Orders;