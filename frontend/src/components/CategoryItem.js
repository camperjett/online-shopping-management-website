import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Link } from "react-router-dom";
import { CardActionArea } from '@mui/material';
import { Link as RouterLink } from "react-router-dom";

const CategoryItem = ({ data }) => {
  return (
    <Card sx={{ maxWidth: 200 }}>
      <CardActionArea component={RouterLink} to={`/category/${data.cid}`}>

        <CardMedia
          component="img"
          width="10"
          image={data?.image ? data.image.image : ""}
          alt={data.title}
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {data.title}
          </Typography>
          {/* <Typography variant="body2" color="text.secondary">
          Lizards are a widespread group of squamate reptiles, with over 6,000
          species, ranging across all continents except Antarctica
        </Typography> */}
        </CardContent>
        <CardActions>
          <Button size="small">Explore {data.title}</Button>
        </CardActions>
      </CardActionArea>
    </Card>
  );
}
export default CategoryItem;



