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
import { sk } from "date-fns/locale";

const userProfile = JSON.parse(localStorage.getItem("user"));
const skills = [
  { name: "Java", check: false },
  { name: "Java 17", check: false },
  { name: "OOP", check: false },
  { name: "PostgreSQL", check: false },
  { name: "SQL", check: false },
  { name: "React", check: false },
  { name: "Redux", check: false },
  { name: "Unity", check: false },
  { name: "Html", check: false },
  { name: "CSS", check: false },
];

export class JobAdverts extends Component {
  constructor(props) {
    super(props);
    this.state = {
      openCreateModal: false,
      startDateValue: new Date(),
      endDateValue: new Date(),
      name: 1,
      isOnline: false,
      address: "",
      num_jobs: 0,
      jobs: [],
      city: "",
      description: "",
      openModalDetail: false,
      selectedJobAdverts: 0,
      job_type: "",
      workplace_type: "",
      city_id: 0,
      job_skills: [],
    };
  }

  componentDidMount() {
    ApiRequests.getJobs()
      .then((res) => {
        console.log(res);
        this.setState({ jobs: res.data.data });
        this.setState({ num_jobs: res.data.data.length });
      })
      .catch((err) => {
        console.log(err.response);
      });
  }

  handleGetJobs = () => {
    ApiRequests.getJobs()
      .then((res) => {
        console.log(res);
        this.setState({ jobs: res.data.data });
        this.setState({ num_jobs: res.data.data.length });
      })
      .catch((err) => {
        console.log(err.response);
      });
  };

  timeOut(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }

  //isOnline: isOnline, address: address, startDateTime: new Date(), endDateTime: new Date()
  handleJobsUpload = async (e) => {
    e.preventDefault();
    const {
      description,
      name,
      job_type,
      job_skills,
      workplace_type,
      city_id,
      isOnline,
    } = this.state;
    console.log(description);
    console.log(name);
    console.log(job_type);
    console.log(workplace_type);
    console.log(city_id);

    console.log(skills);
    var skill_array = [];

    for (let index = 0; index < skills.length; index++) {
      if (skills[index].check == true) {
        skill_array.push(index + 1);
      }
    }

    console.log(skill_array);

    const formData = new FormData();
    formData.append("file", null);

    if (isOnline == false) {
      formData.append(
        "companyJobPostDto",
        new Blob(
          [
            JSON.stringify({
              jobTitleId: name,
              jobType: job_type,
              workplaceType: workplace_type,
              cityId: city_id,
              jobSkillsIds: skill_array,
              description: description,
              isOnline: isOnline,
            }),
          ],
          { type: "application/json" }
        )
      );
    } else {
      formData.append(
        "companyJobPostDto",
        new Blob(
          [
            JSON.stringify({
              jobTitleId: name,
              jobType: job_type,
              workplaceType: workplace_type,
              jobSkillsIds: skill_array,
              description: description,
              isOnline: isOnline,
            }),
          ],
          { type: "application/json" }
        )
      );
    }
    // console.log(description);

    var flag = false;

    ApiRequests.createJobs(userProfile.id, formData)
      .then((res) => {
        console.log(res);
        flag = true;
      })
      .catch((err) => {
        console.log(err.response);
      });

    await this.timeOut(1000);

    if (flag == true) {
      this.setState({ openCreateModal: false });
      this.handleGetJobs();
    }
  };

