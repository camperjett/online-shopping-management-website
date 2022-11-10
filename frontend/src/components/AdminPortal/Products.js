import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { useSelector } from 'react-redux';
import api from '../../api';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import NewProductModal from './NewProductModal';
import EditProductModal from './NewProductModal';
import { useSnackbar } from 'notistack';

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

const toDate = (d) => {
  var t = d.split(/[- T :]/);
  var n = new Date(Date.UTC(t[0], t[1] - 1, t[2]));
  return n.getDate() + '/' + (n.getMonth() + 1) + '/' + n.getFullYear()
}

const columns = [
  { field: 'id', headerName: 'SNo.', width: 30 },
  { field: 'pid', headerName: 'id', width: 30 },
  { field: 'name', headerName: 'Name', width: 300 },
  { field: 'price', headerName: 'MRP', width: 100 },
  {
    field: 'stock',
    headerName: 'Stock',
    width: 70,
  },
  {
    field: 'date_added',
    headerName: 'Date Added',
    width: 100,
  },
  {
    field: 'rating',
    headerName: 'Average Rating',
    width: 120,
  },
  {
    field: 'category_id',
    headerName: 'Category Code',
    width: 150,
  },
];

const initForm = {
  id: null,
  product_name: "",
  stock: 0,
  description: "",
  MRP: 0,
  brand: "",
  category_id: 0,
  photo_url: ""
};

const Products = () => {
  const { enqueueSnackbar, closeSnackbar } = useSnackbar();
  const [open, setOpen] = React.useState(false);
  const [editModal, setEditModal] = React.useState(false);
  const [form, setForm] = React.useState(initForm)
  const store = useSelector(store => store);
  const [orders, setOrders] = React.useState([]);
  const [selectionModel, setSelectionModel] = React.useState([]);
  const getData = async () => {
    const { data } = await api.post(`/products/all`, {}, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    const mappedData = data.map((item, index) => {
      return {
        id: index,
        pid: item.product_id,
        name: item.product_name,
        price: `₹ ${item.MRP}`,
        stock: item.stock,
        date_added: toDate(item.date_added),
        rating: item.avg_rating,
        category_id: item.category_id,
        description: item.description,
        photo_url: item.photo_url,
        MRP: item.mrp,
        brand: item.brand
      }
    })
    setOrders(mappedData);
  }
  const handleUpdate = async () => {
    const data = orders[selectionModel[0]];
    await setForm({
      ...form,
      product_name: data.name,
      stock: data.stock,
      description: data.description,
      MRP: data.MRP,
      brand: data.brand,
      category_id: data.category_id,
      photo_url: data.photo_url,
      id: data.pid
    }
    )
    await setEditModal(!editModal);
  }
  const handleDelete = async () => {
    const payload = selectionModel.map(id => orders[id].pid);
    const { data } = await api.post(`/admin/deleteProduct`, payload, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    enqueueSnackbar(data, { variant: "success" });
    getData();
  };
  React.useEffect(() => {
    if (store.cats.uploadUrl) setOpen(!open);
    getData();
  }, []);
  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Products
      </Typography>
      <Stack sx={{ m: 1, display: 'flex', justifyContent: 'space-around' }} spacing={2} direction="row">
        <Button onClick={() => { setForm(initForm); setOpen(!open); }} variant="contained">ADD NEW</Button>
        <Button onClick={handleDelete} disabled={selectionModel.length === 0} variant="contained">DELETE</Button>
        <Button onClick={handleUpdate} disabled={selectionModel.length !== 1} variant="contained">UPDATE</Button>
      </Stack>
      <div style={{ height: 500, width: '100%' }}>
        <DataGrid
          rows={orders}
          columns={columns}
          pageSize={8}
          rowsPerPageOptions={[8]}
          checkboxSelection
          onSelectionModelChange={(newSelectionModel) => {
            setSelectionModel(newSelectionModel);
          }}
          selectionModel={selectionModel}
        />
      </div>
      <NewProductModal isEdit={false} form={form} setForm={setForm} open={open} setOpen={setOpen} />
      <EditProductModal isEdit={true} form={form} setForm={setForm} open={editModal} setOpen={setEditModal} />
    </div>
  );
}
export default Products;