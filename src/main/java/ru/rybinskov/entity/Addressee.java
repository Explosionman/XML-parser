package ru.rybinskov.entity;

public class Addressee {
    String addressee;

    public Addressee(String addressee) {
        this.addressee = addressee;
    }

    public String getAddressee() {
        return addressee;
    }

    @Override
    public String toString() {
        return "Addressee{" +
                "addressee='" + addressee + '\'' +
                '}';
    }
}
