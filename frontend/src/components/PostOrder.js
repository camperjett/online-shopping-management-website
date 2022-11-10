import { useParams } from "react-router-dom";
import { Card } from '@mui/material';
import { Typography } from '@mui/material';
import CelebrationIcon from '@mui/icons-material/Celebration';
import Orders from "./Orders";

const PostOrder = () => {
  const { oid } = useParams();
  return (
    <div>
      <Card sx={{ p: 5 }}>
        <Typography variant="h3" gutterBottom>
          Thank You! <CelebrationIcon fontSize="24px" />
        </Typography>
        <Typography variant="body1" gutterBottom>
          Your order has been placed. We have informed the admin about your order. It will be checked by the admin at
          the earliest!
        </Typography>
        <Orders />
      </Card>
    </div>
  );
}

export default PostOrder;