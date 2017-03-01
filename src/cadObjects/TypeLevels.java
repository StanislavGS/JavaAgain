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
    notABorder((byte) 0),
    borderLowPriority((byte) 1),
    borderNormalPriority((byte) 2),
    borderHighPriority((byte) 3),
    border((byte) 4);
    private final byte value;

    private TypeLevels(byte value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static TypeLevels forByte(byte id) {
        for (TypeLevels tl : values()) {
            if (tl.getValue() == id) {
                return tl;
            }
        }
        throw new IllegalArgumentException("Invalid Day id: " + id);
    }
}
