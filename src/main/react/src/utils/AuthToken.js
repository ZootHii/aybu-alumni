const AuthToken = () => {
    
    return {Authorization: localStorage.getItem("Token")}

}

export default AuthToken