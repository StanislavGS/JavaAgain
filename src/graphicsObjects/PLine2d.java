/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicsObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author stanislav
 */
public class PLine2d implements Iterable<Vector2d>{

    private List<Point2D> points;
    private short type;
    private double elevation;    
    private boolean isInContour;
    
    public PLine2d() {
        this.points=new ArrayList<>();
        setType((short) 0);
        setElevation(0);
        isInContour=false;
    }
    
    public PLine2d(short type){
        this.points=new ArrayList<>();
        setType(type);
        setElevation(0);
        isInContour=false;
    }
     
   
    @Override
    public Iterator<Vector2d> iterator() {
        Iterator<Vector2d> it = new Iterator<Vector2d>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex <(points.size()-1);
            }

            @Override
            public Vector2d next() {
                return new Vector2d(points.get(currentIndex),points.get(currentIndex++ +1));
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    public void setType(short type) {
        this.type = type;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    void addPoint(Point2D point){
        this.points.add(point);
    }

    public boolean isIsInContour() {
        return isInContour;
    }

    public void setIsInContour(boolean isInContour) {
        this.isInContour = isInContour;
    }
    
    int size(){
        return points.size();
    }
    
    void removePoint(int idx){
        if (size()==0 ){
            throw new IllegalArgumentException("Null length line!");
        }       
        
        points.remove(idx);
    }

    public List<Point2D> getPoints() {
        return points;
    }
    
    
}
