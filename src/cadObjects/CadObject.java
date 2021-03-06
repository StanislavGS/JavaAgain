/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadObjects;

import dataObjects.Table;
import graphicsObjects.Contour;
import graphicsObjects.PLine2d;
import graphicsObjects.Point2D;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    private double[] ref;
    private Point2D[] window;
    private String cooType;
    private String contents;
    private String comment;

    private Map<Long, CadLinePoint> linePoints;
    private List<BasePoint> rgo;
    private Map<Integer, CadLine> lines;
    private Map<String, Contour> contours;
    private List<CadSign> signs;
    private List<CadText> texts;
    private Map<String, Table> tables;

    public CadObject() {
        this.ref = new double[2];
        this.window = new Point2D[2];
        linePoints=new TreeMap<>();
        rgo = new ArrayList<>();
        lines = new TreeMap<>();
        contours = new HashMap<>();
        signs = new LinkedList<>();
        texts = new LinkedList<>();
        tables = new TreeMap<>();
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

    public void setRef(double x, double y) {
        this.ref = new double[]{x, y};
    }

    public void setWindow(Point2D lowLeft, Point2D upRight) {
        this.window = new Point2D[]{lowLeft, upRight};
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

    public List<BasePoint> getRgo() {
        return rgo;
    }

    public Map<Integer, CadLine> getLines() {
        return lines;
    }

    public Map<String, Contour> getContours() {
        return contours;
    }

    public List<CadSign> getSigns() {
        return signs;
    }

    public List<CadText> getTexts() {
        return texts;
    }

    public Map<Long, CadLinePoint> getLinePoints() {
        return linePoints;
    }

    public Map<String, Table> getTables() {
        return tables;
    }
    
    
}
