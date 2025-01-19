import axios from "axios";
import { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../css/uploadfile.css"

function UploadPost() {
    //const caption = useRef();
    const [caption, setCaption] = useState();
    let usenavigate = useNavigate();


    const onSubmitHandler = (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append("imageFile", e.target.profileImage.files[0]);
        const username = JSON.parse(localStorage.getItem('username'));

        axios({
            method: "POST",
            url: `http://localhost:9090/post/image/upload/${username}/${caption}`,
            data: formData,
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }).then(result => {
            console.log("uploaded")
            window.alert('post uploaded successfully');
            usenavigate('/profile');
        }).catch(error => {
            console.log(error.data);
        })

    }

    return (
        <section className="upload-page">
            <form className="upload-form" onSubmit={onSubmitHandler}>
                <label htmlFor="image-upload">Upload Image:</label>
                <input type="file" id="image-upload" accept="image/*" name="profileImage"/>
                <label htmlFor="caption">Caption:</label>
                <textarea id="caption" rows="4" placeholder="write a caption..."
                value={caption}
                onChange={(e)=>setCaption(e.target.value)}></textarea>
                <button type="submit" className="upload-btn">Upload</button>
            </form>
        </section>
    );
}
export default UploadPost;



/*

<form className="container" onSubmit={onSubmitHandler}>
            <div className="form-container">
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label text-login">Caption</label>
                    <input type="text" ref={caption} class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="caption" />
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword1" class="form-label text-login">Upload Image</label>
                    <input type="file" accept="image/*" name="profileImage" class="form-control" id="exampleInputPassword1" />
                </div>
                <button type="submit" class="btn btn-primary btn-signup">Add Post</button>
            </div>
        </form>

*/