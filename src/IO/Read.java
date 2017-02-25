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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author stanislav
 */
public class Read {

    private CadObject cObj;
    private String[] _allLines;
    private int pointer = 0;

    public void readCad(String filePathName) throws IOException {
        byte[] fileLines = Files.readAllBytes(Paths.get(filePathName));

        for (int i = 0; i < fileLines.length; i++) {
            if (fileLines[i] >= -80 && fileLines[i] <= -65) {
                fileLines[i] += 48;
            }
        }

        {
            String fl1 = new String(fileLines, "IBM866");
            this._allLines = fl1.split(System.lineSeparator());
        }
        this.cObj = new CadObject();
        readHeader();
//        for(String s:this._allLines){
//            System.out.println(s);
//        }
    }

    private void readHeader() {
        pointer = -1;
        while (this._allLines[++pointer].trim().equalsIgnoreCase("header"));
        while (this._allLines[pointer].trim().equalsIgnoreCase("END_HEADER")) {
            String[] sts = this._allLines[pointer].trim().split("\\s+");
            String allRest = String.join(" ", Arrays.copyOfRange(sts, 1, sts.length));
            switch (sts[0]) {
                case "VERSION":
                    this.cObj.setVer(sts[1]);
                    break;
                case "EKATTE":
                    this.cObj.seteKATTE(Integer.parseInt(sts[1]));
                    break;
                case "NAME":
                    this.cObj.setName(allRest);
                    break;
                case "DATE":
                    this.cObj.setDate(LocalDate.parse(sts[1], DateTimeFormatter.ofPattern("d.M.u")));
            }

            pointer++;
        }

        System.out.println(_allLines[pointer]);
    }

    public CadObject getcObj() {
        return cObj;
    }

}
