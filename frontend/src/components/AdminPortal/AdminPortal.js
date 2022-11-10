import { useNavigate } from "react-router-dom";
import { Typography } from '@mui/material';
import Navbar from "./Navbar";
import { styled } from '@mui/material/styles';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import api from '../../api';
import Avatar from '@mui/material/Avatar';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import HomeIcon from '@mui/icons-material/Home';
import FolderSharedIcon from '@mui/icons-material/FolderShared';
import FavoriteIcon from '@mui/icons-material/Favorite';
import Orders from "./Orders";
import MembershipRequests from "./MembershipRequests";
import CategoryIcon from '@mui/icons-material/Category';
import Products from './Products';
import Cats from "./Cats";

function stringToColor(string) {
  let hash = 0;
  let i;

  /* eslint-disable no-bitwise */
  for (i = 0; i < string.length; i += 1) {
    hash = string.charCodeAt(i) + ((hash << 5) - hash);
  }

  let color = '#';

  for (i = 0; i < 3; i += 1) {
    const value = (hash >> (i * 8)) & 0xff;
    color += `00${value.toString(16)}`.slice(-2);
  }
  /* eslint-enable no-bitwise */

  return color;
}

function stringAvatar(name) {
  return {
    sx: {
      bgcolor: stringToColor(name),
    },
    children: `${name.split(' ')[0][0]}${name.split(' ')[1][0]}`,
  };
}


const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(2),
  margin: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

const AdminPortal = () => {
  const navigate = useNavigate();
  const auth = useSelector(store => store.auth);
  const store = useSelector((store) => store);
  const [press, setPress] = useState(0);
  const checkPermission = () => {
    if (auth.userId === null || auth.role !== 3) navigate('/');
  }
  const ShowChoiceItem = () => {
    if (press === 0) return <Orders />;
    else if (press === 1) return <MembershipRequests />;
    else if (press === 2) return <Products />;
    else if (press === 3) return <Cats />;
  }
  useEffect(() => {
    checkPermission();
  }, []);
  return (
    <div>
      <Navbar />
      <div>
        <Grid container spacing={1}>
          <Grid item xs={3}>
            <Item>
              <Stack
                direction="column"
                justifyContent="center"
                alignItems="center"
                spacing={1}
              >
                <Button onClick={() => { setPress(0) }} fullWidth variant={press === 0 ? "contained" : "text"}><ShoppingCartIcon /><Typography sx={{ ml: 'auto' }}>Orders</Typography></Button>
                <Button onClick={() => { setPress(1) }} fullWidth variant={press === 1 ? "contained" : "text"}><FolderSharedIcon /><Typography sx={{ ml: 'auto' }}>Membership Requests</Typography></Button>
                <Button onClick={() => { setPress(2) }} fullWidth variant={press === 2 ? "contained" : "text"}><CategoryIcon /><Typography sx={{ ml: 'auto' }}>Products</Typography></Button>
                <Button onClick={() => { setPress(3) }} fullWidth variant={press === 3 ? "contained" : "text"}><CategoryIcon /><Typography sx={{ ml: 'auto' }}>Categories</Typography></Button>
              </Stack>
            </Item>
          </Grid>
          <Grid item xs={9}>
            <Item>
              <ShowChoiceItem />
            </Item>
          </Grid>
        </Grid>
      </div>
    </div>
  );
}
export default AdminPortal;