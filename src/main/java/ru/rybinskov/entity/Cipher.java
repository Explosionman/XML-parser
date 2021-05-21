package ru.rybinskov.entity;

public class Cipher {
    private String cipher;

    public Cipher() {
    }

    public Cipher(String cipher) {
        this.cipher = cipher;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    @Override
    public String toString() {
        return "Cipher{" +
                "cipher='" + cipher + '\'' +
                '}';
    }
}
