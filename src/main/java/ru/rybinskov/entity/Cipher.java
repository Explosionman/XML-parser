package ru.rybinskov.entity;

public class Cipher {
    private String cipher;

    public Cipher(String cipher) {
        this.cipher = cipher;
    }

    public String getCipher() {
        return cipher;
    }

    @Override
    public String toString() {
        return "Cipher{" +
                "cipher='" + cipher + '\'' +
                '}';
    }
}
