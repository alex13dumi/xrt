//-------------------------------------------------------------------------------------
package xpu.sw.tools.sdk.common.fileformats.abstractexecutable;
//-------------------------------------------------------------------------------------
import java.io.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.apache.logging.log4j.*;
import org.apache.lucene.util.*;

import com.esotericsoftware.kryo.kryo5.*;
import com.esotericsoftware.kryo.kryo5.io.*;

import xpu.sw.tools.sdk.common.isa.*;
import xpu.sw.tools.sdk.common.fileformats.core.*;

//-------------------------------------------------------------------------------------
public class AbstractExecutableFile extends XpuFile {
    protected ArrayList<AbstractSegment> featureSegments = new ArrayList<>();
    protected ArrayList<AbstractSegment> codeSegments = new ArrayList<>();
    protected ArrayList<AbstractSegment> dataSegments = new ArrayList<>();
    protected int crcValue = 0;

//-------------------------------------------------------------------------------------
    public AbstractExecutableFile(Logger _log, String _path, String _extension) {
        super(_log, _path, _extension);
    }

//-------------------------------------------------------------------------------------
    public AbstractExecutableFile(Logger _log, String _path, String _extension, List<Program> _programs, List<Data> _datas, List<Long> _features) {
        super(_log, _path, _extension);
        AbstractSegment _featureSegment = new AbstractSegment(log, -1);
        _featureSegment.setData(_features);

        int _address = 0;
        AbstractSegment _codeSegment = new AbstractSegment(log, _address);
        List<Long> _bincodeInSegment = new ArrayList<Long>();
        for(int i = 0; i < _programs.size(); i++){
            Program _program = _programs.get(i);
            List<InstructionLine> _instructionLines = _program.getAll();
            for(int j = 0; j < _instructionLines.size(); j++){
                InstructionLine _instructionLine = _instructionLines.get(j);
                _bincodeInSegment.add(_instructionLine.toBin());
//                HexLine _hexLine = new HexLine(_instructionLine);
//                _hex.add(_address, _hexLine);
                _address++;
            }
        }
        _codeSegment.setData(_bincodeInSegment);

        List<Long> _bindataInSegment = new ArrayList<Long>();
        AbstractSegment _dataSegment = new AbstractSegment(log);
        for(int i = 0; i < _datas.size(); i++){
            Data _data = _datas.get(i);
            List<DataLine> _dataLines = _data.getAll();
            _address = _data.getAddress();
            if(i == 0){
                _dataSegment.setAddress(_address);
            }
            log.debug("Write "+_dataLines.size()+" datalines @ address " + _address);
            for(int j = 0; j < _dataLines.size(); j++){
                DataLine _dataLine = _dataLines.get(j);
                _bindataInSegment.add(_dataLine.toBin());
//                HexLine _hexLine = new HexLine(_dataLine);
//                _hex.add(_address, _hexLine);
                _address++;
            }

            // TODO: get machine size from context
            while ((_address - _data.getAddress()) % 1024 != 0) {
                _bindataInSegment.add(0L);
                _address++;
            }
        }
        _dataSegment.setData(_bindataInSegment);

        addFeatureSegment(_featureSegment);
        addCodeSegment(_codeSegment);
        addDataSegment(_dataSegment);
    }

//-------------------------------------------------------------------------------------
    public ArrayList<AbstractSegment> getFeatureSegments() {
        return featureSegments;
    }

//-------------------------------------------------------------------------------------
    public ArrayList<AbstractSegment> getCodeSegments() {
        return codeSegments;
    }

//-------------------------------------------------------------------------------------
    public ArrayList<AbstractSegment> getDataSegments() {
        return dataSegments;
    }

//-------------------------------------------------------------------------------------
    public int getCrcValue() {
        return crcValue;
    }

//-------------------------------------------------------------------------------------
    public AbstractSegment getFeatureSegment(int _index) {
    return featureSegments.get(_index);
}
    public AbstractSegment getFeatureSegment() {
        return featureSegments.get(0);
    }
    public AbstractSegment getCodeSegment(int _index) {
    return codeSegments.get(_index);
}
    public AbstractSegment getCodeSegment() {
    return codeSegments.get(0);
}
    public AbstractSegment getDataSegment(int _index) { return dataSegments.get(_index);}
    public AbstractSegment getDataSegment() { return dataSegments.get(0);}

