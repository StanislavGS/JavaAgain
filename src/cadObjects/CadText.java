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
public class CadText {

    private short _type;
    private int _number;
    private Point2D _position;
    private short _height;

    /**
     * Return Height of text in milimeters
     *
     */
    public short getHeight() {
        return _height;
    }

}
