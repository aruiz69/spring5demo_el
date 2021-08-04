package com.example.demoEL.confTest;

import java.util.function.BinaryOperator;

public class Record{

   private String data1 = "Example1";
   private String data2 = "Example2";
   private String data3 = "Example3";

    public Record() {
    }

    public Record(String data1, String data2, String data3) {
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
    }

    public Record clone(){
        return new Record(this.data1, this.data2, this.data3);
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }


    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    @Override
    public String toString() {
        return "Record{" +
                "data1='" + data1 + '\'' +
                ", data2='" + data2 + '\'' +
                ", data3='" + data3 + '\'' +
                '}';
    }
}
