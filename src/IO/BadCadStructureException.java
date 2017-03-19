/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

/**
 *
 * @author stanislav
 */
public class BadCadStructureException extends RuntimeException{

    public BadCadStructureException() {
        super();
    }
    
    public BadCadStructureException(String st) {
        super(st);
    }
    
}
