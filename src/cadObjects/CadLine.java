/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadObjects;

import graphicsObjects.PLine2d;
import java.time.LocalDate;

/**
 *
 * @author stanislav
 */
public class CadLine extends PLine2d {

    private int number;
    private TypeLevels level;
    private LocalDate _begDate;
    private LocalDate _endDate;

    public CadLine(int number, short type,TypeLevels level,Double elevation,LocalDate begDate,
            LocalDate endDate) {
        super(type);
        super.setElevation(elevation);
        setNumber(number);
        setLevel(level);
        setBegDate(begDate);
        setEndDate(endDate);
        
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setLevel(TypeLevels level) {
        this.level = level;
    }

    public void setBegDate(LocalDate _begDate) {
        this._begDate = _begDate;
    }

    public void setEndDate(LocalDate _endDate) {
        this._endDate = _endDate;
    }
    
     
    
}
