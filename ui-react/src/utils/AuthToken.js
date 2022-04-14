const AuthToken = () => {
  return { Access_Token: localStorage.getItem("Token") };
};

export default AuthToken;
