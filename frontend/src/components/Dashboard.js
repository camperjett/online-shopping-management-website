import { styled } from '@mui/material/styles';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import { Typography } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import api from '../api';
import Avatar from '@mui/material/Avatar';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import HomeIcon from '@mui/icons-material/Home';
import FolderSharedIcon from '@mui/icons-material/FolderShared';
import FavoriteIcon from '@mui/icons-material/Favorite';
import Orders from './Orders';
import Profile from './Profile';
import Addresses from './DashboardAddresses';
import Reviews from './MyReviews';
import { useNavigate } from 'react-router-dom';

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
const Dashboard = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState({
    "user_id": null,
    "username": "",
    "first_name": "",
    "last_name": "",
    "role": null,
    "gender": null,
    "photo_url": "",
    "contact_no": [],
    "email": [],
    "license_image_url": null,
    "license_no": null
  });
  const [press, setPress] = useState(0);
  const dispatch = useDispatch();
  const store = useSelector((store) => store);
  const getData = async () => {
    const { data } = await api.get('/dashboard/profile', {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    setUser(data)
  }
  useEffect(() => {
    if (!store.auth.userId) navigate('/');
    getData();
  }, []);

  const ShowChoiceItem = () => {
    if (press === 0) return <Orders />;
    else if (press === 1) return <Profile />;
    else if (press === 2) return <Addresses />;
    else return <Reviews />;
  }
  return (
    <div>
      <Grid container spacing={1}>
        <Grid item xs={3}>
          <Item sx={{ mb: 1 }}>
            <Stack direction="row" spacing={2} sx={{ display: 'flex' }}>
              <Avatar {...stringAvatar(`${user.first_name} ${user.last_name}`)} />
              <Typography variant="h6">
                {`${user.first_name} ${user.last_name}`}
              </Typography>
            </Stack>

          </Item>
          <Item>
            <Stack
              direction="column"
              justifyContent="center"
              alignItems="center"
              spacing={1}
            >
              <Button onClick={() => { setPress(0) }} fullWidth variant={press === 0 ? "contained" : "text"}><ShoppingCartIcon /><Typography sx={{ ml: 'auto' }}>Orders</Typography></Button>
              <Button onClick={() => { setPress(1) }} fullWidth variant={press === 1 ? "contained" : "text"}><AccountBoxIcon /><Typography sx={{ ml: 'auto' }}>profile</Typography></Button>
              <Button onClick={() => { setPress(2) }} fullWidth variant={press === 2 ? "contained" : "text"}><HomeIcon /><Typography sx={{ ml: 'auto' }}>addresses</Typography></Button>
              <Button onClick={() => { setPress(3) }} fullWidth variant={press === 3 ? "contained" : "text"}><FolderSharedIcon /><Typography sx={{ ml: 'auto' }}>reviews</Typography></Button>
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
  );
}

export default Dashboard;