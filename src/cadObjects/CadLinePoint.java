/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadObjects;

import graphicsObjects.Point2D;

/**
 *
 * @author stanislav
 */
public class CadLinePoint extends Point2D{
    private long _number;
    private byte _codePrecizion;
            private byte _codeStructure;
            private byte _codeCalculation;

    public CadLinePoint(long _number, double x, double y, byte _codePrecizion, 
            byte _codeStructure,  byte _codeCalculation) {
        super(x, y);
        this._number = _number;
        this._codePrecizion = _codePrecizion;
        this._codeStructure = _codeStructure;
        this._codeCalculation = _codeCalculation;
    }
            
}
