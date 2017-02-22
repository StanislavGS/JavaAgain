/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicsObjects;

/**
 *
 * @author stanislav
 */
public class Vector2d {
    private Point2D point1;
    private Point2D point2;

    public Vector2d(Point2D point1, Point2D point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point2D getPoint1() {
        return point1;
    }

    public Point2D getPoint2() {
        return point2;
    }

    public void setPoint1(Point2D point1) {
        this.point1 = point1;
    }

    public void setPoint2(Point2D point2) {
        this.point2 = point2;
    }
    
    
    
}
