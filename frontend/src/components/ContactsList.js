import * as React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';
import api from '../api';
import { useSelector } from 'react-redux';
import AddIcon from '@mui/icons-material/Add';
import { Typography } from '@mui/material';
import Stack from '@mui/material/Stack';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
  display: 'flex',
  flexDirection: 'column'
};


const ContactList = () => {
  const store = useSelector(store => store);
  const [form, setForm] = React.useState([]);
  const [open, setOpen] = React.useState(false);
  const [newContactNo, setNewEmail] = React.useState("");
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const getData = async () => {
    const { data } = await api.get(`/dashboard/profile`, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    })
    setForm(data.secondary_contact_no)
  }
  const handleDelete = async (ucid) => {
    const { data } = await api.delete(`/dashboard/profile/edit/deleteContact/${ucid}`, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    console.log(data);
    getData();
  };
  const handleNew = async () => {
    if (!newContactNo) return;
    const { data } = await api.post(`dashboard/profile/edit/addContact`, { contact_no: newContactNo }, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    getData();
    setNewEmail("");
    handleClose();
  };

  React.useEffect(() => {
    getData();
  }, []);

  return (
    <>
      <div style={{ display: 'flex' }}>
        <Stack direction="row">
          <Typography sx={{ ml: 2 }} variant="h6">
            Phone Numbers
          </Typography>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleOpen}
          >
            <AddIcon />
          </IconButton>
        </Stack>
      </div>
      <List sx={{ width: '100%', bgcolor: 'background.paper' }}>
        {form.map((value, index) => {
          const labelId = `checkbox-list-label-${value.ucid}`;

          return (
            <ListItem
              key={value.ucid}
              secondaryAction={
                <IconButton onClick={() => { handleDelete(value.ucid) }} edge="end" aria-label="comments">
                  <DeleteIcon />
                </IconButton>
              }

            >
              <ListItemButton role={undefined} dense>
                <ListItemIcon>
                </ListItemIcon>
                <ListItemText id={labelId} primary={value.contact_no} />
              </ListItemButton>
            </ListItem>
          );
        })}
      </List>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            New Phone Number
          </Typography>
          <TextField onKeyPress={(e) => {
            if (e.key === "Enter") handleNew();
          }} sx={{ mb: 1 }} onChange={(e) => setNewEmail(e.target.value)} value={newContactNo} id="outlined-basic-username" variant="outlined" />
          <Button onClick={handleNew} variant="contained">OK</Button>
        </Box>
      </Modal>
    </>
  );
}

export default ContactList;