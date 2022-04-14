import React, { Component } from "react";
import ApiRequests from "../utils/ApiRequests";
import "../css/home.css";
import {
  Avatar,
  Divider,
  IconButton,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  TextField,
} from "@mui/material";
import { Card, Button, Col, Row } from "react-bootstrap";
import AddAPhotoIcon from "@mui/icons-material/AddAPhoto";
import { MdAddAPhoto } from "react-icons/md";
import { AiOutlinePaperClip } from "react-icons/ai";
import { BsFillPlayBtnFill } from "react-icons/bs";

const userProfile = JSON.parse(localStorage.getItem("user"));

export class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      friends: [],
    };
  }
  handleLogout = () => {
    ApiRequests.logOut()
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err.response);
      });

    localStorage.clear();
    this.props.history.push("/login");
  };

  componentDidMount() {
    ApiRequests.getFriends(userProfile.id)
      .then((response) => {
        console.log(response);
        this.setState({ friends: response.data.data });
      })
      .catch((error) => {
        console.log(error.response);
      });
  }

  render() {
    const { friends } = this.state;
    return (
      <div className="home-container">
        <div className="home-container-left">
          <div className="profile-container-header">
            <img src={userProfile.coverPhotoUrl} />
          </div>
          <div className="profile-container-body">
            <Avatar alt="Remy Sharp" src={userProfile.profilePhotoUrl} />

            <Card style={{ width: "18rem" }}>
              <Card.Body>
                <Card.Title>
                  {userProfile.name + " " + userProfile.surname}
                </Card.Title>
                <Card.Subtitle className="mb-2 text-muted">
                  Ankara Yıldırım Beyazıt Üniversitesi
                </Card.Subtitle>
                {/* <p>{userProfile.department + " / " + userProfile.grade}</p> */}
              </Card.Body>
            </Card>
          </div>
        </div>
        <div className="home-container-middle">
          <Card>
            <Card.Body>
              <Row>
                <Col xs={2}>
                  {" "}
                  <Avatar alt="Remy Sharp" src={userProfile.profilePhotoUrl} />
                </Col>
                <Col xs={10}>
                  <TextField
                    // id="demo-helper-text-misaligned-no-helper"
                    label="Gönderi hazırla"
                  />
                </Col>
              </Row>

              <Row className="mt-3">
                <Col xs={12}>
                  <Button className="home-container-post-icons-btn">
                    <MdAddAPhoto size={20} color="#A8D4FF" />
                    Fotoğraf
                  </Button>
                  <Button className="home-container-post-icons-btn">
                    <BsFillPlayBtnFill size={20} color="#7FC15E" />
                    Video
                  </Button>
                  <Button className="home-container-post-icons-btn">
                    <AiOutlinePaperClip size={20} color="#70B5F9" />
                    Döküman
                  </Button>
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </div>
        <div className="home-container-right">
          <div className="profile-container-friends">
            <List sx={{ width: "100%", maxWidth: 360, bgcolor: "white" }}>
              {friends.map((item) => (
                <div className="friends-li">
                  <ListItem alignItems="flex-start">
                    <ListItemAvatar>
                      <Avatar alt="Remy Sharp" src={item.profilePhotoUrl} />
                    </ListItemAvatar>
                    <ListItemText primary={item.name + item.surname} />
                  </ListItem>
                  <Divider variant="inset" component="li" />
                </div>
              ))}
            </List>
          </div>
        </div>
      </div>
    );
  }
}

export default Home;
