import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { useEffect } from 'react';
import { Typography } from '@mui/material';
import { fetchCatProducts } from "../actions";
import { connect } from "react-redux";
import { styled } from '@mui/material/styles';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import ProductCard from './ProductCard';
import Container from '@mui/material/Container';

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

const Products = ({ catProducts, fetchCatProducts }) => {
  const { cid } = useParams();
  // TODO Filters need to be implemented on backend and frontend
  const [filters, setFilters] = useState({
    cid: cid,
  });

  useEffect(() => {
    fetchCatProducts(filters);
  }, [])

  return (
    <Container maxWidth="lg">
      <Typography variant="h3" gutterBottom>
        {catProducts.title}
      </Typography>

      <Grid container spacing={1}>
        <Grid item xs={3}>
          <Item>
            <Typography variant="h6" gutterBottom>
              Filters
            </Typography>

          </Item>
        </Grid>
        <Grid item xs={9}>
          <Item>{catProducts.products && catProducts.products.map(item => <ProductCard key={item.product_id} item={item} />)}</Item>
        </Grid>
      </Grid>
    </Container>
  );
}

const mapStateToProps = state => {
  return state.cats;
}
export default connect(mapStateToProps, { fetchCatProducts })(Products);