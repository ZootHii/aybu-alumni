import { Api } from "@mui/icons-material";
import React, { Component } from "react";
import ApiRequests from "../utils/ApiRequests";
import "../css/profile.css";
import Avatar from "@mui/material/Avatar";
import PhotoCamera from "@mui/icons-material/PhotoCamera";
import IconButton from "@mui/material/IconButton";
import { Card, ListGroup, Modal } from "react-bootstrap";
import { AiOutlineClose } from "react-icons/ai";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import Divider from "@mui/material/Divider";
import ListItemText from "@mui/material/ListItemText";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import GroupIcon from "@mui/icons-material/Group";
import Typography from "@mui/material/Typography";
import { Button } from "@mui/material";
import WorkIcon from "@mui/icons-material/Work";
import DraftsIcon from "@mui/icons-material/Drafts";

const userLocal = JSON.parse(localStorage.getItem("user"));
export default class Profile extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: [],
      userThreePosts: [],
      userContact: [],
      friends: [],
      communityPage: {},
      companyPage: {},
      showContactModal: false,
    };
  }
  componentDidMount() {
    ApiRequests.getUserInfo()
      .then((response) => {
        this.setState({
          user: response.data.data,
          userContact: response.data.data.userContactInfo,
        });
        this.setState({
          communityPage: response.data.data.communityPage.page,
        });
        this.setState({
          companyPage: response.data.data.companyPage.page,
        });
      })
      .catch((error) => {
        console.log(error.response);
      });

    ApiRequests.getThreeUserPost(userLocal.id).then((response) => {
      console.log(response.data.data);
      // this.setState({
      //   userThreePosts: response.data.data,
      // });
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
    const {
      user,
      friends,
      communityPage,
      companyPage,
      showContactModal,
      userContact,
    } = this.state;
    console.log(userContact.birthday);

    console.log(user.id);
    return (
      <div className="profile-container-main">
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

              <div className="profile-container-body-cards">
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

                    <p
                      style={{
                        fontSize: "18px",
                        display: "flex",
                        alignItems: "center",
                      }}
                    >
                      {" "}
                      <GroupIcon style={{ marginRight: "5px" }} />{" "}
                      {friends.length}
                    </p>

                    <Button
                      onClick={() => {
                        this.setState({ showContactModal: true });
                      }}
                      className="profile-container-body-button"
                    >
                      Contact
                    </Button>
                  </Card.Body>
                </Card>

                <Modal
                  className="profile-contact-modal"
                  show={showContactModal}
                  backdrop="static"
                  keyboard={false}
                >
                  <Modal.Header>
                    <Modal.Title>{user.name + " " + user.surname}</Modal.Title>
                    <button
                      onClick={() => {
                        this.setState({ showContactModal: false });
                      }}
                      className="btn"
                    >
                      {" "}
                      <AiOutlineClose />{" "}
                    </button>
                  </Modal.Header>
                  <Modal.Body>
                    <h5>Contact Information</h5>
                    <List
                      sx={{
                        width: "100%",
                        maxWidth: 360,
                        bgcolor: "background.paper",
                      }}
                    >
                      <ListItem>
                        <ListItemAvatar>
                          <Avatar
                            alt="Remy Sharp"
                            src="https://img-s1.onedio.com/id-61dfdad347fb326c10161890/rev-0/w-1200/h-600/f-jpg/s-48e57905ee60f88011380e671456e630cf1ce30d.jpg"
                          />
                        </ListItemAvatar>
                        <ListItemText
                          primary="Profile"
                          secondary={user.profileUrl}
                        />
                      </ListItem>

                      <ListItem>
                        <ListItemAvatar>
                          <Avatar>
                            <WorkIcon />
                          </Avatar>
                        </ListItemAvatar>
                        <ListItemText
                          primary="Website"
                          secondary={userContact.websiteUrl}
                        />
                      </ListItem>

                      <ListItem>
                        <ListItemAvatar>
                          <Avatar>
                            <DraftsIcon />
                          </Avatar>
                        </ListItemAvatar>
                        <ListItemText
                          primary="Email"
                          secondary={userContact.email}
                        />
                      </ListItem>
                    </List>
                  </Modal.Body>
                </Modal>

                <Card style={{ width: "18rem" }}>
                  <Card.Body>
                    <ListGroup>
                      <ListGroup.Item>
                        <img src={communityPage.logoPhotoUrl} />
                        {communityPage.name}
                      </ListGroup.Item>
                      <ListGroup.Item>
                        <img src={companyPage.logoPhotoUrl} />
                        {companyPage.name}
                      </ListGroup.Item>
                    </ListGroup>
                  </Card.Body>
                </Card>
              </div>
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
                    <ListItemText
                      primary={friend.name + " " + friend.surname}
                    />
                  </ListItem>
                  <Divider variant="inset" component="li" />
                </div>
              ))}
            </List>
          </div>
        </div>
        <div className="profile-container-body-about">
          <div className="profile-container-body-about-box">
            <h4>About</h4>
            <hr />
            <p>about alumni</p>
          </div>
        </div>

        <div className="profile-container-body-post">
          <div className="profile-container-body-post-box">
            <h4>Post</h4>
            <hr />
            <div className="post">
              <List
                sx={{
                  width: "100%",
                  maxWidth: 360,
                  bgcolor: "background.paper",
                }}
              >
                <ListItem>
                  <ListItemAvatar>
                    <Avatar
                      alt="Remy Sharp"
                      src="https://img-s1.onedio.com/id-61dfdad347fb326c10161890/rev-0/w-1200/h-600/f-jpg/s-48e57905ee60f88011380e671456e630cf1ce30d.jpg"
                    />
                  </ListItemAvatar>
                  <ListItemText primary="Profile" secondary={user.profileUrl} />
                </ListItem>
              </List>
              <p>about alumni</p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
