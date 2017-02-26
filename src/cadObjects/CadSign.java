/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadObjects;

import graphicsObjects.Point2D;
import java.time.LocalDate;

/**
 *
 * @author stanislav
 */
public class CadSign extends Point2D{
    private short type;
    private int number;
    private float rot;
    private float scale;
    private LocalDate begDate;
    private LocalDate endDate;

    public CadSign(short type) {
        setType(type);
    }

    public void setType(short type) {
        this.type = type;
    }
    
    
}
