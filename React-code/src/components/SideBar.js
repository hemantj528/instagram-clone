import { Link } from "react-router-dom";
function SideBar(){

    return(
        <div class="d-flex flex-column flex-shrink-0 bg-black" style={{width: '5%'}}>
    
    <ul class="nav nav-pills nav-flush flex-column mb-auto text-center">
      <li class="nav-item">
        <Link to="/">
        <span class="nav-link py-3 border-bottom">Home</span>
        </Link>
      </li>
      <li class="nav-item">
        <Link to="/feed">
        <span class="nav-link py-3 border-bottom">Feed</span>
        </Link>
      </li>
      <li class="nav-item">
        <Link to="/add-post">
        <span class="nav-link py-3 border-bottom">Add Post</span>
        </Link>
      </li>
      
    </ul>
    <div class="dropdown border-top">
      <a href="#" class="d-flex align-items-center justify-content-center p-3 link-dark text-decoration-none dropdown-toggle" id="dropdownUser3" data-bs-toggle="dropdown" aria-expanded="false">
        <img src="https://github.com/mdo.png" alt="mdo" width="24" height="24" class="rounded-circle"/>
      </a>
      <ul class="dropdown-menu text-small shadow" aria-labelledby="dropdownUser3" >
        <li><a class="dropdown-item" href="#">New project...</a></li>
        <li><a class="dropdown-item" href="#">Settings</a></li>
        <li><a class="dropdown-item" href="#">Profile</a></li>
        <li><hr class="dropdown-divider"/></li>
        <li><a class="dropdown-item" href="#">Sign out</a></li>
      </ul>
    </div>
  </div>
    );
}
export default SideBar;