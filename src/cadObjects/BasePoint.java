/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadObjects;

import graphicsObjects.Point3D;
import java.time.LocalDate;

/**
 *
 * @author stanislav
 */
public class BasePoint extends Point3D{
    
    private byte _type;
    private int _number;
    private byte _classPozition;
    private byte _classElevation;
    private float _precizionX;
    private float _precizionY;
    private float _precizionZ;
    private byte _methodStabilization;
    private byte _methodMark;
    private byte _signNumber;
    private boolean _hasUndergroundMark;
    private String _oldNumber;
    private LocalDate _begDate;
    private LocalDate _endDate;

    public BasePoint(byte type ){
        setType(type);
    }

    public void setType(byte _type) {
        this._type = _type;
    }
    
    
    
}
