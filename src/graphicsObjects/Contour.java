/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicsObjects;

import java.util.List;

/**
 *
 * @author stanislav
 */
public class Contour {
    List<PLine2d> lines;
    String number;
    List<Contour> surround;
    List<Contour> nested;

    public List<PLine2d> getLines() {
        return lines;
    }
    
    
}
