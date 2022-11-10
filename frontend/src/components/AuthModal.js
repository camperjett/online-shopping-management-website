import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { connect, useDispatch } from 'react-redux';
import { signIn, signOut } from '../actions';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';
import { SET_NEW } from '../actions/types';
import RegisterForm from './RegisterForm';
import { useNavigate } from 'react-router-dom';

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
  const dispatch = useDispatch();
  const [inType, setInType] = React.useState(1);
  const [nextStep, setNextStep] = React.useState(true);
  const navigate = useNavigate();

  const [form, setForm] = React.useState(
    {
      username: "",
      password: "",
      email: "",
      role: 0,
      gender: 0,
      phone_no: "",
      first_name: "",
      last_name: ""
    }
  )
  // const handleOpen = () => setOpen(true);
  const handleClose = () => props.setOpen(false);
  const handleChange_p = (e) => setForm({ ...form, password: e.target.value });
  const handleChange_u = (e) => setForm({ ...form, username: e.target.value });
  const handleChange_r = (event, newValue) => {
    setForm({ ...form, role: newValue });
  };
  const handleDone = () => {
    setInType(!inType);
    setNextStep(!nextStep);
    setForm({
      username: "",
      password: "",
      email: "",
      role: 0,
      gender: 0,
      phone_no: "",
      first_name: "",
      last_name: ""
    });
    handleClose();
  }

  const handleRegister = async () => {
    await dispatch({ type: SET_NEW, payload: form });
    await setNextStep(!nextStep);
  }
  const handleLogin = async () => {
    await props.signIn(form);
  }
  const handleLogOut = () => {
    props.signOut();
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
              {inType ? "Login" : "Register"}
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

          <Tabs value={form.role} onChange={handleChange_r} centered>
            <Tab label="CUSTOMER" />
            <Tab label="SHOPKEEPER" />
            <Tab label="ADMIN" />
          </Tabs>
          {/* <BaseForm /> */}
          {nextStep ? <>
            <TextField
              id="outlined-name"
              label="Username"
              value={form.username}
              onChange={handleChange_u}
              margin="normal"
              fullWidth
            />
            <TextField
              id="outlined-name"
              label="Password"
              value={form.password}
              onChange={handleChange_p}
              margin="normal"
              fullWidth
            />
            <Stack direction={{ xs: 'column', sm: 'row' }}
              spacing={{ xs: 1, sm: 2, md: 4 }}>
              {props.auth.userId !== null ? <><Button onClick={handleLogOut} variant={`contained`}>Logout</Button></> : <><Button onClick={!inType ? handleRegister : toggleInType} variant={!inType ? `contained` : `text`}>Register</Button>
                <Button onClick={inType ? handleLogin : toggleInType} variant={inType ? `contained` : `text`}>Login</Button></>}
            </Stack>
          </>
            : <RegisterForm handleDone={handleDone} type={form.role} />
          }
        </Box>
      </Modal>
    </div >
  );
}

const mapStateToProps = state => {
  return { auth: state.auth };
}
export default connect(mapStateToProps, { signIn, signOut })(BasicModal);