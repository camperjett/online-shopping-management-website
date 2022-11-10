import * as React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Checkbox from '@mui/material/Checkbox';
import IconButton from '@mui/material/IconButton';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { connect } from 'react-redux';
import { fetchAddresses } from '../actions';
import EditAddressModal from './EditAddressModal';
import api from '../api';
import { paymentModes } from '../constants';
import { useDispatch } from 'react-redux';
import { PAY_MODE } from '../actions/types';
import { useSelector } from 'react-redux';

const Addresses = (props) => {
  const dispatch = useDispatch();
  const address = useSelector((store) => store.address);
  const handleToggle = (value) => () => {
    dispatch({ type: PAY_MODE, payload: value });
  };

  return (
    <>
      <List sx={{ width: '100%', bgcolor: 'background.paper' }}>
        {paymentModes.map((item, index) => {
          const labelId = `checkbox-list-label-${item.value}`;

          return (
            <ListItem
              key={item.value}
            >
              <ListItemButton role={undefined} onClick={handleToggle(item.value)} dense>
                <ListItemIcon>
                  <Checkbox
                    edge="start"
                    checked={address.payMode === item.value}
                    tabIndex={-1}
                    disableRipple
                    inputProps={{ 'aria-labelledby': labelId }}
                  />
                </ListItemIcon>
                <ListItemText id={labelId} primary={`${item.title}`} />
              </ListItemButton>
            </ListItem>
          );
        })}
      </List>
    </>
  );
}
const mapStateToProps = state => {
  return state;
}
export default connect(mapStateToProps, { fetchAddresses })(Addresses);