import axios from "axios";
import { useEffect, useState } from "react";
import Card from "./Card";

function UserPosts() {
    useEffect(()=>{
        getposts();
    },[])
    const [postData, setPostData] = useState([]);


    const username = JSON.parse(localStorage.getItem('username'));
    function loadUserPost(){
        axios.get(`http://localhost:9090/post/all/posts/${username}`)
        .then((result) => {
           
            setPostData(result.data);
            console.log(result.data)
        }).catch((error) => {
            console.log(error);
        })
      }

    function getposts() {
        
        axios.get(`http://localhost:9090/post/all/posts/${username}`)
            .then((result) => {
                setPostData(result.data);
                console.log("user data")
                console.log(result.data)
                
            }).catch((error) => {
                console.log(error);
            })

    }
  


    return (<div className="profile-userposts">

    {postData.map((e) => 
        <Card data={e}  refreshmethod={loadUserPost} />
    )
    
}
</div>);
}

export default UserPosts;