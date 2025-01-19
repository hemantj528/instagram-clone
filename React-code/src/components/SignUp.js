import { useRef } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import '../css/form.css';

function SignUp() {

    const userName = useRef();
    const password = useRef();
    const phone = useRef();
    const email = useRef();

    let usenavigate = useNavigate();

    const onSubmitHandler=(e)=>{
        e.preventDefault();
        console.log(userName.current.value);
        console.log(password.current.value);

        axios.post('http://localhost:9090/user/register',{
            userName: userName.current.value,
            password:password.current.value,
            phone:phone.current.value,
            email:email.current.value
        }).then(result => {
            window.alert("Sign up successfull");
            usenavigate('/login');
            console.log(result.data);
        }).catch(error=>{
            window.alert("Entered username is already present, Kindly try with another username");
        })
        userName.current.value="";
        password.current.value="";

    }

    return (
        <form className="container" onSubmit={onSubmitHandler} >
            <div className="form-container">
            <div class="mb-3">
                <label for="exampleInputEmail1" class="form-label text-login">User Name</label>
                <input type="text" ref={userName} class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" required="true" placeholder="User Name"/>
            </div>
            <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label text-login">Password</label>
                <input type="password" ref={password} class="form-control" id="exampleInputPassword1" required="true" placeholder="Password"/>
            </div>
            <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label text-login">Phone</label>
                <input type="mobile" ref={phone} class="form-control" id="exampleInputPassword1" required="true" placeholder="Phone"/>
            </div>
            <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label text-login">Email</label>
                <input type="email" ref={email} class="form-control" id="exampleInputPassword1" required="true" placeholder="Email"/>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
            <Link to="/login">
            <button type="button" class="btn btn-primary btn-signup btn-signup">Login</button>
            </Link>
            </div>
        </form>
    );
}
export default SignUp;