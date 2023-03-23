package com.example.assignment.model;

public class Lop {
    private String tenlop;
    private String malop;
    private String avatar_lop;

    public Lop(String malop,String tenlop ,String avatar_lop) {
        this.tenlop = tenlop;
        this.malop = malop;
        this.avatar_lop = avatar_lop;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public String getAvatar_lop() {
        return avatar_lop;
    }

    public void setAvatar_lop(String avatar_lop) {
        this.avatar_lop = avatar_lop;
    }
}
