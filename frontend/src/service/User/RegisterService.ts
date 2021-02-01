import http from "../axios.service";

class RegisterService {
    pushUser(formData: object) {
        //console.log(formData);
        return http.post("/user/signup", formData);
    }
    loginStudent(formData: object) {
        //http.defaults.headers["access-token"] = window.localStorage.getItem("access-token");
        //console.log(formData);
        return http.post("user/login", formData);
    }
}

export default new RegisterService();