import * as React from 'react';
import { useEffect } from 'react';
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
import { useDispatch } from 'react-redux';
import { SELECT_ADDRESS } from '../actions/types';
import { useSelector } from 'react-redux';

const Addresses = (props) => {
  const dispatch = useDispatch();
  const [openAddressModal, setOpenAddressModal] = React.useState(false);
  const [editData, setEditData] = React.useState({
    name: "",
    pincode: "",
    city: "",
    state: 0,
    house_address: "",
  });
  const handleEdit = (value, index) => {
    setEditData(prev => {
      return { ...value };
    });
    setOpenAddressModal(!openAddressModal);
  };
  const handleDelete = async (id) => {
    const { data } = await api.delete(`/dashboard/address/remove/${id}`, {
      headers: {
        'user-id': props.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    console.log(data);
    props.fetchAddresses({ user_id: props.auth.userId });
  }
  const handleToggle = (value) => () => {
    dispatch({ type: SELECT_ADDRESS, payload: value });
  };

  useEffect(() => {
    if (props.address.list.length === 0) props.fetchAddresses({ user_id: props.auth.userId });
  }, []);

  return (
    <>
      <List sx={{ width: '100%', bgcolor: 'background.paper' }}>
        {props.address.list.map((value, index) => {
          const labelId = `checkbox-list-label-${value.address_id}`;

          return (
            <ListItem
              key={value.address_id}
              secondaryAction={
                <>
                  <IconButton onClick={() => { handleEdit(value, index) }} edge="end" aria-label="comments">
                    <EditIcon />
                  </IconButton>
                  <IconButton onClick={() => { handleDelete(value.address_id) }} edge="end" aria-label="comments">
                    <DeleteIcon />
                  </IconButton>
                </>
              }

            >
              <ListItemButton role={undefined} onClick={handleToggle(value.address_id)} dense>
                <ListItemIcon>
                  <Checkbox
                    edge="start"
                    checked={props.address.selected === value.address_id}
                    tabIndex={-1}
                    disableRipple
                    inputProps={{ 'aria-labelledby': labelId }}
                  />
                </ListItemIcon>
                <ListItemText id={labelId} primary={`${value.name}, ${value.house_address}`} />
              </ListItemButton>
            </ListItem>
          );
        })}
      </List>
      <EditAddressModal type={"Edit"} open={openAddressModal} setOpen={setOpenAddressModal} data={editData} setData={setEditData} />
    </>
  );
}
const mapStateToProps = state => {
  return state;
}
export default connect(mapStateToProps, { fetchAddresses })(Addresses);