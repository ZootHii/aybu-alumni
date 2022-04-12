import { Api } from "@mui/icons-material";
import React, { Component } from "react";
import ApiRequests from "../utils/ApiRequests";
import "../css/profile.css";
import Avatar from "@mui/material/Avatar";
import PhotoCamera from "@mui/icons-material/PhotoCamera";
import IconButton from "@mui/material/IconButton";
import { Button, Card } from "react-bootstrap";

import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import Divider from "@mui/material/Divider";
import ListItemText from "@mui/material/ListItemText";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Typography from "@mui/material/Typography";

const userLocal = JSON.parse(localStorage.getItem("user"));
export default class Profile extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: [],
      friends: [],
    };
  }
  componentDidMount() {
    ApiRequests.getUserInfo()
      .then((response) => {
        console.log(response);
        this.setState({ user: response.data.data });
      })
      .catch((error) => {
        console.log(error.response);
      });

    ApiRequests.getFriends(userLocal.id)
      .then((response) => {
        console.log(response);
        this.setState({ friends: response.data.data });
      })
      .catch((error) => {
        console.log(error.response);
      });
  }

  render() {
    const { user, friends } = this.state;
    return (
      <div className="profile-container">
        <div className="profile-container-information">
          <div className="profile-container-header">
            <img src={user.coverPhotoUrl} />
          </div>
          <div className="profile-container-body">
            <Avatar alt="Remy Sharp" src={user.profilePhotoUrl} />
            <IconButton aria-label="upload picture" component="span">
              <PhotoCamera />
            </IconButton>
            <Card style={{ width: "18rem" }}>
              <Card.Body>
                <Card.Title>{user.name + " " + user.surname}</Card.Title>
                <Card.Subtitle className="mb-2 text">
                  {user.headline}
                </Card.Subtitle>
                <Card.Subtitle className="mb-2 text-muted">
                  Ankara Yıldırım Beyazıt Üniversitesi
                </Card.Subtitle>
                <p>{user.department + " / " + user.grade}</p>
              </Card.Body>
            </Card>
          </div>
        </div>

        <div className="profile-container-friends">
          <List sx={{ width: "100%", maxWidth: 360, bgcolor: "white" }}>
            {friends.map((friend) => (
              <div className="friends-li">
                <ListItem alignItems="flex-start">
                  <ListItemAvatar>
                    <Avatar alt="Remy Sharp" src={friend.profilePhotoUrl} />
                  </ListItemAvatar>
                  <ListItemText primary={friend.name + " " + friend.surname} />
                </ListItem>
                <Divider variant="inset" component="li" />
              </div>
            ))}
          </List>
        </div>
      </div>
    );
  }
}
