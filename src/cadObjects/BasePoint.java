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
final public class BasePoint extends Point3D{
    
    private byte _type;
    private int _number;
    private byte _classPosition;
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

    public BasePoint(byte _type, int _number, byte _classPozition, byte _classElevation, 
            float _precizionX, float _precizionY, float _precizionZ, byte _methodStabilization, 
            byte _methodMark, byte _signNumber, boolean _hasUndergroundMark, String _oldNumber,
            LocalDate _begDate, LocalDate _endDate, double x, double y, float z) {
        super(z, x, y);
        this.setType( _type);
        this.setNumber(_number);
        this.setClassPosAndElev(_classPozition, _classElevation);
        this.setPrecizionXYZ(_precizionX, _precizionY, _precizionZ);
        this.setMethodStabilization(_methodStabilization);
        this._methodMark = _methodMark;
        this._signNumber = _signNumber;
        this._hasUndergroundMark = _hasUndergroundMark;
        this._oldNumber = _oldNumber;
        this.setBegDate(_begDate);
        this.setEndDate(_endDate);
    }

    
    
    public void setType(byte _type) {
        this._type = _type;
    }

    public void setNumber(int _number) {
        this._number = _number;
    }

    public void setClassPosAndElev(byte _classPozition,byte _classElevation) {
        this._classPosition = _classPozition;
        this._classElevation = _classElevation;
    }

    public void setPrecizionXYZ(float _precizionX,float _precizionY,float _precizionZ) {
        this._precizionX = _precizionX;
        this._precizionY = _precizionY;
        this._precizionZ = _precizionZ;        
    }

    public void setMethodStabilization(byte _methodStabilization) {
        this._methodStabilization = _methodStabilization;
    }

    public void setBegDate(LocalDate _begDate) {
        this._begDate = _begDate;
    }

    public void setEndDate(LocalDate _endDate) {
        this._endDate = _endDate;
    }
    
    
    
    
}
