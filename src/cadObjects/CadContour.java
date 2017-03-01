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

    public CadContour(TypeLevels _type, short[] number ,Point2D _numberPosition,
            LocalDate _begDate, LocalDate _endDate) {
        this._type = _type;
        this._number=number;
        this._numberPosition = _numberPosition;
        this._begDate = _begDate;
        this._endDate = _endDate;
    }
    
    
    
}
