import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import UploadButton from '../../utils/UploadButton'
import { useSelector, useDispatch } from 'react-redux';
import { UPLOAD_IMG_CLEAR, FORM_CHANGE } from '../../actions/types';
import api from '../../api';

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
};

const NewProductModal = ({ open, setOpen }) => {
  const dispatch = useDispatch();
  const { uploadUrl } = useSelector(store => store.cats);
  const store = useSelector(store => store);
  // const { form } = useSelector(store => store.cats);
  const [form, setForm] = React.useState({
    title: "",
    image_path: null,
  })
  const [images, setImages] = React.useState([]);
  const readFiles = async (file, index) => {
    const reader = new FileReader();
    reader.onloadend = () => {
      setImages([...images, reader.result]);
    };
    reader.readAsDataURL(file);
  };

  const handleImages = (e) => {
    const files = [...e.target.files];
    files.map(async (file, index) => {
      await readFiles(file, index);
    });
  };
  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });
  // const handleChange = (e) => dispatch({ type: FORM_CHANGE, payload: { ...form, [e.target.name]: e.target.value } })
  const handleSubmit = async () => {
    const imgs = {
      image:
      {
        image: images[0]
      }
    };
    console.log(imgs);
    const payload = [{ ...form, ...imgs }];
    const { data } = await api.post(`/category`, payload, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    console.log(data);
    dispatch({ type: UPLOAD_IMG_CLEAR });
    handleClose();
  }
  const handleClose = () => setOpen(false);
  return (
    <div>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2" gutterBottom>
            New Category
          </Typography>
          <div sx={{ display: 'flex' }}>
            <div>
              <TextField
                fullWidth
                id="outlined-name"
                label="Name"
                name="title"
                value={form.title}
                onChange={handleChange}
                margin="normal"
              />
            </div>
          </div>
          <Stack spacing={2} direction="row">
            <Button onClick={() => { handleClose(); }} variant="text">Cancel</Button>
            {/* <UploadButton /> */}
            <Button
              variant="contained"
              component="label"
            >
              Upload File
              <input
                onChange={handleImages}
                style={{ display: "none" }}
                type="file"
                name="file"
                id="files"
              />
            </Button>
            <Button onClick={handleSubmit} variant="contained">OK</Button>

          </Stack>
        </Box>
      </Modal>
    </div>
  );
}

export default NewProductModal;