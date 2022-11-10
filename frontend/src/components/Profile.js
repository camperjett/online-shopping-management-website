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
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import EmailList from './EmailsList';
import ContactList from './ContactsList';

const Profile = () => {
  const dispatch = useDispatch();
  const store = useSelector(store => store);
  const navigate = useNavigate();
  const [form, setForm] = React.useState({
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
  })
  const [gender, setGender] = React.useState(0);
  const [edit, setEdit] = React.useState(0);
  const handleChange_g = (event) => {
    setForm({ ...form, "gender": event.target.value });
  };
  const handleChange_u = (event) => {
    setForm({ ...form, "username": event.target.value });
  };
  const handleChange_f = (event) => {
    setForm({ ...form, "first_name": event.target.value });
  };
  const handleChange_l = (event) => {
    setForm({ ...form, "last_name": event.target.value });
  };
  const handleEdit_b = async () => {
    console.log(form);
    const { data } = await api.put(`/dashboard/profile/edit`, form, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    console.log(data);
    getData();
    setEdit(!edit);
  }
  const getData = async () => {
    const { data } = await api.get(`/dashboard/profile`, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    })
    setForm(data);
  }
  React.useEffect(() => {
    getData();
  }, []);
  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant="h5" gutterBottom>
          Your Profile
        </Typography>
      </div>
      <Box
        component="form"
        sx={{
          display: "flex",
          flexDirection: "column",
        }}
        noValidate
        autoComplete="off"
      >
        <div style={{ display: "flex" }}>
          <TextField onChange={handleChange_u} value={form.username} disabled={!edit} sx={{ m: 2, width: "25%" }} id="outlined-basic-username" label="Username" variant="outlined" />
          <FormControl disabled={!edit} sx={{ m: 2 }}>
            <FormLabel id="demo-row-radio-buttons-group-label">Your Gender</FormLabel>
            <RadioGroup
              row
              aria-labelledby="demo-controlled-radio-buttons-group"
              name="controlled-radio-buttons-group"
              value={form.gender}
              onChange={handleChange_g}
            >
              <FormControlLabel value={1} control={<Radio />} label="Male" />
              <FormControlLabel value={0} control={<Radio />} label="Female" />
            </RadioGroup>
          </FormControl>
        </div>
        <div style={{ display: "flex" }}>
          <TextField onChange={handleChange_f} value={form.first_name} disabled={!edit} sx={{ m: 2 }} id="outlined-basic-f-name" label="First Name" variant="outlined" />
          <TextField onChange={handleChange_l} value={form.last_name} disabled={!edit} sx={{ m: 2 }} id="outlined-basic-l-name" label="Last Name" variant="outlined" />
        </div>
        <Stack spacing={2} direction="row">
          {edit ?
            <>
              <Button onClick={() => setEdit(!edit)} variant="text">Cancel</Button>
              <Button onClick={handleEdit_b} variant="contained">OK</Button>
            </> :
            <Button onClick={() => setEdit(!edit)} variant="text">Edit</Button>
          }
        </Stack>
        <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '1rem' }}>
          <Typography variant="h5" gutterBottom>
            Contact Info
          </Typography>
        </div>
        <EmailList />
        <ContactList />
      </Box>
    </div>
  );
}
export default Profile;