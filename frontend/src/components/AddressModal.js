import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { connect } from 'react-redux';
import { fetchAddresses } from '../actions';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';
import MenuItem from '@mui/material/MenuItem';
import { states } from "../constants";
import api from '../api';

const styles = {
  // this group of buttons will be aligned to the right side
  toolbarButtons: {
    display: 'flex',
  },
  BoxStyle: {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
  }
};

const BasicModal = (props) => {
  const [inType, setInType] = React.useState(1);
  // const [open, setOpen] = React.useState(false);
  const [form, setForm] = React.useState(
    {
      name: "",
      pincode: "",
      city: "",
      state: 0,
      house_address: "",
    }
  )
  // const handleOpen = () => setOpen(true);
  const handleClose = () => props.setOpen(false);
  const handleChange_n = (e) => setForm({ ...form, name: e.target.value });
  const handleChange_p = (e) => setForm({ ...form, pincode: e.target.value });
  const handleChange_c = (e) => {
    setForm({ ...form, city: e.target.value });
  };
  const handleChange_s = (e) => setForm({ ...form, state: e.target.value });
  const handleChange_h = (e) => setForm({ ...form, house_address: e.target.value });

  const handleAddAddress = async () => {

    const { data } = await api.post(`/dashboard/address/add`, form, {
      headers: {
        'user-id': props.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    props.fetchAddresses({ user_id: props.auth.userId });

  }

  const toggleInType = () => setInType(!inType);

  return (
    <div>
      <Modal
        open={props.open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={styles.BoxStyle}>
          <div style={styles.toolbarButtons}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
              {props.type} Address
            </Typography>
            <div style={{ marginLeft: 'auto' }}><IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="primary-search-account-menu"
              aria-haspopup="true"
              color="inherit"
              onClick={handleClose}
            >
              <CloseIcon />
            </IconButton></div>
          </div>
          {/* <BaseForm /> */}
          <TextField
            id="outlined-name"
            label="Name"
            value={form.name}
            onChange={handleChange_n}
            margin="normal"
            fullWidth
          />
          <TextField
            id="outlined-name"
            label="Pincode"
            value={form.pincode}
            onChange={handleChange_p}
            margin="normal"
            fullWidth
          />
          <TextField
            id="outlined-name"
            label="City"
            value={form.city}
            onChange={handleChange_c}
            margin="normal"
            fullWidth
          />
          <TextField
            id="outlined-select-state"
            select
            label="State"
            value={form.state}
            onChange={handleChange_s}
          >
            {states.map((option) => (
              <MenuItem key={option.value} value={option.value}>
                {option.label}
              </MenuItem>
            ))}
          </TextField>
          <TextField
            id="outlined-name"
            label="Local Address"
            value={form.house_address}
            onChange={handleChange_h}
            margin="normal"
            fullWidth
          />
          <Stack direction={{ xs: 'column', sm: 'row' }}
            spacing={{ xs: 1, sm: 2, md: 4 }}>
            <Button onClick={handleAddAddress} variant={`contained`}>Ok</Button>
          </Stack>
        </Box>
      </Modal>
    </div >
  );
}

const mapStateToProps = state => {
  return { auth: state.auth };
}
export default connect(mapStateToProps, { fetchAddresses })(BasicModal);