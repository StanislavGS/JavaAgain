/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadObjects;

import graphicsObjects.Contour;
import graphicsObjects.PLine2d;
import graphicsObjects.Point2D;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private Point2D[] window;
    private String cooType;
    private String contents;
    private String comment;    
    
    List<BasePoint> rgo;
    List<CadLine> lines;
    List<Contour> contours;
    

    public CadObject() {
        this.ref = new double[2];
        this.window = new Point2D[2];
        rgo=new ArrayList<>();
        lines=new ArrayList<>();
        contours=new ArrayList<>();
        
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

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public void setRef(double x,double y) {
        this.ref = new double[]{x,y};
    }

    public void setWindow(Point2D lowLeft,Point2D upRight) {
        this.window = new Point2D[]{lowLeft,upRight};
    }

    public void setCooType(String cooType) {
        this.cooType = cooType;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
    
    
    
    
}
