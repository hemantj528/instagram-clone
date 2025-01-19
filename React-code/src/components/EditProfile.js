import React, { useEffect, useRef, useState } from "react";
import axios from "axios";
import Profile from "./Profile";
import { Link, useNavigate } from "react-router-dom";
import '../css/profile.css';
function EditProfile(){
    const [userName, setUserName] = useState();
    const [phone, setPhone] = useState();
    const [email, setEmail] = useState();
    const [bio, setBio] = useState();

    let usenavigate = useNavigate();


    const username = JSON.parse(localStorage.getItem('username'));
    useEffect(() => {
  
      axios({
        method: "GET",
        url: `http://localhost:9090/user/profile/${username}`
      }).then((result) => {
        setUserName(result.data.userName);
        setEmail(result.data.email);
        setPhone(result.data.phone);
        setBio(result.data.bio);
      })
        .catch(error => {
          console.log(error.data);
        })
    }, []);
  
    const onSubmitHandler=(e)=>{
        e.preventDefault();
        
        const formData = new FormData();
        const userData = { userName: userName,
        phone: phone,
        email: email,
        bio: bio
        }
        formData.append("imageFile", e.target.profileImage.files[0]);
        formData.append('profileData', JSON.stringify(userData));

        for (const value of formData.values()) {
            console.log(value);
          }

        axios({
            method:"PUT",
            url:`http://localhost:9090/user/profile/update/${username}`,
            data: formData, 
            headers:{
                "Content-Type":"multipart/form-data"
            }
        }).then((result)=>{
            localStorage.setItem('username',JSON.stringify(userName));
            usenavigate('/profile');

        }).catch(error=>{
            window.alert(error.response.data.message)
            console.log(error);
        })
        
    }

    return(
        <form className="container" onSubmit={onSubmitHandler}>
            <div className="edit-profile-container">

        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">User Name</label>
            <input type="text" value={userName} onChange={(e)=>setUserName(e.target.value)} class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"/>
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Phone</label>
            <input type="mobile" value={phone} onChange={(e)=>setPhone(e.target.value)} class="form-control" id="exampleInputPassword1"/>
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Email</label>
            <input type="email" value={email} onChange={(e)=>setEmail(e.target.value)} class="form-control" id="exampleInputPassword1"/>
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Bio</label>
            <input type="text" value={bio} onChange={(e)=>setBio(e.target.value)} class="form-control" id="exampleInputPassword1"/>
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Upload Image</label>
            <input type="file" accept="image/*" name="profileImage" class="form-control" id="exampleInputPassword1"/>
        </div>
        <button type="submit" class="btn btn-primary">Edit</button>
        <Link to="/profile">
          <button type="button" class="btn btn-primary btn-signup">Cancel</button>
        </Link>
            </div>
    </form>
    );

}
export default EditProfile;