import React, { Component } from "react";

import ApiRequests from "../utils/ApiRequests";
import { FiLogIn } from "react-icons/fi";
import { MdOutlineLock } from "react-icons/md";
import AybuLogo from "../pics/alumni-logo-white.png";
export class Register extends Component {
  constructor(props) {
    super(props);

    this.state = {
      user: [],
      email: "",
      password: "",
      pageUrl: "",
      name: "",
      surname: "",
      error: [],
    };
  }

  handleRegister = async (e) => {
    e.preventDefault();
    console.log(this.state.email);
    console.log(this.state.password);
    console.log(this.state.pageUrl);
    console.log(this.state.name);
    console.log(this.state.surname);

    ApiRequests.register(
      this.state.email,
      this.state.password,
      this.state.name,
      this.state.surname,
      this.state.pageUrl
    )
      .then((response) => {
        console.log(response);
        this.props.history.push("/login");
      })
      .catch((err) => {
        console.log(err.response);

        var x = document.getElementById("email");
        var y = document.getElementById("password");
        var z = document.getElementById("pageUrl");

        if (err.response.data.data.email) {
          x.value = "";
          x.classList.add("error-input");
        }
        if (err.response.data.data.password) {
          y.value = "";
          y.classList.add("error-input");
        }

        if (err.response.data.data.profileUrl) {
          z.value = "";
          z.classList.add("error-input");
        }

        this.setState({ error: err.response.data.data });
      });

    await this.timeOut(1000);

    console.log(this.state.error);
  };

  timeOut(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }

  handleBack = () => {
    this.props.history.push("/login");
  };

  render() {
    const { error, email, password, name, surname, pageUrl } = this.state;

    return (
      <div className="page-box">
        <div className="left-box">
          <div className="img-box">
            <img src={AybuLogo} alt="" />
          </div>

          <div className="left-text-box">
            <div className="left-text-content">
              <h2>
                Ankara Yıldırım Beyazıt Üniversitesi Mezun Bilgi Sistemine
                Hoşgeldiniz
              </h2>
              <p>
                Mezun Bilgi Sistemi, mezunlarımız ile üniversitemiz arasındaki
                bağı koparmamak için ve iletişimi korumaya yönelik oluşturulmuş
                bir interaktif iletişim aracıdır ve katılım tamamen ücretsizdir.
              </p>
            </div>
          </div>

          <div className="footer-box">
            <div className="footer-p-box">
              <p>© 2021 Ankara Yıldırım Beyazıt Üniversitesi Yazılım Grubu</p>
            </div>
          </div>
        </div>

        <div className="mobile-logo">
          <div className="mobile-img">
            <img src={AybuLogo} alt="" />
          </div>
        </div>

        <div className="right-box">
          <div className="login-container">
            <div class="card shadow">
              <div className="card-header">
                <h3 class="card-title">User Register</h3>
              </div>
              <div class="card-body">
                <p class="card-text">
                  <form>
                    <div className="row">
                      <div className="col-sm-12">
                        <label for="email" class="col-sm-2  col-form-label">
                          Email
                        </label>
                      </div>
                    </div>
                    <div class="row">
                      <div className="col-sm-12">
                        <input
                          type="email"
                          class={`form-control ${
                            error.email && email == "" ? "error-input" : ""
                          }`}
                          id="email"
                          autoComplete="off"
                          placeholder={`${
                            error.email ? `${error.email}` : "Email"
                          }`}
                          onChange={(e) => {
                            this.setState({ email: e.target.value });
                          }}
                        />
                      </div>
                    </div>

                    <div className="row">
                      <div className="col-sm-12">
                        <label for="password" class="col-sm-2 col-form-label">
                          Password
                        </label>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-sm-12">
                        <input
                          type="password"
                          class={`form-control ${
                            error.password && password == ""
                              ? "error-input"
                              : ""
                          }`}
                          id="password"
                          autoComplete="off"
                          placeholder={`${
                            error.password ? `${error.password}` : "Password"
                          }`}
                          onChange={(e) => {
                            this.setState({ password: e.target.value });
                          }}
                        />
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-sm-6">
                        <label for="email" class="col-sm-2 col-form-label">
                          Name
                        </label>
                      </div>
                      <div class="col-sm-6">
                        <label for="email" class="col-sm-2  col-form-label">
                          Surname
                        </label>
                      </div>

                      <div class="col-sm-6">
                        <input
                          type="text"
                          class={`form-control ${
                            error.name && name == "" ? "error-input" : ""
                          }`}
                          id="name"
                          autoComplete="off"
                          placeholder={`${
                            error.name ? `${error.name}` : "Name"
                          }`}
                          onChange={(e) => {
                            this.setState({ name: e.target.value });
                          }}
                        />
                      </div>
                      <div class="col-sm-6">
                        <input
                          type="text"
                          class={`form-control ${
                            error.surname && surname == "" ? "error-input" : ""
                          }`}
                          id="surname"
                          autoComplete="off"
                          placeholder={`${
                            error.surname ? `${error.surname}` : "Surname"
                          }`}
                          onChange={(e) => {
                            this.setState({ surname: e.target.value });
                          }}
                        />
                      </div>
                    </div>

                    <div className="row">
                      <div className="col-sm-12">
                        <label for="password" class="col-sm-2 col-form-label">
                          Username
                        </label>
                      </div>
                    </div>

                    <div class="row mb-4">
                      <div class="col-sm-12">
                        <input
                          type="text"
                          class={`form-control ${
                            error.profileUrl && pageUrl == ""
                              ? "error-input"
                              : ""
                          }`}
                          id="pageUrl"
                          autoComplete="off"
                          placeholder={`${
                            error.profileUrl
                              ? `${error.profileUrl}`
                              : "Username"
                          }`}
                          onChange={(e) => {
                            this.setState({ pageUrl: e.target.value });
                          }}
                        />
                      </div>
                    </div>
                    <button
                      type="submit"
                      class="btn login-button"
                      onClick={(e) => {
                        this.handleRegister(e);
                      }}
                    >
                      <FiLogIn size={23} /> Register
                    </button>
                    <button
                      type="submit"
                      class="btn login-button"
                      onClick={this.handleBack}
                    >
                      <MdOutlineLock size={23} /> Back
                    </button>
                  </form>
                </p>
              </div>
            </div>
          </div>
        </div>

        <div className="mobile-footer-box">
          <div className="m-footer">
            <p>© 2021 Ankara Yıldırım Beyazıt Üniversitesi Yazılım Grubu</p>
          </div>
        </div>
      </div>
    );
  }
}

export default Register;
