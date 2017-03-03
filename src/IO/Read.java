/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import cadObjects.BasePoint;
import cadObjects.CadContour;
import cadObjects.CadLine;
import cadObjects.CadLinePoint;
import cadObjects.CadObject;
import cadObjects.CadSign;
import cadObjects.CadText;
import cadObjects.TypeLevels;
import dataObjects.RecordOptions;
import dataObjects.Table;
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

    public Read() {
        this.cObj = new CadObject();
    }

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
        readTables();

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
        CadLine currentLine = null;
        CadContour currentContour = null;
        CadText currentText = null;
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
                    switch (statusLine) {
                        case 2:
                            if (!addPointsToLine(currentLine, sts)) {
                                statusLine = 0;
                                pointer--;
                            }
                        case 3:
                            if (!addLinesToContour(currentContour, sts)) {
                                statusLine = 0;
                                pointer--;
                            }
                        case 5:
                            addTextParameters(currentText, sts);
                    }

                }
            }
            pointer++;
        }

    }

    private void addBasePoint(String[] sts) {
        this.cObj.getRgo().add(new BasePoint(Byte.parseByte(sts[1]), Integer.parseInt(sts[2]),
                Byte.parseByte(sts[6]), Byte.parseByte(sts[9]), Float.parseFloat(sts[7]),
                Float.parseFloat(sts[8]), Float.parseFloat(sts[10]), Byte.parseByte(sts[11]),
                Byte.parseByte(sts[12]), Byte.parseByte(sts[13]), sts[14].equals("1"),
                sts[15].replaceAll("^\"|\"$", ""),
                LocalDate.parse(sts[16], DateTimeFormatter.ofPattern("d.M.u")),
                sts[17].equals("0") ? null
                : LocalDate.parse(sts[17], DateTimeFormatter.ofPattern("d.M.u")),
                Double.parseDouble(sts[3]), Double.parseDouble(sts[4]),
                Float.parseFloat(sts[10])));
    }

    private CadLine addLine(String[] sts) {
        int num = Integer.parseInt(sts[2]);
        CadLine ln = new CadLine(num, Short.parseShort(sts[1]), TypeLevels.forByte(Byte.parseByte(sts[3])),
                (sts.length > 6) ? Double.parseDouble(sts[6]) : 0.0,
                LocalDate.parse(sts[4], DateTimeFormatter.ofPattern("d.M.u")),
                sts[5].equals("0") ? null
                : LocalDate.parse(sts[5], DateTimeFormatter.ofPattern("d.M.u"))
        );
        this.getcObj().getLines().put(num, ln);
        return ln;
    }

    private CadContour addContour(String[] sts) {
        String[] num = sts[2].split(".");
        short[] numSho = new short[num.length];
        for (int i = 0; i < num.length; i++) {
            numSho[i] = Short.parseShort(num[i]);
        }

        CadContour cc = new CadContour(TypeLevels.forByte(Byte.parseByte(sts[1])), numSho,
                new Point2D(Double.parseDouble(sts[3]), Double.parseDouble(sts[4])),
                LocalDate.parse(sts[5], DateTimeFormatter.ofPattern("d.M.u")),
                sts[6].equals("0") ? null
                : LocalDate.parse(sts[6], DateTimeFormatter.ofPattern("d.M.u")));
        this.getcObj().getContours().put(sts[2], cc);
        return cc;
    }

    private void addSign(String[] sts) {
        this.cObj.getSigns().add(new CadSign(Short.parseShort(sts[1]), Integer.parseInt(sts[2]),
                new Point2D(Double.parseDouble(sts[3]), Double.parseDouble(sts[4])), Float.parseFloat(sts[5]),
                Float.parseFloat(sts[6]), LocalDate.parse(sts[7], DateTimeFormatter.ofPattern("d.M.u")),
                sts[8].equals("0") ? null : LocalDate.parse(sts[8], DateTimeFormatter.ofPattern("d.M.u"))));
    }

    private CadText addText(String[] sts) {
        CadText ct = new CadText(Short.parseShort(sts[1]), Integer.parseInt(sts[2]),
                new Point2D(Double.parseDouble(sts[3]), Double.parseDouble(sts[4])),
                Short.parseShort(sts[5]), LocalDate.parse(sts[6], DateTimeFormatter.ofPattern("d.M.u")),
                sts[7].equals("0") ? null : LocalDate.parse(sts[7], DateTimeFormatter.ofPattern("d.M.u")),
                Float.parseFloat(sts[8]), CadText.Justify.valueOf(sts[9]));
        this.cObj.getTexts().add(ct);
        return ct;
    }

    private boolean addPointsToLine(CadLine currentLine, String[] sts) {

        if (sts[0].charAt(0) < '0' || sts[0].charAt(0) > '9') {
            return false;
        }
        String[] pts = this._allLines[this.pointer].trim().split(";");

        for (String ppts : pts) {
            String[] pDetails = ppts.split("\\s+");
            if (pDetails.length < 3) {
                throw new BadCadStructureException();
            }
            long num = Long.parseLong(pDetails[0]);
            CadLinePoint lp = new CadLinePoint(num, Double.parseDouble(pDetails[1]),
                    Double.parseDouble(pDetails[2]), pDetails.length > 3 ? Byte.parseByte(pDetails[3]) : 0,
                    pDetails.length > 4 ? Byte.parseByte(pDetails[4]) : 0,
                    pDetails.length > 5 ? Byte.parseByte(pDetails[5]) : 0);
            this.cObj.getLinePoints().put(num, lp);
            currentLine.getPoints().add(lp);

        }
        return true;
    }

    private boolean addLinesToContour(CadContour currentContour, String[] sts) {
        if (sts[0].charAt(0) < '0' || sts[0].charAt(0) > '9') {
            return false;
        }
        for (String ln : sts) {
            int num = Integer.parseInt(ln);
            CadLine lin = this.cObj.getLines().get(num);
            if (lin == null) {
                throw new BadCadStructureException();
            }
            currentContour.getLines().add(lin);
        }
        return true;
    }

    private void addTextParameters(CadText currentText, String[] sts) {
        currentText.setpText(sts[0]);
        if (sts.length >= 4) {
            currentText.setTypeDO(CadText.TypeDescibedObject.valueOf(sts[1]));
            currentText.setNumDO(sts[2]);
            currentText.setGrParam(CadText.GraphParam.valueOf(sts[3]));
            if (sts.length > 4) {
                currentText.setsText(sts[4]);
            }
        }

        if (sts.length > 1 && sts.length < 4) {
            currentText.setsText(sts[1]);
        }
    }

    private void readTables() {
        boolean isOpenTable = false;
        Table currentTable = null;
        while (this.pointer < this._allLines.length) {
            String[] sts = this._allLines[pointer].trim().split("\\s+");
            if (isOpenTable) {
                if (sts[0].equals("F")) {
                    currentTable.getRecordsDescr().add(new RecordOptions(sts[1],
                            RecordOptions.RecordType.valueOf(sts[2]), Byte.parseByte(sts[3]),
                            Byte.parseByte(sts[4]), Byte.parseByte(sts[5]),
                            sts.length > 6 ? sts[6] : "", null));
                } else if (sts[0].equals("D")) {
                    currentTable.addRow(Arrays.copyOfRange(sts, 1, sts.length));
                }else if (sts[0].equals("END_TABLE")){
                    isOpenTable=false;
                }
            } else {
                if (sts[0].equalsIgnoreCase("table")) {
                    currentTable = new Table(sts[1]);
                    isOpenTable = true;
                }
            }

        }
    }

    public CadObject getcObj() {
        return cObj;
    }

}
