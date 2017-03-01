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
public class CadText {

    public enum Justify {
        LD, LC, LT, CD, CC, CT, RD, RC, RT
    }
    
    public enum TypeDescibedObject{
        P, L, C, S,  A
    }
    
    public enum GraphParam{
        AN ,SI,NU,LE,ХС,YC,HI,AR,LP,AD,ST,IO
    }
    private short _type;
    private int _number;
    private Point2D _position;
    private short _height;
    private LocalDate _begDate;
    private LocalDate _endDate;
    private float _rot;
    private Justify _justify;

    private String pText;
    private TypeDescibedObject _typeDO;
    private String _numDO;
    private GraphParam _grParam;
    private String sText;
    
    public CadText(short _type, int _number, Point2D _position, short _height,
            LocalDate _begDate, LocalDate _endDate, float _rot,  Justify justify) {
        this._type = _type;
        this._number = _number;
        this._position = _position;
        this._height = _height;
        this._begDate = _begDate;
        this._endDate = _endDate;
        this._rot = _rot;
        this._justify = justify;
    }

    /**
     * Return Height of text in milimeters
     *
     */
    public short getHeight() {
        return _height;
    }

    public void setpText(String pText) {
        this.pText = pText;
    }

    public void setTypeDO(TypeDescibedObject _typeDO) {
        this._typeDO = _typeDO;
    }

    public void setNumDO(String _numDO) {
        this._numDO = _numDO;
    }

    public void setGrParam(GraphParam _grParam) {
        this._grParam = _grParam;
    }

    public void setsText(String sText) {
        this.sText = sText;
    }
    
    
}
