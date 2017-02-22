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
public class Point3D extends Point2D{
    private int z;

    public Point3D() {
        super();
        setZmm(0);
    }

    public Point3D(int z, long x, long y) {
        super(x, y);
        setZmm(z);
    }

    public Point3D(double z, double x, double y) {
        super(x, y);
        setZm(z);
    }

    
    
    public void setZmm(int z) {
        this.z = z;
    }
    
    public void setZm(double z){
        setZmm((int) (z*1000+0.5));
    }
    
    
    
}
