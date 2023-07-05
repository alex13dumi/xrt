//-------------------------------------------------------------------------------------
//
//                             The XRT Project
//
// See LICENSE.TXT for details.
//
//-------------------------------------------------------------------------------------

#include "common/XrtException.h"
#include <common/Utils.h>
#include <manager/libmanager/LibraryResolver.h>
#include <fmt/format.h>
#include <stdexcept>
#include <iostream>
#include <magic_enum.hpp>

const std::filesystem::path LibraryResolver::cLibPath = getXpuHome() + "/lib";

//-------------------------------------------------------------------------------------
LibraryResolver::LibraryResolver(const Arch &_arch) : arch(_arch)
{
    if (!std::filesystem::exists(cLibPath)) {
        throw std::runtime_error("library path does not exist in XPU_HOME: "
            "Make sure you have installed the XPU libraries correctly.");
    }

    std::cout << fmt::format("Reading libraries from {}", cLibPath.string()) << std::endl;
}

//-------------------------------------------------------------------------------------
std::filesystem::path LibraryResolver::resolve(const std::string &_name, LibLevel _level) {
    std::cout << fmt::format("Resolving library {} at level {}...", _name, magic_enum::enum_name(_level)) << std::endl;
    
    switch (_level) {
        case LibLevel::ANY_LEVEL: {
            for (LibLevel _level : {LibLevel::HIGH_LEVEL, LibLevel::MID_LEVEL, LibLevel::LOW_LEVEL}) {
                try {
                    std::cout << fmt::format("Attempting level {}...", magic_enum::enum_name(_level)) << std::endl;
                    return resolve(_name, _level);
                } catch (const LibNotFoundError &) {
                    continue;
                }
            }

            break;
        }

        case LibLevel::LOW_LEVEL: {
            std::filesystem::path _path = cLibPath / "lowlevel" / arch.IDString / _name;

            for (const std::filesystem::path& _ext : {".json", ".hex", ".obj"}) {
                _path.replace_extension(_ext);
                if (std::filesystem::exists(_path)) {
                    std::cout << fmt::format("Found library {} at {}", _name, _path.string()) << std::endl;
                    return _path;
                }
            }

            break;
        }

        case LibLevel::MID_LEVEL: {
            std::filesystem::path _path = cLibPath / "midlevel" / _name;

            for (const std::string& _ext : {".so", ".cpp", ".c"}) {
                _path.replace_extension(_ext);
                if (std::filesystem::exists(_path)) {
                    std::cout << fmt::format("Found library {} at {}", _name, _path.string()) << std::endl;
                    return _path;
                }
            }

            break;
        }

        case LibLevel::HIGH_LEVEL: {
            // TODO: LibLevel::HIGH_LEVEL
            break;
        }
    }

    throw LibNotFoundError(_name);
}

//-------------------------------------------------------------------------------------
LibNotFoundError::LibNotFoundError(const std::string &_name)
    : XrtException(fmt::format("library {} not found", _name), XrtErrorNumber::LIBRARY_NOT_FOUND)
{}

//-------------------------------------------------------------------------------------