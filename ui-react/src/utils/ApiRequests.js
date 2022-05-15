import axios from "axios";
import AuthToken from "./AuthToken";
import config from "../config.json";

const API_URL = config.API_URL;

class ApiRequests {
  checkUser(email, password) {
    console.log(API_URL);
    return axios.post(API_URL + "auths/login", {
      email: email,
      password: password,
    });
  }

  getUserInfo() {
    var id = localStorage.getItem("user")
      ? JSON.parse(localStorage.getItem("user")).id
      : null;
    console.log(id);
    return axios.get(API_URL + `api/users/${id}`, { headers: AuthToken() });
  }

  register(email, password, name, surname, pageUrl) {
    return axios.post(API_URL + "auths/register", {
      email: email,
      password: password,
      name: name,
      surname: surname,
      profileUrl: pageUrl,
    });
  }

  logOut() {
    return axios.post(API_URL + "auths/logout");
  }

  createJobPost(title, content, description, fileUrl) {
    console.log(AuthToken());
    console.log(
      axios.post(
        API_URL + "api/posts/16/job",
        {
          name: title,
          content: content,
          description: description,
          file_url: fileUrl,
        },
        { headers: AuthToken() }
      )
    );
    return axios.post(
      API_URL + "api/posts/16/job",
      {
        name: title,
        content: content,
        description: description,
        file_url: fileUrl,
      },
      { headers: AuthToken() }
    );
  }

  createEventPost(title, content, description, fileUrl) {
    console.log(AuthToken());
    console.log(
      axios.post(
        API_URL + "api/posts/1/event",
        {
          name: title,
          content: content,
          description: description,
          file_url: fileUrl,
        },
        { headers: AuthToken() }
      )
    );
    return axios.post(
      API_URL + "api/posts/1/event",
      {
        name: title,
        content: content,
        description: description,
        file_url: fileUrl,
      },
      { headers: AuthToken() }
    );
  }

  getFriends(id) {
    return axios.get(API_URL + "api/friendships/" + id, {
      headers: AuthToken(),
    });
  }

  getThreeUserPost(id) {
    return axios.get(API_URL + `api/posts/user/${id}/last3`, {
      headers: AuthToken(),
    });
  }
}

export default new ApiRequests();
