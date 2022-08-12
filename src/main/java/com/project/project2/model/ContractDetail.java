package com.project.project2.model;

import java.time.LocalDate;

public class ContractDetail {
    private int id_contract_detail;
    private int id_contract;
    private int id_car;

    public ContractDetail(){

    }

    public ContractDetail(int id_contract_detail, int id_contract, int id_car) {
        this.id_contract_detail = id_contract_detail;
        this.id_contract = id_contract;
        this.id_car = id_car;
    }

    public int getId_contract_detail() {
        return id_contract_detail;
    }

    public void setId_contract_detail(int id_contract_detail) {
        this.id_contract_detail = id_contract_detail;
    }

    public int getId_contract() {
        return id_contract;
    }

    public void setId_contract(int id_contract) {
        this.id_contract = id_contract;
    }

    public int getId_car() {
        return id_car;
    }

    public void setId_car(int id_car) {
        this.id_car = id_car;
    }

    @Override
    public String toString() {
        return "ContractDetail{" +
                "id_contract_detail=" + id_contract_detail +
                ", id_contract=" + id_contract +
                ", id_car=" + id_car +
                '}';
    }
}
