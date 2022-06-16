import PropTypes from "prop-types";
import React, { Component } from "react";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import Divider from "@mui/material/Divider";
import ListItemText from "@mui/material/ListItemText";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import Typography from "@mui/material/Typography";
import "../css/events.css";
import Button from "@mui/material/Button";
import ApiRequests from "../utils/ApiRequests";
import { AiOutlineClose } from "react-icons/ai";
import { Col, Form, Modal, Row } from "react-bootstrap";

import TextField from "@mui/material/TextField";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import { format } from "date-fns";

const userProfile = JSON.parse(localStorage.getItem("user"));

export class Events extends Component {
  constructor(props) {
    super(props);
    this.state = {
      openCreateModal: false,
      startDateValue: new Date(),
      endDateValue: new Date(),
      name: "",
      isOnline: false,
      address: "",
      num_events: 0,
      events: [],
      city: 1,
      description: "",
    };
  }

  componentDidMount() {
    ApiRequests.getEvents()
      .then((result) => {
        console.log(result);
        this.setState({
          num_events: result.data.data.length,
          events: result.data.data,
        });

        console.log(result.data.data.length);
      })
      .catch((err) => {
        console.log(err.response);
      });
  }

  handleGetEvents = () => {
    ApiRequests.getEvents()
      .then((result) => {
        console.log(result);
        this.setState({
          num_events: result.data.data.length,
          events: result.data.data,
        });
      })
      .catch((err) => {
        console.log(err.response);
      });
  };

  timeOut(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }

  //isOnline: isOnline, address: address, startDateTime: new Date(), endDateTime: new Date()
  handleEventUpload = async (e) => {
    const {
      name,
      isOnline,
      address,
      startDateValue,
      endDateValue,
      city,
      description,
    } = this.state;

    console.log(name);
    console.log(isOnline);
    console.log(address);
    console.log(startDateValue);
    console.log(endDateValue);
    console.log(city);

    var formattedDate_1 = format(startDateValue, "MM-dd-yyyy H:mm");
    var formattedDate_2 = format(endDateValue, "MM-dd-yyyy H:mm");

    console.log(formattedDate_1);
    console.log(formattedDate_2);

    const formData = new FormData();
    formData.append("file", null);

    if (isOnline == false) {
      formData.append(
        "companyEventDto",
        new Blob(
          [
            JSON.stringify({
              name: name,
              address: address,
              isOnline: isOnline,
              startDateTime: formattedDate_1,
              endDateTime: formattedDate_2,
              cityId: city,
              description: description,
            }),
          ],
          { type: "application/json" }
        )
      );
    } else {
      formData.append(
        "companyEventDto",
        new Blob(
          [
            JSON.stringify({
              name: name,
              address: address,
              isOnline: isOnline,
              startDateTime: formattedDate_1,
              endDateTime: formattedDate_2,
              description: description,
            }),
          ],
          { type: "application/json" }
        )
      );
    }

    var flag = false;

    ApiRequests.createEvents(userProfile.id, formData)
      .then((res) => {
        console.log(res);
        flag = true;
      })
      .catch((err) => {
        console.log(err.response);
      });

    await this.timeOut(1500);

    if (flag == true) {
      this.setState({ openCreateModal: false });
      this.handleGetEvents();
    }
  };

