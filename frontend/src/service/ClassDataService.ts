import http from "./axios.service";

class ClassDataService {
  getAllClass() {
    return http.get("/class");
    }
}

export default new ClassDataService();