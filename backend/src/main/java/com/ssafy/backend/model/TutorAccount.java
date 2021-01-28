package com.ssafy.backend.model;
import lombok.Data;
import lombok.NonNull;

@Data
public class TutorAccount {
  @NonNull
  private String bankName;

  @NonNull
  private String accountNumber;

  public TutorAccount(){
    this.bankName = "등록안함";
    this.accountNumber = "등록안함";
  }
}