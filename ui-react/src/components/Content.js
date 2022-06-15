import { BrowserRouter, Route, Switch } from "react-router-dom";
import Home from "./Home";
import Login from "./Login";
import Register from "./Register";
import React, { Component } from "react";
import PrivateRoute from "../utils/PrivateRoute";
import Profile from "./Profile";
import Header from "./Header";
import Utils from "../utils/Utils";
import PublicRoute from "../utils/PublicRoute";
import Events from "./Events";
import JobAdverts from "./JobAdverts";
import Friends from "./Friends";

export default class Content extends Component {
  render() {
    return (
      <div className="content-page">
        {Utils.displayToken() ? (
          <div className="home-nav">
            <Header />
          </div>
        ) : null}
        <BrowserRouter>
          <Switch>
            <PublicRoute exact path="/login" component={Login} />
            <PrivateRoute exact path="/" component={Home} />
            <PublicRoute exact path="/register" component={Register} />
            <PrivateRoute exact path="/profile" component={Profile} />
            <PrivateRoute exact path="/events" component={Events} />
            <PrivateRoute exact path="/jobs" component={JobAdverts} />
            <PrivateRoute exact path="/friends" component={Friends} />
          </Switch>
        </BrowserRouter>
      </div>
    );
  }
}
