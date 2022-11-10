import TextField from '@mui/material/TextField';
import { useState } from 'react';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import { useSelector, useDispatch } from 'react-redux';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import api from "../api";
import { useSnackbar } from 'notistack';

const RegisterForm = (props) => {
  const { enqueueSnackbar, closeSnackbar } = useSnackbar();

  const store = useSelector(store => store);
  const dispatch = useDispatch();
  const [form, setForm] = useState({
    first_name: "",
    last_name: "",
    gender: 0
  });
  const typeString = props.type === 0 ? 'customer' : (props.type === 1 ? 'shopkeeper' : 'admin');
  const handleChange_f = (e) => setForm({ ...form, first_name: e.target.value });
  const handleChange_l = (e) => setForm({ ...form, last_name: e.target.value });
  const handleChange_g = (event) => setForm({ ...form, gender: event.target.value });
  const handleComplete = async () => {
    if (form.first_name === "" || form.last_name === "" || form.gender === null || store.auth.register.role === 2) {
      enqueueSnackbar("Please complete the form", { variant: "warning" });
    }
    else {
      const payload = { ...store.auth.register, role: store.auth.register.role + 1, ...form };
      console.log(payload);
      const { data } = await api.post(`auth/signup/${typeString}`, payload);
      enqueueSnackbar(data, { variant: "success" });
    }
    props.handleDone();
  }
  return (
    <div>
      <TextField
        id="outlined-name"
        label="First Name"
        value={form.first_name}
        onChange={handleChange_f}
        margin="normal"
        fullWidth
      />
      <TextField
        id="outlined-name"
        label="Last Name"
        value={form.last_name}
        onChange={handleChange_l}
        margin="normal"
        fullWidth
      />
      <RadioGroup
        row
        aria-labelledby="demo-row-radio-buttons-group-label"
        name="row-radio-buttons-group"
        value={form.gender}
        onChange={handleChange_g}
      >
        <FormControlLabel value={0} control={<Radio />} label="Female" />
        <FormControlLabel value={1} control={<Radio />} label="Male" />
      </RadioGroup>
      <Stack direction={{ xs: 'column', sm: 'row' }}
        spacing={{ xs: 1, sm: 2, md: 4 }}>
        <Button onClick={handleComplete} variant={`contained`}>OK</Button>
      </Stack>
    </div>
  );
}
export default RegisterForm;