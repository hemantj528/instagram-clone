import { Link, useNavigate } from "react-router-dom";
import "../css/header.css"

function Header() {

    let usenavigate = useNavigate();

    function userLogOUt() {
        localStorage.clear();
        usenavigate("/login");
    }


    return (
        <header className="header">
            <div className="logo">InstaClone</div>
            <nav className="nav">
                <Link to="/">
                    <a class="nav-link active" aria-current="page" href="#">Home</a></Link>
                <Link to="/add-post">
                    <a class="nav-link active" aria-current="page" href="#">Add Post</a></Link>
                <Link to="/profile">
                    <a class="nav-link active" aria-current="page" href="#">Profile</a></Link>
                    <button class="nav-link active nav-btn" aria-current="page" onClick={userLogOUt}>Logout</button>
            </nav>

        </header>
    )
}
export default Header;


/*

<nav class="navbar navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Navbar</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <Link to="/">
                            <a class="nav-link active" aria-current="page" href="#">Home</a></Link>
                    </li>
                    <li class="nav-item">
                        <Link to="/add-post">
                            <a class="nav-link active" aria-current="page" href="#">Add Post</a></Link>
                    </li>
                    <li class="nav-item">
                        <Link to="/profile">
                            <a class="nav-link active" aria-current="page" href="#">Profile</a></Link>
                    </li>
                    
                        <button class="nav-link active nav-btn" aria-current="page" onClick={userLogOUt}>Logout</button>
                </ul>
                    
            </div>
        </div>
    </nav>

*/
