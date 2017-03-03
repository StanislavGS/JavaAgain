/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataObjects;

/**
 *
 * @author stanislav
 */
public class RecordOptions {
    public enum RecordType{
        C,N,S,L,D,B,T
    }
    private String _name;
    private RecordType _type;
    private byte _len;
    private byte _prec;
    private byte _flagImportance;
    private String _conTableName;
    private Table _conectedTable;

    public RecordOptions(String _name, RecordType _type, byte _len, byte _prec,
            byte _flagImportance, String _conTablName, Table _conectedTable) {
        this._name = _name;
        this._type = _type;
        this._len = _len;
        this._prec = _prec;
        this._flagImportance = _flagImportance;
        this._conTableName= _conTablName;
        this._conectedTable = _conectedTable;
    }

    public RecordType getType() {
        return _type;
    }

    public byte getFlagImportance() {
        return _flagImportance;
    }

    
    
    
        
}
