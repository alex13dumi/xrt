//-------------------------------------------------------------------------------------
//
//                             The XRT Project
//
// See LICENSE.TXT for details.
//
//-------------------------------------------------------------------------------------
#include <string>
#include <vector>
#include <iostream>
#include <fstream>
#include <filesystem>
#include <regex>
#include <common/Globals.h>
#include "common/Utils.h"

//-------------------------------------------------------------------------------------
int getFileTypeFromGeneralPath(const std::string &_path) {
    std::vector<std::string> _dirs = split(_path, ".0x");
    //	int _npos = find_last_of(".0x");
    switch (_dirs.size()) {
        case 1: {
            return getFileTypeFromPath(_path);
        }
        case 2: {
            return getFileTypeFromPath(_dirs.at(0));
        }
        default: {
            throw std::runtime_error("Unrecognizable path: " + _path);
        }
    }
}

//-------------------------------------------------------------------------------------
int getFileTypeFromPath(const std::string &_path) {
    if (endsWith(_path, ".hex")) {
        return XPU_FILE_HEX;
    } else if (endsWith(_path, ".json")) {
        return XPU_FILE_JSON;
    } else if (endsWith(_path, ".obj")) {
        return XPU_FILE_OBJ;
    } else if (endsWith(_path, ".onnx")) {
        return XPU_FILE_ONNX;
    } else if (endsWith(_path, ".c")) {
        return XPU_FILE_C;
    } else if (endsWith(_path, ".C") || endsWith(_path, ".cxx")
               || endsWith(_path, ".cc") || endsWith(_path, ".cpp")) {
        return XPU_FILE_CPP;
    } else {
        throw std::runtime_error("Unrecognizable extension: " + _path);
    }
}

//-------------------------------------------------------------------------------------
std::vector<std::string> split(std::string _value, const std::string &_separator) {
    std::regex _regex(_separator);

    std::vector<std::string> _out(std::sregex_token_iterator(_value.begin(), _value.end(), _regex, -1),
            std::sregex_token_iterator());
    /*
        for (auto &_s: _out) {
            std::cout << _s << std::endl;
        }*/
    return _out;
}

//-------------------------------------------------------------------------------------
inline bool endsWith(std::string const &value, std::string const &ending) {
    if (ending.size() > value.size()) return false;
    return std::equal(ending.rbegin(), ending.rend(), value.rbegin());
}

//-------------------------------------------------------------------------------------
std::string basename(const std::string& _path) {
    size_t _nameStartIndex = _path.find_last_of(FS_DELIMITERS) + 1;
    size_t _nameStopIndex = _path.find_last_of('.');

    if (_nameStartIndex == std::string::npos)
        _nameStartIndex = 0;
    if (_nameStopIndex == std::string::npos)
        _nameStopIndex = _path.size();

    return {_path.begin() + _nameStartIndex, _path.begin() + _nameStopIndex};
}


//-------------------------------------------------------------------------------------
