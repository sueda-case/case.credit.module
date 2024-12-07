package com.example.credit.module.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;

@Getter
@Setter
public class PaymentResponse implements Serializable {

       private int countOfPaidInstallment;
       private double totalAmount;
       private boolean isPaid;
       private String message;
       public int getCountOfPaidInstallment() {
              return countOfPaidInstallment;
       }

       public void setCountOfPaidInstallment(int countOfPaidInstallment) {
              this.countOfPaidInstallment = countOfPaidInstallment;
       }

       public double getTotalAmount() {
              return totalAmount;
       }

       public void setTotalAmount(double totalAmount) {
              this.totalAmount = totalAmount;
       }

       public boolean isPaid() {
              return isPaid;
       }

       public void setPaid(boolean paid) {
              isPaid = paid;
       }

       public String getMessage() {
              return message;
       }

       public void setMessage(String message) {
              this.message = message;
       }


}
