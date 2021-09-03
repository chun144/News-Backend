package com.chun.springboot.api.output;

import java.awt.image.BufferedImage;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Res {

    @JsonIgnore
    BufferedImage a;


    public BufferedImage getA() {
        return a;
    }
    
    public void setA(BufferedImage a) {
        this.a = a;
    }

    
}
