/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataObjects;

import IO.BadCadStructureException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public void addRow(String[] values) {
        if (_recordsDescr.size() != values.length) {
            throw new BadCadStructureException();
        }
        Object[] rw = new Object[values.length];
        String key="";
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].trim();
            if(_recordsDescr.get(i).getFlagImportance()==1){
                key=values[i];
            }
            switch (_recordsDescr.get(i).getType()) {
                case C:
                    rw[i] = values[i].replaceAll("^\"|\"$", "");
                    break;
                case N:
                    rw[i] = Double.parseDouble(values[i]);
                    break;
                case S:
                    rw[i] = Integer.parseInt(values[i]);
                    break;
                case L:
                    rw[i] = Long.parseLong(values[i]);
                    break;
                case D:
                    if (values[i].equals("") || values[i].equals("0")) {
                        rw[i] = (LocalDate) null;
                    } else {
                        rw[i] = LocalDate.parse(values[i], DateTimeFormatter.ofPattern("d.M.u"));
                    }
                    break;
                case B:
                    rw[i] = values[i].equalsIgnoreCase("T");
                    break;
                case T:
                    if (values[i].equals("") || values[i].equals("0")) {
                        rw[i] = (LocalTime) null;
                    } else {
                        rw[i] = LocalTime.parse(values[i]);
                    }

            }
        }
        if (this._name.equals("PRAVA")||this._name.equals("HISTORY")||
                this._name.equals("SERVITUTI")){
            key=rw[0]+","+rw[1]+","+rw[2];
        }
        if (this._name.equals("OGRPIMO")||this._name.equals("ОTREGDANE")||
                this._name.equals("IMOТKATEG")){
            key=rw[0]+","+rw[1];
        }
        if (this._name.equals("GORIMOTI")){
            key=rw[0]+","+rw[1]+","+rw[2]+","+rw[3];
        }
        
        this._records.put(key, rw);
    }

}
