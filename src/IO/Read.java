/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import cadObjects.CadContour;
import cadObjects.CadLine;
import cadObjects.CadObject;
import cadObjects.CadText;
import graphicsObjects.Point2D;
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
        readCadaster();

    }

    private void readHeader() {
        pointer = -1;
        while (this._allLines[++pointer].trim().equalsIgnoreCase("header"));
        while (this._allLines[pointer].trim().equalsIgnoreCase("END_HEADER")) {
            if (this._allLines[pointer].trim() != "") {
                String[] sts = this._allLines[pointer].trim().split("\\s+");
                String allRest = "";
                if (sts.length > 1) {
                    allRest = String.join(" ", Arrays.copyOfRange(sts, 1, sts.length));
                }
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
                        break;
                    case "FIRM":
                        this.cObj.setFirm(allRest);
                        break;
                    case "REFERENCE":
                        if (sts.length != 3) {
                            throw new BadCadStructureException();
                        }
                        this.cObj.setRef(Double.parseDouble(sts[1]), Double.parseDouble(sts[2]));
                        break;
                    case "WINDOW":
                        if (sts.length != 5) {
                            throw new BadCadStructureException();
                        }
                        this.cObj.setWindow(new Point2D(Double.parseDouble(sts[1]),
                                Double.parseDouble(sts[2])), new Point2D(Double.parseDouble(sts[3]),
                                Double.parseDouble(sts[4])));
                        break;
                    case "COORDTYPE":
                        this.cObj.setCooType(allRest);
                        break;
                    case "CONTENTS":
                        this.cObj.setContents(allRest);
                        break;
                    case "COMMENT":
                        this.cObj.setComment(allRest);
                        break;
                }
            }
            pointer++;
        }

        System.out.println(_allLines[pointer]);
    }
    private byte statusLine = 0;

    private void readCadaster() {
        CadLine currentLine;
        CadContour currentContour;
        CadText currentText;
        while (this._allLines[++pointer].trim().equalsIgnoreCase("LAYER CADASTER"));
        while (this._allLines[pointer].trim().equalsIgnoreCase("END_LAYER")) {
            if (this._allLines[pointer].trim() != "") {
                String[] sts = this._allLines[pointer].trim().split("\\s+");
                if (statusLine == 0) {
                    switch (sts[1]) {
                        case "P":
                            addBasePoint(sts);
                            break;
                        case "L":
                            currentLine = addLine(sts);
                            statusLine = 2;
                            break;
                        case "C":
                            currentContour = addContour(sts);
                            statusLine = 3;
                            break;
                        case "S":
                            addSign(sts);
                            break;
                        case "T":
                            currentText = addText(sts);
                            statusLine = 5;
                            break;
                    }
                } else {
                    switch(statusLine){
                        case 2:
                            if (!addPointsToLine(currentLine,sts)){
                                statusLine=0;
                                pointer--;
                            }
                        case 3:
                            if (!addLinesToContour(currentContour,sts)){
                                statusLine=0;
                                pointer--;
                            }
                        case 5:
                            addTextParameters(currentText,sts)
                    }

                }
            }
            pointer++;
        }

    }

    public CadObject getcObj() {
        return cObj;
    }

}
