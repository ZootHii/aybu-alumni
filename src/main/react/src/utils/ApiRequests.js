import axios from "axios";
import AuthToken from "./AuthToken";

const API_URL = 'http://localhost:4024/'

class ApiRequests {

    checkUser(email,password) {
        return axios.post(API_URL + 'auths/login', {email: email, password: password})
    }

    register(email, password, name, surname, pageUrl) {
        return axios.post(API_URL + 'auths/register', {email: email, password: password, name: name, surname: surname, profileUrl: pageUrl})
    }

    logOut() {
        return axios.post(API_URL + 'auths/logout')
    }

    createJobPost(title, content, description, fileUrl){
        console.log(AuthToken());
        console.log(axios.post(API_URL + 'api/posts/16/job',{name: title, content: content, description: description, file_url: fileUrl} , {headers: AuthToken()} ));
        return axios.post(API_URL + 'api/posts/16/job',{name: title, content: content, description: description, file_url: fileUrl} , {headers: AuthToken()} )
    }

    createEventPost(title, content, description, fileUrl){
        console.log(AuthToken());
        console.log(axios.post(API_URL + 'api/posts/1/event', {name: title, content: content, description: description, file_url: fileUrl}, {headers: AuthToken()}));
        return axios.post(API_URL + 'api/posts/1/event', {name: title, content: content, description: description, file_url: fileUrl}, {headers: AuthToken()})
    }

}

export default new ApiRequests();