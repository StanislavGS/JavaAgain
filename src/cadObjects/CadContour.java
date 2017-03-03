/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadObjects;

import graphicsObjects.Contour;
import graphicsObjects.Point2D;
import java.time.LocalDate;

/**
 *
 * @author stanislav
 */
public class CadContour extends Contour{
    private TypeLevels _type;
    private short[] _number;
    private Point2D _numberPosition;
    private LocalDate _begDate;
    private LocalDate _endDate;

    public CadContour(TypeLevels _type, String number ,Point2D _numberPosition,
            LocalDate _begDate, LocalDate _endDate) {
        super(number);        
        String[] num = number.split("\\.");
        this._number = new short[num.length];
        for (int i = 0; i < num.length; i++) {
            this._number[i] = Short.parseShort(num[i]);
        }
        this._type = _type;
        this._numberPosition = _numberPosition;
        this._begDate = _begDate;
        this._endDate = _endDate;
    }
    
    
    
}
