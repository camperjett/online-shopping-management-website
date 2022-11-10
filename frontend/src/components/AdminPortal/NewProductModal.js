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
import { useSnackbar } from 'notistack';
import MenuItem from '@mui/material/MenuItem';

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

const NewProductModal = ({ open, setOpen, form, setForm, isEdit }) => {
  const { enqueueSnackbar, closeSnackbar } = useSnackbar();
  const dispatch = useDispatch();
  const { uploadUrl } = useSelector(store => store.cats);
  const store = useSelector(store => store);
  const [cats, setCats] = React.useState([{
    value: 0,
    label: 'Please Select'
  }]);
  // const { form } = useSelector(store => store.cats);
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
    if (form.category_id === 0) {
      enqueueSnackbar("Please select a category", { variant: "warning" });
      return;
    }
    const imgs = {
      product_images: images.map(item => {
        return {
          image: item
        }
      })
    };
    const payload = { ...form, ...imgs };
    if (!isEdit) {
      const { data } = await api.post(`/products`, payload, {
        headers: {
          'user-id': store.auth.userId,
          'Content-Type': 'application/json'
        }
      });
      enqueueSnackbar(data, { variant: "success" });
    } else {
      const { data } = await api.put(`/admin/product/edit/${form.id}`, payload, {
        headers: {
          'user-id': store.auth.userId,
          'Content-Type': 'application/json'
        }
      });
      enqueueSnackbar(data, { variant: "success" });
    }
    console.log(images[0]);
    dispatch({ type: UPLOAD_IMG_CLEAR });
    handleClose();
  }
  const handleClose = () => setOpen(false);
  const getCats = async () => {
    const { data } = await api.get('/category');
    const mappedData = data.map(item => {
      return {
        value: item.cid,
        label: item.title
      }
    });
    setCats([...cats, ...mappedData]);
  }
  React.useEffect(() => {
    getCats();
  }, []);
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
            {!isEdit ? `New Product` : `Edit Product`}
          </Typography>
          <div sx={{ display: 'flex' }}>
            <div>
              <TextField
                fullWidth
                id="outlined-name"
                label="Name"
                name="product_name"
                value={form.product_name}
                onChange={handleChange}
                margin="normal"
              />
              <TextField
                fullWidth
                id="outlined-name"
                label="Brand"
                name="brand"
                value={form.brand}
                onChange={handleChange}
                margin="normal"
              />
              <TextField
                fullWidth
                id="outlined-name"
                label="Description"
                name="description"
                value={form.description}
                onChange={handleChange}
                margin="normal"
              />
              <TextField
                fullWidth
                id="outlined-number"
                label="Stock"
                type="number"
                name="stock"
                value={form.stock}
                onChange={handleChange}
                margin="normal"
              />
            </div>
            <div>
              <TextField
                fullWidth
                id="outlined-number"
                label="MRP"
                type="number"
                name="MRP"
                value={form.MRP}
                onChange={handleChange}
                margin="normal"
              />
              <TextField
                id="outlined-select-state"
                select
                label="Category"
                value={form.category_id}
                name="category_id"
                onChange={handleChange}
                fullWidth
                margin="normal"
              >
                {cats.map((option) => (
                  <MenuItem key={option.value} value={option.value}>
                    {option.label}
                  </MenuItem>
                ))}
              </TextField>
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