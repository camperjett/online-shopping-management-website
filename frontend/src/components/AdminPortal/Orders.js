import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { useSelector } from 'react-redux';
import api from '../../api';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import { useSnackbar } from 'notistack';

const columns = [
  { field: 'id', headerName: 'SNo.', width: 30 },
  { field: 'oid', headerName: 'Order ID', width: 70 },
  { field: 'user_id', headerName: 'User Id', width: 130 },
  { field: 'price', headerName: 'Total Price', width: 130 },
  {
    field: 'status',
    headerName: 'Status',
    width: 200,
    valueGetter: (params) => {
      const code = params.row.status;
      if (code === 0) return 'Waiting for Confirmation';
      else if (code === 1) return 'Dispatched';
      else if (code === 2) return 'Delivered';
      else return 'Cancelled';
    }
  },
];

const Orders = () => {
  const { enqueueSnackbar, closeSnackbar } = useSnackbar();
  const store = useSelector(store => store);
  const [orders, setOrders] = React.useState([]);
  const [selectionModel, setSelectionModel] = React.useState([]);
  const getData = async () => {
    const { data } = await api.get(`/admin/orders/`, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    const mappedData = data.map((item, index) => {
      return {
        id: index,
        oid: item.pl_order.order_id,
        user_id: item.pl_order.user_id,
        price: `₹ ${item.total_price}`,
        status: item.pl_order.status
      }
    })
    setOrders(mappedData);
  }
  const handleAction = async (action) => {
    const payload = selectionModel.map(id => {
      return {
        order_id: orders[id].oid,
        old_status: orders[id].status,
        new_status: action
      }
    });
    const { data } = await api.put('/admin/orders/action', payload, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    })
    enqueueSnackbar(data, { variant: "success" });
    getData();
  }
  React.useEffect(() => {
    getData();
  }, []);
  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Orders
      </Typography>
      <Stack sx={{ m: 1, display: 'flex', justifyContent: 'space-around' }} spacing={2} direction="row">
        <Button onClick={() => { handleAction(1) }} disabled={selectionModel.length === 0} variant="contained">Confirm</Button>
        <Button onClick={() => { handleAction(3) }} disabled={selectionModel.length === 0} variant="contained">Cancel</Button>
        <Button onClick={() => { handleAction(2) }} disabled={selectionModel.length === 0} variant="contained">Delivered</Button>
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
    </div>
  );
}
export default Orders;
