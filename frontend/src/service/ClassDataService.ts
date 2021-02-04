import http from "./axios.service";

class ClassDataService {
  // 전체 강의 조회
  getAllClass() {
    return http.get("/class");
    }

  getOneClass(id: string) {
    return http.get(`/class/${id}`);
  }

  // 특정 강의의 전체 섹션 조회
  getAllSection(classId: string) {
    return http.get(`/section?classId=${classId}`);
  }

  // 회원정보 수정
  updateUserInfo(data: object) {
    return http.post("user/updateInfo/", data);
  }

  // 특정 강의 질의응답 조회
}

export default new ClassDataService();