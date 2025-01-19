import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import UserPoast from './components/UserPosts';
import UploadPost from './components/UploadPost';
import Header from './components/Header';
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Login from './components/Login';
import EditProfile from './components/EditProfile';
import SignUp from './components/SignUp';
import axios from 'axios';
import Profile from './components/Profile';


axios.interceptors.request.use((request)=>{
  const token = JSON.parse(localStorage.getItem('jwt-token'));
  //console.log(token);
  if(request.url==="http://localhost:9090/user/login" || request.url==="http://localhost:9090/user/register"){
    return request;
  }

  console.log("headerrrr")
  request.headers.Authorization=`Bearer ${token}`;
  console.log(request);
  return request;

})
const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      { path: "/", element: <UserPoast/> },
      {
        path: "/feed",
        element: <Header/>
      },
      {
        path: "/add-post",
        element: <UploadPost/>
      },
      {
        path: "/profile",
        element: <Profile/>
      },
      {
        path: "/editprofile",
        element: <EditProfile/>
      }
      
    ],
  },{
    path: "/login",
    element: <Login/>,
  },
  {
    path: "/signup",
    element: <SignUp/>
  }
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
