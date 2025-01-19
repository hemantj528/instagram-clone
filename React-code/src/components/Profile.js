import axios from "axios";
import { useEffect, useState } from "react";
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Image from 'react-bootstrap/Image';
import Row from 'react-bootstrap/Row';
import '../css/profile.css';
import { Link } from "react-router-dom";
import Card from "./Card";

function Profile() {
  const [userData, setUserData] = useState("");
  const [postData, setPostData] = useState([]);

  const username = JSON.parse(localStorage.getItem('username'));
  useEffect(() => {
console.log('profile component')
    
      loadUserPost();
     
  }, []);

  function loadUserPost(){
    axios({
      method: "GET",
      url: `http://localhost:9090/user/profile/${username}`
    }).then((result) => {
      setUserData(result.data);
      setPostData(result.data.posts)
    })
      .catch(error => {
        console.log(error.data);
      })
  }

  return (
    <section className="profile-page">
      <div className="profile-header">
        <img src={userData.imageName} alt="profile" className="profile-avatar"/>
        <div className="profile-details">
          <h2>{userData.userName}</h2>
          <p>Bio: {userData.bio}</p>
          <div className="stats">
            <span>Posts: {userData.noOfPosts}</span>
            <span>Followers: {userData.noOfFollowers}</span>
            <span>Following: {userData.noOfFollowings}</span>
          </div>
        </div>
      </div>
      {postData.map((e) => 
        <Card data={e} refreshmethod={loadUserPost}/>
        )
        }
    </section>
  )
}
export default Profile;


/*

<div>
    <div className="profile-upper">
      <div className="profile-image-div">
        <img className="profile-image" src={userData.profilUrl} roundedCircle />
      </div>
      <div className="profile-userdata">
        <p>{userData.userName}</p>
        <p>Bio : {userData.bio}</p>
        <Link to="/editprofile">
          <button type="button" class="btn btn-primary btn-signup">EditProfile</button>
        </Link>
      </div>
    </div>
    <div className="profile-userposts">

        {postData.map((e) => 
        <Card data={e} refreshmethod={loadUserPost}/>
        )
        }
    </div>
      </div>

*/