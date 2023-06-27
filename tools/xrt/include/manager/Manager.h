//-------------------------------------------------------------------------------------
//
//                             The XRT Project
//
// See LICENSE.TXT for details.
//
//-------------------------------------------------------------------------------------
#pragma once

#include <common/cache/Cache.h>
#include "manager/libmanager/FunctionInfo.hpp"
#include "manager/modmanager/ModManager.h"
#include <cstdint>
#include <targets/Targets.h>
#include <manager/libmanager/LibManager.h>
#include <manager/memmanager/MemManager.h>
#include <manager/driver/Driver.h>
#include <manager/libmanager/LibraryResolver.h>

//-------------------------------------------------------------------------------------
class Manager {
    LibManager *libManager;
    MemManager *memManager;
    ModManager *modManager;
    Driver *driver;

    LibraryResolver *libraryResolver;
    
    Cache *cache;

    const Arch& arch;
public:
    Manager(Targets *_targets, Cache *_cache, const Arch& _arch);

    ~Manager();

    void run(const std::string &_name);

    void load(const std::string &_path);

    // driver encapsulation

    void runRuntime(uint32_t _address, uint32_t _argc, uint32_t *_args);

    uint32_t readRegister(uint32_t _address);

    void writeRegister(uint32_t _address, uint32_t _value);

    // used in callbacks

    FunctionInfo *lowLevel(const std::string& _name);

    void runRuntime(FunctionInfo *_function, uint32_t _argc, uint32_t *_argv);


    void readMatrixArray(uint32_t _accMemStart,
                         uint32_t *_ramMatrix,
                         uint32_t _ramTotalLines, uint32_t _ramTotalColumns,
                         uint32_t _ramStartLine, uint32_t _ramStartColumn,
                         uint32_t _numLines, uint32_t _numColumns,
                         bool     _accRequireResultReady);

    void writeMatrixArray(uint32_t _accMemStart,
                          uint32_t *_ramMatrix,
                          uint32_t _ramTotalLines, uint32_t _ramTotalColumns,
                          uint32_t _ramStartLine, uint32_t _ramStartColumn,
                          uint32_t _numLines, uint32_t _numColumns);
};
//-------------------------------------------------------------------------------------
