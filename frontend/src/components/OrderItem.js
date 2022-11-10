import * as React from 'react';
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { Link as RouterLink } from "react-router-dom";
import { CardActionArea } from '@mui/material';
import Button from '@mui/material/Button';
import api from '../api';
import { useDispatch, useSelector } from 'react-redux';
import Stack from '@mui/material/Stack';
import { useNavigate } from 'react-router-dom';

const toDate = (d) => {
  var t = d.split(/[- T :]/);
  var n = new Date(Date.UTC(t[0], t[1] - 1, t[2]));
  return n.getDate() + '/' + (n.getMonth() + 1) + '/' + n.getFullYear()
}
const OrderItem = ({ order, getData }) => {
  console.log(order);
  const navigate = useNavigate();
  const store = useSelector(store => store);
  const theme = useTheme();
  const date = toDate(order.pl_order.date);
  const orderStatus = (status) => {
    if (status === 0) return "Waiting for confimation";
    else if (status === 1) return "Dispatched";
    else if (status === 2) return "Delivered";
    else return "Cancelled";

  }
  const cancelOrder = async () => {
    const { data } = await api.put(`orders/${order.pl_order.order_id}/cancel`, {}, {
      headers: {
        'user-id': store.auth.userId,
        'Content-Type': 'application/json'
      }
    });
    console.log(data);
    getData();
  }
  return (
    <Card sx={{ display: 'flex', mb: 1, justifyContent: 'space-between', height: 175 }}>
      <CardMedia
        component="img"
        sx={{ width: 151 }}
        // src={order.ord_prod.product_images[0].image}
        src={order?.ord_prod?.product_images[0]?.image ? order?.ord_prod?.product_images[0]?.image : ""}
        alt="Live from space album cover"
      />
      <Box sx={{ display: 'flex', flexDirection: 'column', justifyContent: 'space-around' }}>
        <Typography component="div" variant="h7">
          {`${order.ord_prod.brand} ${order.ord_prod.product_name} and more ...`}
        </Typography>
        <Button size="small" variant="contained" color="success">
          {orderStatus(order.pl_order.status)}
        </Button>
        <Box sx={{ display: 'flex', alignItems: 'center', pl: 1, pb: 1 }}>
        </Box>
      </Box>
      <Box sx={{ p: 1 }}>
        <Typography variant="h6">
          ₹ {order.total_price}
        </Typography>
        <Typography variant="h7">
          Ordered: {date}
        </Typography>
        <Stack direction="column" spacing={1}>
          <Button onClick={cancelOrder} variant="contained" disabled={(order.pl_order.status === 3 || order.pl_order.status === 2) ? true : false}>
            Request Cancel
          </Button>
          <Button onClick={() => navigate(`/order/${order.pl_order.order_id}`)} variant="contained">
            View Details
          </Button>
        </Stack>
      </Box>
    </Card>
  );
}

export default OrderItem;