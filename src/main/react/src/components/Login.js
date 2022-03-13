import React, { Component } from "react";
import ApiRequests from "../utils/ApiRequests";
import { FiLogIn } from "react-icons/fi";
import AybuLogo from "../pics/alumni-logo-white.png";

export class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      user: [],
      email: "",
      password: "",
      error: "",
      is_visible: false,
      // is_success : "",
    };
  }
  handleRegister = () => {
    this.props.history.push("/register");
  };

  // isSuccessLogin = () => {
  //   this.setState({is_success : this.error.data.success})
  // };

  handleLogin = (e) => {
    e.preventDefault();

    this.props.history.push("/homepage");

    // console.log(this.state.email);
    // console.log(this.state.password);

    // ApiRequests.checkUser(this.state.email, this.state.password)
    //   .then((res) => {
    //     console.log(res);
    //     localStorage.setItem("Token", res.data.data.accessToken.token);
    //     this.setState({ user: res.data.data.user });
    //     localStorage.setItem("user", JSON.stringify(this.state.user));
    //     window.location.reload();
    //   })
    //   .catch((err) => {
    //     console.log(err.response.data.message);
    //     this.setState({ error: err.response.data.message });

    //     var x = document.getElementById("email");
    //     var y = document.getElementById("password");
    //     if (err.response.data.message) {
    //       x.value = "";
    //       x.classList.add("error-input");

    //       this.setState({ email: "", password: "" });
    //       y.value = "";
    //       y.classList.add("error-input");
    //     }
    //   });
  };
  

  render() {
    const { error, email, password } = this.state;
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
                <h3 class="card-title">User Login</h3>
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
                            error && email === "" ? "error-input" : ""
                          }`}
                          id="email"
                          placeholder={`${error ? `${error}` : "Email"}`}
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

                    <div class="row mb-4">
                      <div class="col-sm-12">
                        <input
                          type="password"
                          class={`form-control ${
                            error && password === "" ? "error-input" : ""
                          }`}
                          id="password"
                          placeholder={`${error ? `${error}` : "Password"}`}
                          onChange={(e) => {
                            this.setState({ password: e.target.value });
                          }}
                        />
                      </div>
                    </div>
                    <button
                      type="submit"
                      class="btn login-button"
                      onClick={this.handleLogin}
                    >
                      <FiLogIn size={23} /> Sign in
                    </button>
                    <button type="submit" class="btn login-button">
                      Forgot Password
                    </button>
                  </form>
                </p>
              </div>
              <div class="card-footer text-muted">
                <div className="row">
                  <div className="col-sm-12">
                    <p className="register-content ">
                      Do not you have an account yet?
                      <button
                        className="sign-up-btn"
                        onClick={() => {
                          this.handleRegister();
                        }}
                      >
                        Sign Up
                      </button>
                    </p>
                  </div>
                </div>
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

export default Login;
