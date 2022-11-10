import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import SkipPreviousIcon from '@mui/icons-material/SkipPrevious';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import SkipNextIcon from '@mui/icons-material/SkipNext';
import { Link as RouterLink } from "react-router-dom";
import { CardActionArea } from '@mui/material';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import { useSelector, useDispatch } from 'react-redux';
import { addProduct, removeProduct } from '../reducers/wishlist-slice';

const ProductCard = ({ item }) => {
  const wishlist = useSelector(store => store.wishlist);
  const dispatch = useDispatch();
  const [likeStatus, setLikeStatus] = React.useState(false);
  const handleLike = () => {
    if (likeStatus) dispatch(removeProduct({ item: item.product_id }))
    else dispatch(addProduct({ ...item }));
    setLikeStatus(!likeStatus);
  }
  const getLikeStatus = () => {
    for (let items of wishlist.products) {
      // Check if the product already added before
      if (items.product_id === item.product_id) {
        setLikeStatus(true);
      }
    }
  }
  React.useEffect(() => {
    getLikeStatus();
  }, []);
  return (
    <Card sx={{ display: 'flex', mb: 1, justifyContent: 'space-between' }}>
      <CardActionArea sx={{ width: 151 }} component={RouterLink} to={`/product/${item.product_id}`}>
        <CardMedia
          component="img"
          sx={{ height: 151 }}
          src={item.product_images.length > 0 ? item.product_images[0].image : ""}
          alt={item.product_name}
        />
      </CardActionArea >
      <Box sx={{ display: 'flex', flexDirection: 'column' }}>
        <CardContent sx={{ flex: '1 0 auto' }}>
          <CardActionArea sx={{ width: 151 }} component={RouterLink} to={`/product/${item.product_id}`}>
            <Typography component="div" variant="h5">
              <p className='mb-6 text-xl line-clamp-3'>{item.product_name}</p>
            </Typography>
            <Typography variant="subtitle1" color="text.secondary" component="div">
              {item.brand}
            </Typography>
          </CardActionArea>
          <IconButton onClick={handleLike} color="primary" aria-label="add an alarm">
            {likeStatus ?
              <FavoriteIcon sx={{ p: 1, fontSize: 40 }} />
              :
              <FavoriteBorderIcon sx={{ p: 1, fontSize: 40 }} />
            }
          </IconButton>
        </CardContent>
        <Box sx={{ display: 'flex', alignItems: 'center', pl: 1, pb: 1 }}>
        </Box>
      </Box>
      <Box sx={{
        p: 2
      }}>
        < Typography variant="h5" >
          ₹ {item.MRP}
        </Typography>
      </Box>
    </Card >
  );
}

export default ProductCard;