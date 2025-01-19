import axios from "axios";
import { useEffect, useState } from "react";
import "../css/card.css"
import Popup from "./Popup";

function Card(props) {
  const[isPopupOpen, setIsPopupOpen]=useState(false);
  const[commentData, setCommentData]=useState([]);

  const username = JSON.parse(localStorage.getItem('username'));
    const toggleLike = (id)=>{
        axios.post(`http://localhost:9090/post/like/${username}/${id}`)
                .then((result) => {
                    //setPostData(result.data);
                    props.refreshmethod();
                    
                }).catch((error) => {
                    console.log(error);
                })
    }
    const toggleFollow = (targetUserName)=>{
      axios.post(`http://localhost:9090/user/follow/${username}/${targetUserName}`)
                .then((result) => {
                    //setPostData(result.data);
                    props.refreshmethod();
                }).catch((error) => {
                    console.log(error);
                })
    }

    const handleComments = (id)=>{
      console.log(`id `+id);
      setIsPopupOpen(true);
      axios.get(`http://localhost:9090/post/comment/get/${id}`)
        .then((result) => {
           setCommentData(id);
        }).catch((error) => {
            console.log(error);
        })
    }
    return (<>
      <Popup isOpen={isPopupOpen}
      commentData={commentData}
      onClose={()=>setIsPopupOpen(false)}/>
      <div className="post-card">
        <div className="post-header">
          <img src={props.data.user.imageName} alt="user" className="user-avatar"/>&nbsp;&nbsp;
          <span className="username"><b>{props.data.user.username}</b></span>&nbsp;&nbsp;{props.data.createdAt}ago
          
        </div>
        <img src={props.data.imageName} alt="post" className="post-image"/>
        
        <div className="post-details">
        <div className="post-caption">
          <span className="username">{props.data.caption}...</span>
        </div>
          <a type="button" className={`btn ${props.data.liked ?"btn-danger": "btn-outline-secondary"}`} onClick={()=>toggleLike(props.data.id)}><span className="likes">{props.data.noOfLikes}&nbsp;{props.data.liked ? "Liked" : "Like"}</span></a>&nbsp;&nbsp;&nbsp;
          {
            props.data.user.username === username ? <></>:
            <button className={`btn ${props.data.followed ?"btn-danger": "btn-outline-secondary"}`} onClick={()=>toggleFollow(props.data.user.username)}>{props.data.followed}&nbsp;{props.data.followed ? "Following" : "Follow"}</button>
            
          }
        <div>

          <span className="comments" onClick={()=>handleComments(props.data.id)}>{props.data.noOfComments}&nbsp;comments</span>&nbsp;&nbsp;&nbsp;
        </div>
        </div>
        
      </div></>
    )
}
export default Card;

/*

<div class="card profile-post" >
    <img class="card-img-top" src={props.data.imageUrl} alt="user post"/>
    <div class="card-body">
      <h5 class="card-title">{props.data.imageName}</h5>
      <p class="card-text">{props.data.createdAt}</p>
      <p class="card-text">{props.data.caption}</p>
    </div>
    <div className="card-footer">
    <button type="button" className={`btn ${props.data.like ?"btn-danger": "btn-outline-secondary"}`} onClick={()=>toggleLike(props.data.id)}>
        {props.data.like ? "Liked" : "Like"}</button> <span>{props.data.noOfLikes}</span>
    <button type="button" class="btn btn-success" style={{float:"right"}}>Follow</button>
    </div>
  </div>

*/