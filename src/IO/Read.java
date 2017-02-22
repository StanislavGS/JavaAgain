/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import graphicsObjects.CadObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;



/**
 *
 * @author stanislav
 */
public class Read {
    public static void readCad(CadObject obj,String filePathName) throws IOException{
        //String fileLines=new String(Files.readAllBytes(Paths.get(filePathName)));
        List<String> fileLines=Files.readAllLines(Paths.get(filePathName));
        
        System.out.println(fileLines);
        
    }
}
