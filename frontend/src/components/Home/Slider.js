import React from 'react';
import { Slide } from 'react-slideshow-image';
import 'react-slideshow-image/dist/styles.css';
import './slider.css';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import IconButton from '@mui/material/IconButton';

const styles = {
  nextArrow: <IconButton sx={{ m: 2 }} ><ArrowForwardIosIcon /></IconButton>,
  prevArrow: <IconButton sx={{ m: 2 }} ><ArrowBackIosIcon /></IconButton>,
  canSwipe: true,
  duration: 2000
}

const Slider = () => {
  const images = [
    "./slider-imgs/slider1.webp",
    "./slider-imgs/slider2.webp",
    "./slider-imgs/slider4.webp",
  ];

  return (
    <Slide {...styles}>
      <div className="each-slide-effect">
        <div style={{ 'backgroundImage': `url(${images[0]})`, 'backgroundPosition': 'center', 'backgroundRepeat': 'no-repeat' }}>
        </div>
      </div>
      <div className="each-slide-effect">
        <div style={{ 'backgroundImage': `url(${images[1]})`, 'backgroundPosition': 'center', 'backgroundRepeat': 'no-repeat' }}>
        </div>
      </div>
      <div className="each-slide-effect">
        <div style={{ 'backgroundImage': `url(${images[2]})`, 'backgroundPosition': 'center', 'backgroundRepeat': 'no-repeat' }}>
        </div>
      </div>
    </Slide>
  );
};

export default Slider;