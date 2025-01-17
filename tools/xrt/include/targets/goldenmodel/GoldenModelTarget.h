//-------------------------------------------------------------------------------------
//
//                             The XRT Project
//
// See LICENSE.TXT for details.
//-------------------------------------------------------------------------------------
#pragma once

#include <cstdint>
#include <targets/common/Target.h>

//-------------------------------------------------------------------------------------
class GoldenModelTarget : public Target {

public:
    GoldenModelTarget() = default;

    ~GoldenModelTarget() override = default;

    void reset() override;

    void runRuntime(uint32_t _address, uint32_t _argc, uint32_t *_args) override;

    void runDebug(uint32_t _address, uint32_t *_args, uint32_t _breakpointAddress) override;

    uint32_t readRegister(uint32_t _address) override;

    void writeRegister(uint32_t _address, uint32_t _register) override;

    void writeCode(uint32_t _address, uint32_t *_code, uint32_t _length) override;

    void readControllerData(uint32_t _address, uint32_t *_data, uint32_t _lineStart, uint32_t _lineStop,
            uint32_t _columnStart, uint32_t _columnStop) override;

    void writeControllerData(uint32_t _address, uint32_t *_data, uint32_t _lineStart, uint32_t _lineStop,
            uint32_t _columnStart, uint32_t _columnStop) override;

    void readMatrixArray(uint32_t _accMemStart,
                         uint32_t *_ramMatrix,
                         uint32_t _ramTotalLines, uint32_t _ramTotalColumns,
                         uint32_t _ramStartLine, uint32_t _ramStartColumn,
                         uint32_t _numLines, uint32_t _numColumns,
                         bool     _accRequireResultReady) override;

    void writeMatrixArray(uint32_t _accMemStart,
                          uint32_t *_ramMatrix,
                          uint32_t _ramTotalLines, uint32_t _ramTotalColumns,
                          uint32_t _ramStartLine, uint32_t _ramStartColumn,
                          uint32_t _numLines, uint32_t _numColumns) override;

    void dump(const std::string & _address) override;

};

//-------------------------------------------------------------------------------------
