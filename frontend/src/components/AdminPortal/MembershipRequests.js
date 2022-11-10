import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { useSelector } from 'react-redux';
import api from '../../api';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import { useSnackbar } from 'notistack';

const toDate = (d) => {
  var t = d.split(/[- T :]/);
  var n = new Date(Date.UTC(t[0], t[1] - 1, t[2]));
  return n.getDate() + '/' + (n.getMonth() + 1) + '/' + n.getFullYear()
}

const columns = [
  { field: 'id', headerName: 'SNo.', width: 30 },
  { field: 'rid', headerName: 'Request Id', width: 30 },
  { field: 'username', headerName: 'Username' },
  {
    field: 'name', headerName: 'Full Name', width: 130,
    valueGetter: (params) => `${params.row.first_name} ${params.row.last_name}`
  },
  {
    field: 'gender', headerName: 'Gender', width: 70,
    valueGetter: params => (params.row.gender ? 'Male' : 'Female')
  },
  {
    field: 'email',
    headerName: 'Email',
    // width: 200,
  },
  {
    field: 'date_joined',
    headerName: 'Date joined',
    // width: 150
  },
  {
    field: 'isApproved',
    headerName: 'Status',
    valueGetter: params => {
      const { status } = params.row;
      if (status === 0) return 'Waiting';
      else if (status === 1) return 'Active Member';
      else return 'Declined';
    },
    width: 200
  }
];

const MembershipRequests = () => {
  const { enqueueSnackbar, closeSnackbar } = useSnackbar();
  const store = useSelector(store => store);
  const [list, setList] = React.useState([]);
  const [selectionModel, setSelectionModel] = React.useState([]);
  const getData = async () => {
    const { data } = await api.get(`admin/shopkeepers/tentative`, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    const mappedData = data.map((item, index) => {
      return {
        id: index,
        rid: item.sid,
        username: item.username,
        first_name: item.first_name,
        last_name: item.last_name,
        gender: item.gender,
        email: item.email,
        date_joined: toDate(item.date_joined),
        status: item.isApproved
      }
    });
    setList(mappedData);
  }
  const handleAction = async (action) => {
    const payload = selectionModel.map(id => {
      return {
        requestId: list[id].rid,
        status: action,
      }
    });
    console.log(payload);
    const { data } = await api.put('/admin/shopkeepers/action', payload, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    enqueueSnackbar(data, { variant: "success" });
    getData();
  }
  React.useEffect(() => {
    getData();
  }, []);
  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Membership Requests
      </Typography>
      <Stack sx={{ m: 1, display: 'flex', justifyContent: 'space-around' }} spacing={2} direction="row">
        <Button onClick={() => { handleAction(1) }} disabled={selectionModel.length === 0} variant="contained">Confirm MemberShip</Button>
        <Button onClick={() => { handleAction(2) }} disabled={selectionModel.length === 0} variant="contained">Cancel Request</Button>
      </Stack>
      <div style={{ height: 500, width: '100%' }}>
        <DataGrid
          rows={list}
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
export default MembershipRequests;
