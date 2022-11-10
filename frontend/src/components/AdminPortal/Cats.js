import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { useSelector } from 'react-redux';
import api from '../../api';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import NewCatModal from './NewCatModal';

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
  { field: 'cid', headerName: 'id', width: 50 },
  { field: 'title', headerName: 'Name', width: 400 },
  { field: 'image_path', headerName: 'Image URL', width: 300 },
];

const Cats = () => {
  const [open, setOpen] = React.useState(false);
  const store = useSelector(store => store);
  const [orders, setOrders] = React.useState([]);
  const [selectionModel, setSelectionModel] = React.useState([]);
  const getData = async () => {
    const { data } = await api.get(`/category`, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    const mappedData = data.map((item, index) => {
      return {
        id: index,
        cid: item.cid,
        title: item.title,
        image_path: item.image_path,
        image: item.image
      }
    })
    console.log(mappedData);
    setOrders(mappedData);
  }
  const handleDelete = () => {
    // const {data} = api.delete()
  }
  const handleAdd = () => {
    console.log("Add new MOdal");
  }
  React.useEffect(() => {
    if (store.cats.uploadUrl) setOpen(!open);
    getData();
  }, []);
  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Categories
      </Typography>
      <Stack sx={{ m: 1, display: 'flex', justifyContent: 'space-around' }} spacing={2} direction="row">
        <Button onClick={() => { setOpen(!open) }} variant="contained">ADD NEW</Button>
        <Button onClick={handleDelete} disabled={selectionModel.length !== 1} variant="contained">DELETE</Button>
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
      <NewCatModal open={open} setOpen={setOpen} />
    </div>
  );
}
export default Cats;