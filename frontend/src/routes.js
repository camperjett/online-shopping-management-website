import Category from './components/Category';
import Checkout from './components/Checkout';
import Dashboard from './components/Dashboard';
import Products from './components/Products';
import SearchResults from './components/SearchResults';
import Dev from './components/Dev';
import ProductDetail from './components/ProductDetail';
import PostOrder from './components/PostOrder';
import OrderDetails from './components/OrderDetails';
import WishListModal from './components/WishListModal';
import AdminPortal from './components/AdminPortal/AdminPortal';
import Home from './components/Home';

export const routes = [
  { path: '/', component: <Home /> },
  { path: '/cats', component: <Category /> },
  { path: '/dashboard', component: <Dashboard /> },
  { path: '/checkout', component: <Checkout /> },
  { path: '/category/:cid', component: <Products /> },
  { path: '/search/:term', component: <SearchResults /> },
  { path: '/product/:pid', component: <ProductDetail /> },
  { path: '/checkout', component: <Checkout /> },
  { path: '/post-order/:oid', component: <PostOrder /> },
  { path: '/dev', component: <Dev /> },
  { path: '/order/:oid', component: <OrderDetails /> },
  { path: '/admin', component: <AdminPortal /> },
]