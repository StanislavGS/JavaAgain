/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadObjects;

/**
 *
 * @author stanislav
 */
public enum TypeLevels {
    notABorder(0),
    borderLowPriority(1),
    borderNormalPriority(2),
    borderHighPriority(3),
    border(4);
    private final int value;

    private TypeLevels(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
