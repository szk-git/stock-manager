import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import AuthService from "../../services/auth-service";

import {isRequired, isEmailFormat, isUsername, isFullname, isPassword} from "../../common/validator";

export default class Register extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: "",
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      message: "",
      loading: false
    };
  }

  onChangeUsername = (e) => {
    this.setState({
      username: e.target.value
    });
  }

  onChangeFirstName = (e) => {
    this.setState({
      firstName: e.target.value
    });
  }

  onChangeLastName = (e) => {
    this.setState({
      lastName: e.target.value
    });
  }

  onChangeEmail = (e) => {
    this.setState({
      email: e.target.value
    });
  }

  onChangePassword = (e) => {
    this.setState({
      password: e.target.value
    });
  }

  handleRegister = (e) => {
    e.preventDefault();

    this.setState({
      message: "",
      loading: true
    });

    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      AuthService.register(
        this.state.username,
        this.state.firstName,
        this.state.lastName,
        this.state.email,
        this.state.password
      ).then(response => {
          this.setState({
            message: response.data.message,
            loading: false
          });
          this.props.history.push("/login");
        }, error => {
          const resMessage = error.toString();
          this.setState({
            message: resMessage
          });
        }
      );
    }else{
      this.setState({
        loading: false
      });
    }
  }

  render() {
    return (
      <div className="col-md-12">
        <div className="card card-container">
          <img src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" alt="profile-img" className="profile-img-card" />

          <Form onSubmit={this.handleRegister} ref={c => { this.form = c; }} >
              <div>
                <div className="form-group">
                  <label htmlFor="username">Username</label>
                  <Input type="text" className="form-control" name="username" value={this.state.username} onChange={this.onChangeUsername} validations={[isRequired, isUsername]} />
                </div>

                <div className="form-group">
                  <label htmlFor="firstName">First name</label>
                  <Input type="text" className="form-control" name="firstName" value={this.state.firstName} onChange={this.onChangeFirstName} validations={[isRequired, isFullname]} />
                </div>

                <div className="form-group">
                  <label htmlFor="lastName">Last name</label>
                  <Input type="text" className="form-control" name="lastName" value={this.state.lastName} onChange={this.onChangeLastName} validations={[isRequired, isFullname]} />
                </div>

                <div className="form-group">
                  <label htmlFor="email">Email</label>
                  <Input type="text" className="form-control" name="email" value={this.state.email} onChange={this.onChangeEmail} validations={[isRequired, isEmailFormat]} />
                </div>

                <div className="form-group">
                  <label htmlFor="password">Password</label>
                  <Input type="password" className="form-control" name="password" value={this.state.password} onChange={this.onChangePassword} validations={[isRequired, isPassword]} />
                </div>

                <div className="form-group">
                  <button className="btn btn-primary btn-block">
                    {this.state.loading && (
                      <span className="spinner-border spinner-border-sm"></span>
                    )}
                    <span>Register</span>
                  </button>
                </div>
              </div>


            {this.state.message && (
              <div className="form-group">
                <div className={this.state.successful ? "alert alert-success" : "alert alert-danger"} role="alert">
                  {this.state.message}
                </div>
              </div>
            )}
            <CheckButton style={{ display: "none" }} ref={c => { this.checkBtn = c; }} />
          </Form>
        </div>
      </div>
    );
  }
}
