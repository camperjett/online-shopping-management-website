import { BrowserRouter, Route, Router, Routes } from 'react-router-dom';
import Category from './components/Category';
import Navbar from './components/Navbar';
import { routes } from './routes';
import Checkout from "./components/Checkout";
import { connect } from 'react-redux';
import { signIn, signOut } from './actions';

const App = (props) => {
  return (
    <div className="">
      <Navbar />
      <Routes>
        {
          routes.map((route) => <Route
            exact path={route.path}
            element={route.component}
            key={route.path}
          />)
        }
      </Routes>
    </div>
  );
}

// filter what data from store gets passed to this component
const mapStateToProps = state => {
  console.log(state);
  return state;
}
// second argument tells us which actions are used in this...
// leave second arg blank to get dispatch function, which can be called as
// props.dispatch(action())
export default connect(mapStateToProps, { signIn, signOut })(App);
