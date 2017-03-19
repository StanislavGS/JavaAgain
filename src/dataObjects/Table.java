/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataObjects;

import IO.BadCadStructureException;
import static dataObjects.RecordOptions.RecordType.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author stanislav
 */
public class Table {

    private String _name;
    private List<RecordOptions> _recordsDescr;
    private Map<String, Object[]> _records;

    public Table(String name) {
        this._name = name;
        this._recordsDescr = new ArrayList<>();
        this._records = new TreeMap<>();
    }

    public List<RecordOptions> getRecordsDescr() {
        return _recordsDescr;
    }

    private String[] split2(String st) {
        char[] ct = st.toCharArray();
        boolean isInString = false;

        List<String> lst = new ArrayList<>();
        StringBuilder sst = new StringBuilder("");
        for (int i = 0; i < ct.length; i++) {
            if (!isInString && ct[i] == ',') {
                lst.add(sst.toString());
                sst.delete(0, sst.length());
            } else {
                sst.append(ct[i]);
                if (ct[i] == '"' && !(isInString && i > 0 && ct[i - 1] == '\\')) {
                    isInString = !isInString;
                }

            }
        }
        lst.add(sst.toString());
        return lst.toArray(new String[0]);
    }

    public void addRow(String record) {
        String[] values = split2(record);
//        if (record.charAt(record.length()-1)==','){
//            values=(record+" ").split(",");
//            values[values.length-1]="";
//        }else{
//            values=record.split(",");
//        }
        if (_recordsDescr.size() != values.length) {
            throw new BadCadStructureException("Table:" + this._name + "Different number "
                    + "of fields in record:" + record);
        }
        Object[] rw = new Object[values.length];
        String key = "";
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].trim();
            if (_recordsDescr.get(i).getFlagImportance() == 1) {
                key = values[i].replaceAll("^\"|\"$", "");
            }
            if ((values[i].equals("") && (_recordsDescr.get(i).getType().equals(N)
                    || _recordsDescr.get(i).getType().equals(S)
                    || _recordsDescr.get(i).getType().equals(L)))) {
                values[i] = "0";
            }
            switch (_recordsDescr.get(i).getType()) {
                case C:
                    rw[i] = values[i].replaceAll("^\"|\"$", "");
                    break;
                case N:
                    rw[i]=values[i];
//                    try {
//                        if (values[i].matches("^\\d\\d*\\.?\\d*/\\d\\d*\\.?\\d*$")) {
//                            rw[i] = Double.parseDouble(values[i].substring(0, values[i].indexOf("/")));
//                        } else {
//                            rw[i] = Double.parseDouble(values[i]);
//                        }
//                    } catch (NumberFormatException ex) {
//                        throw new BadCadStructureException("Bad double format-Table:"
//                                + this._name + "; record:" + record);
//                    }
                    break;
                case S:
                    rw[i]=values[i];
//                    if (values[i].matches("^\\d\\d*\\.?\\d*$")) {
//                        rw[i] = (int) (Double.parseDouble(values[i]) + 0.5);
//                    } else {
//                        try {
//                            rw[i] = Integer.parseInt(values[i]);
//                        } catch (NumberFormatException ex) {
//                            throw new BadCadStructureException("Bad shortInt  format-Table:"
//                                    + this._name + "; record:" + record);
//                        }
//                    }
                    break;
                case L:
                    rw[i]=values[i];
//                    rw[i] = Long.parseLong(values[i]);
                    break;
                case D:
                    rw[i]=values[i];
//                    if (values[i].matches("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$")) {
//                        rw[i] = LocalDate.parse(values[i], DateTimeFormatter.ofPattern("d.M.u"));
//                    } else if (values[i].matches("^\\d{4}$")) {
//                        rw[i] = LocalDate.of(Integer.parseInt(values[i]), 1, 1);
//                    } else if (values[i].equals("") || values[i].equals("0")) {
//                        rw[i] = (LocalDate) null;
//                    } else {
//                        throw new BadCadStructureException("Bad date format-Table:"
//                                + this._name + "record:" + record);
//                    }
                    break;
                case B:
                    rw[i] = values[i].equalsIgnoreCase("T");
                    break;
                case T:
                    rw[i]=values[i];
//                    if (values[i].equals("") || values[i].equals("0")) {
//                        rw[i] = (LocalTime) null;
//                    } else {
//                        rw[i] = LocalTime.parse(values[i]);
//                    }

            }
        }
        if (this._name.equals("PRAVA") || this._name.equals("HISTORY")
                || this._name.equals("SERVITUTI")) {
            key = rw[0] + "," + rw[1] + "," + rw[2];
        }
        if (this._name.equals("OGRPIMO") || this._name.equals("ОTREGDANE")
                || this._name.equals("IMOТKATEG")) {
            key = rw[0] + "," + rw[1];
        }
        if (this._name.equals("GORIMOTI")) {
            key = rw[0] + "," + rw[1] + "," + rw[2] + "," + rw[3];
        }

        this._records.put(key, rw);
    }

}