  render() {
    const {
      openCreateModal,
      startDateValue,
      endDateValue,
      isOnline,
      num_jobs,
      jobs,
      openModalDetail,
      selectedJobAdverts,
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
            <Modal.Title> İş İlanı Oluşturun </Modal.Title>
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
              <label>İş Adı:</label>
            </Row>
            <Row>
              <div class="form-group">
                <select
                  class="form-control"
                  id="exampleFormControlSelect1"
                  onChange={(e) => {
                    this.setState({ name: e.target.value });
                    console.log(e.target.value);
                  }}
                  value={1}
                >
                  <option value={1}> Software Developer</option>
                  <option value={2}> Full-Stack Developer</option>
                  <option value={3}> Frontend Developer</option>
                  <option value={4}> Backend Developer</option>
                  <option value={5}> Java Developer</option>
                  <option value={6}> React Developer</option>
                  <option value={7}> Database Developer</option>
                  <option value={8}> Game Developer</option>
                </select>
              </div>
            </Row>
            <Row>
              <label>İş Türü:</label>
            </Row>
            <Row>
              <div class="form-group">
                <select
                  class="form-control"
                  id="exampleFormControlSelect1"
                  onChange={(e) => {
                    this.setState({ job_type: e.target.value });
                    console.log(e.target.value);
                  }}
                  value="PART TIME"
                >
                  <option value="PART TIME"> Part-Time</option>
                  <option value="FULL TIME"> Full-Time</option>
                  <option value="INTERNSHIP"> Internship</option>
                  <option value="DAILY"> Daily</option>
                </select>
              </div>
            </Row>
            <Row>
              <label>İş Yeri Türü:</label>
            </Row>
            <Row>
              <div class="form-group">
                <select
                  class="form-control"
                  id="exampleFormControlSelect1"
                  onChange={(e) => {
                    this.setState({ workplace_type: e.target.value });
                    console.log(e.target.value);
                  }}
                  value="OFFICE"
                >
                  <option value="OFFICE"> Office</option>
                  <option value="HYBRID"> Hybrid</option>
                  <option value="REMOTE"> Remote</option>
                </select>
              </div>
            </Row>

            <Row>
              <label>Yetenekler:</label>
            </Row>
            <Row>
              {skills.map((skill, index) => (
                <div class=" ml-3 form-check form-check-inline">
                  <input
                    class="form-check-input skill-checkbox"
                    type="checkbox"
                    id="inlineCheckbox1"
                    value={index}
                    onClick={(e) => {
                      console.log(e.target.checked);
                      skill.check = e.target.checked;
                    }}
                  />
                  <label class="form-check-label" for="inlineCheckbox1">
                    {skill.name}
                  </label>
                </div>
              ))}
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

            {isOnline == false ? (
              <div>
                <Row>
                  <label>Şehir:</label>
                </Row>
                <Row>
                  <Form.Group
                    className="mb-3"
                    controlId="exampleForm.ControlTextarea1"
                  >
                    <Form.Control
                      as="input"
                      onChange={(e) => {
                        this.setState({ city_id: e.target.value });
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

            <Row className="mt-3">
              <Col xs={6}></Col>
              <Col xs={6}>
                <a
                  className="btn  event-page-modal-button"
                  onClick={(e) => {
                    this.handleJobsUpload(e);
                  }}
                >
                  Yayınla
                </a>
              </Col>
            </Row>
          </Modal.Body>
        </Modal>

        {jobs[selectedJobAdverts] == null ? null : (
          <Modal
            className="home-post-modal"
            show={openModalDetail}
            backdrop="static"
            keyboard={false}
          >
            <Modal.Header>
              <Modal.Title>
                {jobs[selectedJobAdverts].jobPost.jobTitle.name}
              </Modal.Title>
              <button
                onClick={() => {
                  this.setState({ openModalDetail: false });
                }}
                className="btn close-button"
              >
                <AiOutlineClose />
              </button>
            </Modal.Header>
            <Modal.Body>
              <p>Job Type: {jobs[selectedJobAdverts].jobPost.jobType} </p>
              <hr />

              <p>
                Workplace Type: {jobs[selectedJobAdverts].jobPost.workplaceType}{" "}
              </p>
              <hr />

              <p> Job Skills </p>
              <hr />
              {jobs[selectedJobAdverts].jobPost.jobSkills.map((item, index) => (
                <p>
                  {" "}
                  {index + 1}. {item.name}{" "}
                </p>
              ))}
              <hr />
              <p> Created at: {jobs[selectedJobAdverts].createdAt} </p>
            </Modal.Body>
          </Modal>
        )}
        <div className="events-page-left">
          <List sx={{ width: "100%", bgcolor: "background.paper" }}>
            <ListItem alignItems="flex-start">
              <ListItemText primary="İş İlanları" />
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
              <ListItemText primary={`${num_jobs} İş İlanı`} />
            </ListItem>

            <Divider variant="inset" component="li" />

            {jobs.map((event, index) => (
              <div>
                <ListItem
                  alignItems="flex-start"
                  className="job-lists-item"
                  onClick={() => {
                    this.setState({
                      selectedJobAdverts: index,
                      openModalDetail: true,
                    });
                    console.log(jobs[selectedJobAdverts].jobPost.jobTitle.name);
                    console.log(index);
                  }}
                >
                  <ListItemAvatar>
                    <Avatar
                      alt="Cindy Baker"
                      src="/static/images/avatar/3.jpg"
                    />
                  </ListItemAvatar>
                  <ListItemText
                    primary={event.jobPost.jobTitle.name}
                    secondary={
                      <React.Fragment>
                        <Typography
                          sx={{ display: "inline" }}
                          component="span"
                          variant="body2"
                          color="text.primary"
                        >
                          {event.jobPost.description}
                        </Typography>
                        <p>
                          {" "}
                          {event.jobPost.isOnline == true
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

export default JobAdverts;
