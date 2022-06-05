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
  Stack,
  TextField,
} from "@mui/material";
import { Card, Button, Col, Row, Modal, ListGroup, Form } from "react-bootstrap";
import AddAPhotoIcon from "@mui/icons-material/AddAPhoto";
import { MdAddAPhoto } from "react-icons/md";
import { AiOutlinePaperClip } from "react-icons/ai";
import { BsFillPlayBtnFill } from "react-icons/bs";
import { AiOutlineClose } from "react-icons/ai";
import ImageIcon from '@mui/icons-material/Image';
import OndemandVideoIcon from '@mui/icons-material/OndemandVideo';



const userProfile = JSON.parse(localStorage.getItem("user"));

export class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      friends: [],
      posts: [],
      description: "",
      file: [],
      openPostDialog: false
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


    ApiRequests.getUserPosts().then(res => {
      console.log(res);
      this.setState({ posts: res.data.data });
    }).catch(err => {
      console.log(err.response);
    })
  }


  handleGetUserPosts = () => {
    ApiRequests.getUserPosts().then(res => {
      console.log(res);
      this.setState({ posts: res.data.data });
    }).catch(err => {
      console.log(err.response);
    })
  }

  timeOut(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }

  handlePostUpload = async (e) => {
    e.preventDefault();
    var flag = false;
    const { file, description } = this.state;

    const formData = new FormData();


    // https://stackoverflow.com/questions/59235491/react-ajax-request-with-multipart-file-and-json-data
    formData.append('file', null);
    formData.append('postDto', new Blob([JSON.stringify({ description: description })], { type: "application/json" }));

    console.log(file);
    console.log(description);

    ApiRequests.createUserPost(userProfile.id, formData).then(res => {
      console.log(res);
      flag = true;
      this.setState({ openPostDialog: false })
    }).catch(error => {
      console.log(error.response);
    })

    await this.timeOut(1000);

    if (flag == true) {
      this.handleGetUserPosts();
    }


  }

  render() {
    const { friends, openPostDialog, posts } = this.state;
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
                    onClick={() => { this.setState({ openPostDialog: true }) }}
                    // id="demo-helper-text-misaligned-no-helper"
                    label="Gönderi hazırla"
                  />
                </Col>
              </Row>
              <Modal
                className="home-post-modal"
                show={openPostDialog}
                backdrop="static"
                keyboard={false}
              >
                <Modal.Header>
                  <Modal.Title> Gönderi Oluşturun </Modal.Title>
                  <button
                    onClick={() => {
                      this.setState({ openPostDialog: false });
                    }}
                    className="btn close-button"
                  >
                    <AiOutlineClose />
                  </button>
                </Modal.Header>
                <Modal.Body>

                  <Row>
                    <ListGroup>
                      <ListGroup.Item>
                        <img src={userProfile.profilePhotoUrl} />
                        {userProfile.name + " " + userProfile.surname}
                      </ListGroup.Item>

                    </ListGroup>
                  </Row>

                  <Row className="mt-3">
                    <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                      <Form.Control as="textarea" onChange={(e) => { this.setState({ description: e.target.value }) }} placeholder="Ne hakkında konuşmak istiyorsun?" rows={3} />
                    </Form.Group>
                  </Row>

                  <Row className="mt-3">
                    <Col xs={6}>
                      <Stack className="stack-row" direction="row" spacing={1}>
                        <label className="custom-file-upload">
                          <input type="file" name="file" onChange={(e) => { this.setState({ file: e.target.files[0] }); console.log(e.target.files[0]) }} />
                          <IconButton color="primary" aria-label="delete">

                            <ImageIcon />
                          </IconButton>
                        </label>

                        <IconButton aria-label="delete" color="secondary">
                          <OndemandVideoIcon />
                        </IconButton>
                      </Stack>
                    </Col>
                    <Col xs={6}>
                      <a
                        className="btn home-post-modal-button"
                        onClick={(e) => { this.handlePostUpload(e) }}
                      >
                        Yayınla
                      </a>
                    </Col>
                  </Row>


                </Modal.Body>
              </Modal>

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

          {posts.map(post_item => (
            <Card className="home-post-card">
              <Card.Body>
                <Row>
                  <Col xs={2}>
                    {" "}
                    <Avatar alt="Remy Sharp" src={post_item.post.ownerUser.profilePhotoUrl} />

                  </Col>
                  <Col xs={10}>
                    <p className="name-p">
                      {post_item.post.ownerUser.name + " " + post_item.post.ownerUser.surname}
                    </p>
                    <span className="name-p">
                      {post_item.post.ownerUser.headline}
                    </span>
                  </Col>
                </Row>
                <Row className="mt-3">
                  <Col xs={2}>

                  </Col>
                  <Col xs={10}>
                    <p>
                      {post_item.post.description}
                    </p>
                  </Col>
                </Row>
              </Card.Body>
            </Card>
          ))}
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
