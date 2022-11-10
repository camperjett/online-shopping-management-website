import { useEffect } from "react";
import { connect } from "react-redux";
import { fetchCategories } from "../actions";
import CategoryItem from "./CategoryItem";
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import { Typography } from '@mui/material';
import Container from '@mui/material/Container';

const Category = ({ cats, fetchCategories }) => {

  useEffect(() => {
    fetchCategories();
  }, []);

  return (
    <Container maxWidth="lg">
      <Typography variant="h3" gutterBottom>
        Categories
      </Typography>
      <Box sx={{ flexGrow: 1 }}>
        <Grid container
          direction="row"
          justifyContent="space-between"
          alignItems="center">
          {cats.map(cat => <Grid item xs={2} sm={4} md={4} key={cat.cid}><CategoryItem data={cat} key={cat.cid} /></Grid>)}
        </Grid>
      </Box>
    </Container>
  );
}
const mapStateToProps = state => {
  return { cats: state.cats.allCats };
}
export default connect(mapStateToProps, { fetchCategories })(Category);