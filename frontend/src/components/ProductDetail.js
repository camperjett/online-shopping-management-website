import React, { useState, useEffect } from 'react';
import { Add, Remove } from '@mui/icons-material';
import { useParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { addProduct } from '../reducers/cart-slice';
import api from '../api';
import { Card } from '@mui/material';
import { Typography } from '@mui/material';
import Container from '@mui/material/Container';

const ProductDetail = () => {
  const { pid } = useParams();
  const dispatch = useDispatch();
  const [product, setProduct] = useState({});
  let [quantity, setQuantity] = useState(1);
  let [size, setSize] = useState('S');
  const getProduct = async () => {
    try {
      const url = `/products/id/${pid}`;
      const response = await api.get(url);
      setProduct(response.data);
    } catch (error) {
      console.log(error);
    }
  };
  const sizeChangeHandler = (e) => {
    setSize(e.target.value);
  };
  const addToCartHandler = () => {
    dispatch(addProduct({ product, size, quantity }));
  };
  useEffect(() => {
    getProduct();
  }, []);
  return (
    <Container maxWidth="lg">
      <section className='p-8 grid md:grid-cols-2 gap-8'>
        <div className='grow'>
          <img
            src={product?.product_images?.length > 0 ? product.product_images[0].image : ""}
            alt={product.product_name}
            className='w-full'
          />
        </div>
        <div className='grow'>
          <h2 className='text-4xl mb-6'>{`${product.brand} ${product.product_name}`}</h2>
          <p className='mb-6 text-xl'>Rating: {product.avg_rating}</p>
          <p className='mb-6 text-xl line-clamp-3'>{product.description}</p>
          <span className='block mb-6 text-5xl'>₹ {product.MRP}</span>
          <div className='grid sm:grid-cols-2 gap-4 mb-6'>
            {/* <div>
              <label htmlFor='' className='text-xl'>
                Size
              </label>
              <select onChange={sizeChangeHandler}>
                {product.size?.map((item) => (
                  <option key={item} value={item}>
                    {item}
                  </option>
                ))}
              </select>
            </div> */}
          </div>
          <div className='grid sm:grid-cols-2 gap-4 mb-6'>
            <div className='flex items-center justify-start'>
              <span
                className='cursor-pointer'
                onClick={() => {
                  quantity > 1 && setQuantity(quantity - 1);
                }}
              >
                <Remove />
              </span>
              <span className='mx-2 text-xl h-10 w-10 rounded-2xl border flex justify-center items-center'>
                {quantity}
              </span>
              <span
                className='cursor-pointer'
                onClick={() => {
                  setQuantity(quantity + 1);
                }}
              >
                <Add />
              </span>
            </div>
            <div>
              <button
                onClick={addToCartHandler}
                className='uppercase hover:bg-teal-700 hover:text-white transition ease-out duration-500 border-teal-700 border rounded p-4'
              >
                Add to cart
              </button>
            </div>
          </div>
        </div>
      </section>
      {/* Product Details card  */}
      <Card sx={{ p: 5 }}>
        <Typography variant="h4" gutterBottom>
          Product Details
        </Typography>
        <ul className="list-disc">
          <li>Brand: {product.brand}</li>
          <li>Average Rating: {product.avg_rating}</li>
          <li>Number of Reviews: {product.no_of_reviews}</li>
          <li>Available Stock: {product.stock}</li>
          <li>{product.description}</li>
        </ul>
      </Card>
      {/* Product FAQ */}
      {/* Product Reviews */}
    </Container>
  );
};

export default ProductDetail;
