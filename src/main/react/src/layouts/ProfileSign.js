import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import { Card, Dropdown, Image, Menu } from "semantic-ui-react";
import ApiRequests from "../utils/ApiRequests";

export default function ProfileSign() {

  const [user, setUser] = useState({});

  useEffect(()=>{
    // to get info of user in local storage
    const localRepoItems = localStorage.getItem('user');
    if (localRepoItems) {
      setUser(JSON.parse(localRepoItems)); 
  }
  },[])

  // function handleLogout() {
  //   ApiRequests.logOut()
  //     .then((res) => {
  //       console.log(res.data);
  //     })
  //     .catch((err) => {
  //       console.log(err.response);
  //     });
  //   localStorage.clear();
  // }

  return (
    <div>
  <Card
    image='https://avatars.githubusercontent.com/u/45689757?v=4'
    header={`${user.name +" "+ user.surname}`}
    meta={`${user.email}`}
    description={`You are in: ${user.createdAt}`}
  />
    </div>
  );
}
