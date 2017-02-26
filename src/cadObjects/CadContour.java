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
    private short[] _number=new short[4];
    private Point2D _numberPosition;
    private LocalDate _begDate;
    private LocalDate _endDate;
    
}