    public void addFeatureSegment(AbstractSegment _featureSegment) { featureSegments.add(_featureSegment);}
    public void addCodeSegment(AbstractSegment _codeSegment) { codeSegments.add(_codeSegment);}
    public void addDataSegment(AbstractSegment _dataSegment) {
        dataSegments.add(_dataSegment);
    }

//-------------------------------------------------------------------------------------
    public boolean isValid() {
        return crcValue == 0;
    }

//-------------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    /**
     * Loads an object file from path, reading its segments and checking the CRC to match
     * The object file contains in this order:
     *  featuresSegments
     *  codeSegments
     *  dataSegments
     *  crc
     */
    public void load() {
        try {
//          Input _input = new Input(new GZIPInputStream(new FileInputStream(path)));
            Input _input = new Input(new FileInputStream(path));

            crcValue = 0;
            featureSegments = readSegments(log, _input);
            codeSegments = readSegments(log, _input);
            dataSegments = readSegments(log, _input);
            crcValue ^= _input.readInt();

            _input.close();
//          log.info("Loading [" + _index + ":" + _filePath + "]...OK[" + _data.size() + " entries]");
            log.info("Loading [" + path + "]...OK");
            if (isValid()) log.info("Loading [" + path + "]...OK");
            else log.error("Loading [" + path + "]...BAD CRC");
            //saveTestSegment();
        }
        catch(Exception _e){
            log.info("Loading [" + path + "]...error" + _e.getMessage());
        }
    }

    /**
     * Reads segments from object file
     * @param _log Logger where messages are printed
     * @param _input InputStream for the obj file
     * @return An ArrayList of ObjSegments
     */
    private ArrayList<AbstractSegment> readSegments(Logger _log, Input _input) {
        ArrayList<AbstractSegment> ret = new ArrayList<>();
        int num = _input.readInt(); crcValue ^= num;
        while (num-- > 0) {
            int length = _input.readInt(); crcValue ^= length;
            int address = _input.readInt(); crcValue ^= address;
            long[]data = _input.readLongs(length);
            for (long l : data) crcValue ^= l;
            ret.add(new AbstractSegment(_log, address, data));
        }
        return ret;
    }

    /**
     * Writes segments to object file
     * @param _arr ArrayList of object segments to write
     * @param _output OutputStream where segments are written
     */
    private void saveSegments(ArrayList<AbstractSegment> _arr, Output _output) {
        crcValue ^= _arr.size();
        _output.writeInt(_arr.size());
        for (int i = 0; i < _arr.size(); i++) {
            _output.writeInt(_arr.get(i).getLength()); crcValue ^= _arr.get(i).getLength();
            _output.writeInt(_arr.get(i).getAddress()); crcValue ^= _arr.get(i).getAddress();
            _output.writeLongs(_arr.get(i).getData(), 0, _arr.get(i).getData().length);
            for (long l : _arr.get(i).getData())
                crcValue ^= l;
        }
    }

    private void saveTestSegment() {
        String filename = path + ".jpeg";
        Output _output = null;
        try {
            _output = new Output(new FileOutputStream(filename));
            _output.writeLongs(dataSegments.get(0).getData(), 0, dataSegments.get(0).getLength());
            _output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//-------------------------------------------------------------------------------------
    public void save() {
        log.info("Save " + path + "... ");
        try {
            Output _output = new Output(new FileOutputStream(path));
            saveSegments(featureSegments, _output);
            saveSegments(codeSegments, _output);
            saveSegments(dataSegments, _output);
            _output.writeInt(crcValue);
            _output.close();
        } catch (Exception _e) {
            log.info("error: Cannot write object!" + _e.getMessage());
        }
    }

//-------------------------------------------------------------------------------------
}
//-------------------------------------------------------------------------------------
