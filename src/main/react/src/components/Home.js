import React, { Component } from "react";
import ApiRequests from "../utils/ApiRequests";
import Navbar from "./Navbar"

export class Home extends Component {
  handleLogout = () => {
    ApiRequests.logOut().then(res => {
            console.log(res.data)
        }).catch(err => {
            console.log(err.response)
        })

    localStorage.clear();
    this.props.history.push('/login');
  };

  render() {
    return (
      <div>
        {/* <button
          type="submit"
          class="btn btn-primary logout-btn"
          onClick={this.handleLogout}
        >
          Logout
          
        </button> */
        }
        <Navbar/>
      </div>
    );
  }
}

export default Home;
