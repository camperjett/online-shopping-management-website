import Addresses from "./Addresses";
import { Typography } from '@mui/material';
import IconButton from '@mui/material/IconButton'
import { useEffect, useState } from 'react';
import AddIcon from '@mui/icons-material/Add';
import AddressModal from './AddressModal';

const DashboardAddresses = () => {
  const [openAddressModal, setOpenAddressModal] = useState(false);
  const handleOpenAddressModal = () => setOpenAddressModal(!openAddressModal);
  return (
    <div>
      <div style={{ display: "flex" }}>
        <Typography variant="h5">
          Your Saved Address
        </Typography>
        <IconButton
          edge="start"
          color="inherit"
          aria-label="open drawer"
          sx={{ ml: 5 }}
          onClick={handleOpenAddressModal}
        >
          <AddIcon sx={{ fontSize: 30 }} />
        </IconButton>
      </div>
      <Addresses />
      <AddressModal type={"Add"} open={openAddressModal} setOpen={setOpenAddressModal} />
    </div>
  );
}
export default DashboardAddresses;