  render() {
    const {
      openCreateModal,
      startDateValue,
      endDateValue,
      isOnline,
      num_events,
      events,
    } = this.state;
    return (
      <div className="events-page">
        <Modal
          className="home-post-modal"
          show={openCreateModal}
          backdrop="static"
          keyboard={false}
        >
          <Modal.Header>
            <Modal.Title> Etkinlik Oluşturun </Modal.Title>
            <button
              onClick={() => {
                this.setState({ openCreateModal: false });
              }}
              className="btn close-button"
            >
              <AiOutlineClose />
            </button>
          </Modal.Header>
          <Modal.Body>
            <Row>
              <label>Name:</label>
            </Row>
            <Row>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlTextarea1"
              >
                <Form.Control
                  as="input"
                  onChange={(e) => {
                    this.setState({ name: e.target.value });
                  }}
                  rows={3}
                />
              </Form.Group>
            </Row>
            <Row>
              <label>Online:</label>
            </Row>
            <Row>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlTextarea1"
              >
                <Form.Check
                  type="switch"
                  id="custom-switch"
                  label={isOnline == false ? "false" : "true"}
                  onChange={() => {
                    this.setState({ isOnline: !isOnline });
                  }}
                />
              </Form.Group>
            </Row>
            <Row>
              <label>Address:</label>
            </Row>
            <Row>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlTextarea1"
              >
                <Form.Control
                  as="input"
                  onChange={(e) => {
                    this.setState({ address: e.target.value });
                  }}
                  rows={3}
                />
              </Form.Group>
            </Row>

            {isOnline == false ? (
              <div>
                <Row>
                  <label>City:</label>
                </Row>
                <Row>
                  <Form.Group
                    className="mb-3"
                    controlId="exampleForm.ControlTextarea1"
                  >
                    <Form.Control
                      as="input"
                      onChange={(e) => {
                        this.setState({ city: e.target.value });
                      }}
                      rows={3}
                    />
                  </Form.Group>
                </Row>
              </div>
            ) : null}

            <Row>
              <label>Description:</label>
            </Row>
            <Row>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlTextarea1"
              >
                <Form.Control
                  as="input"
                  onChange={(e) => {
                    this.setState({ description: e.target.value });
                  }}
                  rows={3}
                />
              </Form.Group>
            </Row>

            <Row>
              <label>Start Date:</label>
            </Row>
            <Row>
              <LocalizationProvider dateAdapter={AdapterDateFns}>
                <DateTimePicker
                  renderInput={(props) => <TextField {...props} />}
                  label=""
                  value={startDateValue}
                  onChange={(newValue) => {
                    this.setState({ startDateValue: newValue });
                  }}
                />
              </LocalizationProvider>
            </Row>

            <Row>
              <label>End Date:</label>
            </Row>
            <Row>
              <LocalizationProvider dateAdapter={AdapterDateFns}>
                <DateTimePicker
                  renderInput={(props) => <TextField {...props} />}
                  label=""
                  value={endDateValue}
                  onChange={(newValue) => {
                    this.setState({ endDateValue: newValue });
                  }}
                />
              </LocalizationProvider>
            </Row>

            <Row className="mt-3">
              <Col xs={6}></Col>
              <Col xs={6}>
                <a
                  className="btn  event-page-modal-button"
                  onClick={(e) => {
                    this.handleEventUpload(e);
                  }}
                >
                  Yayınla
                </a>
              </Col>
            </Row>
          </Modal.Body>
        </Modal>

        <div className="events-page-left">
          <List sx={{ width: "100%", bgcolor: "background.paper" }}>
            <ListItem alignItems="flex-start">
              <ListItemText primary="Etkinlikler" />
              <Button
                onClick={() => {
                  this.setState({ openCreateModal: true });
                }}
                className="events-page-create-button"
              >
                Oluştur
              </Button>
            </ListItem>

            <ListItem alignItems="flex-start">
              <ListItemText primary={`${num_events} Etkinlik`} />
            </ListItem>

            <Divider variant="inset" component="li" />

            {events.map((event) => (
              <div>
                <ListItem alignItems="flex-start">
                  <ListItemAvatar>
                    <Avatar
                      alt="Cindy Baker"
                      src="/static/images/avatar/3.jpg"
                    />
                  </ListItemAvatar>
                  <ListItemText
                    primary={event.event.name}
                    secondary={
                      <React.Fragment>
                        <Typography
                          sx={{ display: "inline" }}
                          component="span"
                          variant="body2"
                          color="text.primary"
                        >
                          {event.event.startDateTime} -{" "}
                          {event.event.endDateTime}
                        </Typography>
                        <p>
                          {" "}
                          {event.event.isOnline == true
                            ? "Online"
                            : "Face-to-Face"}{" "}
                        </p>
                      </React.Fragment>
                    }
                  />
                </ListItem>
                <Divider variant="inset" component="li" />
              </div>
            ))}
          </List>
        </div>

        <div className="events-page-right">
          <div className="events-page-box">
            <Avatar alt="Remy Sharp" src={userProfile.profilePhotoUrl} />
          </div>
        </div>
      </div>
    );
  }
}

export default Events;
