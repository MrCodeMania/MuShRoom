import http from "./axios.service";

class ClassDataService {
  getAllClass() {
    return http.get("/class");
    }

  getOneClass(id: string) {
    return http.get(`/class/${id}`);
  }
}

export default new ClassDataService();