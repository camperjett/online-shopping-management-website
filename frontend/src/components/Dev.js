import Button from '@mui/material/Button';
import { useEffect, useState } from 'react';
import api from '../api';

const MyButton = () => {
  const [images, setImages] = useState([]);
  const readFiles = async (file, index) => {
    console.log("call");
    const reader = new FileReader();
    reader.onloadend = () => {
      setImages([...images, reader.result]);
    };
    reader.readAsDataURL(file);
  };

  const handleImages = (e) => {
    const files = [...e.target.files];
    files.map(async (file, index) => {
      await readFiles(file, index);
    });
  };

  const handleUpload = async () => {
    console.log(images);
    const payload = { id: 3, image: images[0] };
    const { data } = await api.post(`/auth/dev`, payload, {
      headers: {
        "Content-Type": "application/json"
      }
    });
  }
  useEffect(() => {
    console.log(images);
  }, [images]);

  return (
    <div className="">
      <Button
        variant="contained"
        component="label"
      >
        Upload File
        <input
          onChange={handleImages}
          style={{ display: "none" }}
          type="file"
          name="file"
          id="files"
        />
      </Button>
      <Button onClick={handleUpload}>Upload</Button>
      {images[0]}
      {images.map(item => <img src={item} />)}
    </div>
  );
}

export default MyButton;