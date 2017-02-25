/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicsObjects;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author stanislav
 */
public class CadObject {

    private String ver;    
    private int eKATTE;
    private String name;
    private String program;
    private LocalDate date;
    private String firm;
    private double[] ref ;
    private double[] window;
    private String cooType;
    private String part;
    private String comment;    
    
    List<Contour> contours;

    public CadObject() {
        this.ref = new double[2];
        this.window = new double[4];
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public void seteKATTE(int eKATTE) {
        this.eKATTE = eKATTE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    
}
