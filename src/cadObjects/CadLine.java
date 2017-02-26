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

    public CadLine(int number, short type) {
        super(type);
        setNumber(number);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
