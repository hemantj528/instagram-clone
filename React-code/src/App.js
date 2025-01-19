import './App.css';
import Login from './components/Login';
import 'bootstrap/dist/css/bootstrap.min.css'
import SignUp from './components/SignUp';
import EditProfile from './components/EditProfile';
import UploadPost from './components/UploadPost';
import UserPosts from './components/UserPosts';
import SideBar from './components/SideBar';
import { Outlet } from "react-router-dom";
import Header from './components/Header';


function App() {
  
  return (<div style-={{float: 'left'}}><div>

  <Header></Header>
  </div><div style={{width: '70%'}}>

  <Outlet></Outlet>
  </div>
  </div>)
}

export default App;
