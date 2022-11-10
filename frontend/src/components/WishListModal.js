import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import Button from '@mui/material/Button';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import { useSelector, useDispatch } from 'react-redux';
import WishListItem from './WishListItem';
import Typography from '@mui/material/Typography';

const WishListModal = ({ open, setOpen }) => {
  const wishlist = useSelector(store => store.wishlist);
  const dispatch = useDispatch();
  return (
    <div>
      <Drawer
        anchor='right'
        open={open}
        onClose={() => setOpen(!open)}
      >
        <Box
          sx={{ p: 2 }}
          role="presentation"
        // onClick={() => setOpen(!open)}
        // onKeyDown={() => setOpen(!open)}
        >
          <Typography component="div" variant="h6" gutterBottom>
            Wishlist
          </Typography>
          {wishlist.products.map(item => <WishListItem key={item.product_id} item={item} />)}
        </Box>
      </Drawer>
    </div>
  );
}

export default WishListModal;