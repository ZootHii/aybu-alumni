class Utils {
  displayToken() {
    return localStorage.getItem("Token") || null;
  }
}

export default new Utils();
