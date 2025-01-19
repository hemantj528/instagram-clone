import { useRef } from "react";
import axios from "axios";
import '../css/form.css';
import { Link, useNavigate } from "react-router-dom";

function Login() {

    const userName = useRef();
    const password = useRef();

    let usenavigate = useNavigate();

    const onSubmitHandler = (e) => {
        e.preventDefault();
        //console.log(userName.current.value);
        //console.log(password.current.value);


        axios.post('http://localhost:9090/user/login', {
            userName: userName.current.value,
            password: password.current.value
        }).then(result => {
            console.log(result.data);
            localStorage.setItem("username", JSON.stringify(userName.current.value))
            localStorage.setItem("jwt-token", JSON.stringify(result.data.jwtToken));
            //console.log(result.data.jwtToken);
            usenavigate("/")
        }).catch(error => {
            console.log(error);
            window.alert("Please login with correct credentials")
        })





    }

    return (
        <form className="container" onSubmit={onSubmitHandler}>
            <div className="form-container">
                <div class="mb-3 ">
                    <label for="exampleInputEmail1" class="form-label text-login">User Name</label>
                    <input type="text" ref={userName} class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="User Name" required="true" />
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword1" class="form-label text-login">Password</label>
                    <input type="password" ref={password} class="form-control" id="exampleInputPassword1" placeholder="Password" required="true" />
                </div>

                <button type="submit" class="btn btn-primary">Login</button>

                <Link to="/signup">
                    <button type="button" class="btn btn-primary btn-signup">SignUp</button>
                </Link>
            </div>
        </form>
    );
}
export default Login;