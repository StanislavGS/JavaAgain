/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicsObjects;

import java.awt.Point;

/**
 *
 * @author stanislav
 */
public class Point2D {

    private long x;
    private long y;
    
    public Point2D() {
        this(0, 0);
    }

    /**
     * Constructs and initializes a point2D at the specified {@code (x,y)}
     * location in the coordinate space.
     *
     * @param x (long) the X coordinate in milimeters constructed
     * <code>Point2D</code>
     * @param y (long) the Y coordinate in milimeters constructed
     * <code>Point2D</code>
     */
    public Point2D(long x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs and initializes a point2D at the specified {@code (x,y)}
     * location in the coordinate space.
     *
     * @param x (float) the X coordinate in meters constructed
     * <code>Point2D</code>
     * @param y (float) the Y coordinate in meters constructed
     * <code>Point2D</code>
     */
    public Point2D(float x, float y) {
        this((long) (x * 1000 + 0.5), (long) (y * 1000 + 0.5));
    }

    /**
     * Constructs and initializes a point2D at the specified {@code (x,y)}
     * location in the coordinate space.
     *
     * @param x (double) the X coordinate in meters constructed
     * <code>Point2D</code>
     * @param y (double) the Y coordinate in meters constructed
     * <code>Point2D</code>
     */
    public Point2D(double x, double y) {
        this((long) (x * 1000 + 0.5), (long) (y * 1000 + 0.5));
    }

    /**
     * Constructs and initializes a point2D at the specified {@code (x,y)}
     * location in the coordinate space.
     *
     * @param x (int) the X coordinate in milimeters constructed
     * <code>Point2D</code>
     * @param y (int) the Y coordinate in milimeters constructed
     * <code>Point2D</code>
     */
    public Point2D(int x, int y) {
        this((long) x, (long) y);
    }
    
    public long getXmm() {
        return x;
    }
    
    public long getYmm() {
        return y;
    }
    
    public double getXm() {
        return ((double) getXmm()) / 1000;
    }
    
    public double getYm() {
        return ((double) getYmm()) / 1000;
    }
    
    public void setXmm(long x) {
        this.x = x;
    }
    
    public void setYmm(long y) {
        this.y = y;
    }
    
    public void setXm(double x) {
        setXmm((long) (x * 1000 + 0.5));
    }
    
    public void setYm(double y) {
        setYmm((long) (y * 1000 + 0.5));
    }

    public void moveTo(double x, double y) {
        setXm(x);
        setYm(y);
    }
}
