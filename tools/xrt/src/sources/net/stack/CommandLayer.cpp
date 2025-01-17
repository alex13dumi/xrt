//-------------------------------------------------------------------------------------
//
//                             The XRT Project
//
// See LICENSE.TXT for details.
//
//-------------------------------------------------------------------------------------
#include "sources/net/stack/CommandLayer.h"
#include "common/Utils.h"
#include "common/XrtException.h"
#include "sources/mux/MuxSource.h"
#include <cstdint>
#include <exception>
#include <openssl/md5.h>
#include <stdexcept>

//-------------------------------------------------------------------------------------
CommandLayer::CommandLayer(MuxSource *_muxSource, Cache &_cache, const Arch& _arch, int _clientConnection)
    : NetworkLayer(_muxSource, _clientConnection),
      arch(_arch), cache(_cache) {

}

//-------------------------------------------------------------------------------------
bool CommandLayer::checkFileExtension(const std::string& _filename, int _command) {

    int _fileType = getFileTypeFromGeneralPath(_filename);

    switch (_command) {
    case COMMAND_LOAD_FILE_HEX:
        return _fileType == XPU_FILE_HEX;
    case COMMAND_LOAD_FILE_JSON:
        return _fileType == XPU_FILE_JSON;
    case COMMAND_LOAD_FILE_OBJ:
        return _fileType == XPU_FILE_OBJ;
    case COMMAND_LOAD_FILE_CPP:
        return _fileType == XPU_FILE_SO;
    case COMMAND_LOAD_FILE_ONNX:
        return _fileType == XPU_FILE_ONNX;
    default:
        throw std::runtime_error("CommandLayer::checkFileExtension internal error");
    }
}

//-------------------------------------------------------------------------------------
int CommandLayer::processCommand(int _command) {
    fmt::println("Received command number: {}", _command);

    try {

        switch (_command) {
            case COMMAND_RESERVED: {
                break;
            }

            case COMMAND_HALT: {
                break;
            }

            case COMMAND_RESET: {
                break;
            }

            case COMMAND_IDLE: {
                break;
            }

            case COMMAND_RUN: {
                break;
            }

            case COMMAND_DEBUG_MODE: {
                break;
            }


            case COMMAND_LOAD_CODE_MEMORY: {
                //      loadCodeMemory();
                break;
            }
            case COMMAND_LOAD_DATA_MEMORY: {
                //      loadDataMemory();
                break;
            }
            case COMMAND_CLOSE_CONNECTION: {
                //      closeConnection();
                break;
            }

            case COMMAND_DONE: {
                break;
            }

            case COMMAND_ERROR: {
                break;
            }

            case COMMAND_LOAD_FILE_HEX:
            case COMMAND_LOAD_FILE_JSON:
            case COMMAND_LOAD_FILE_OBJ:
            case COMMAND_LOAD_FILE_ONNX:
            case COMMAND_LOAD_FILE_CPP: {
                unsigned char _level = receiveChar();

                // currently unused; TODO
                (void) _level;

                std::string _fullPath = receiveFile();
                muxSource->runCommand("source " + _fullPath);

                break;
            }

            case COMMAND_RUN_FUNCTION: {
                std::string _functionName = receiveString();

                muxSource->runCommand("run " + _functionName);

                break;
            }

            case COMMAND_DEBUG_RETREIVE_ARRAY_MEMORY_DATA: {
                int _firstCell = receiveInt();
                int _lastCell = receiveInt();
                int _firstRow = receiveInt();
                int _lastRow = receiveInt();

                MuxCommandReturnValue _ret =
                    muxSource->runCommand(fmt::format("debug-get-array-data {} {} {} {}", _firstCell, _lastCell, _firstRow, _lastRow));

                assert(_ret.type == MuxCommandReturnType::WORD_VECTOR);
                assert(sizeof(uint32_t) == sizeof(int));

                sendInt(COMMAND_DONE);
                sendIntArray(reinterpret_cast<const int *>(_ret.words.data()), _ret.words.size());

                break;
            }

            case COMMAND_GET_ARCHITECTURE_ID: {
                // TODO: Implement NetworkLayer::sendCharArray

                for (uint8_t c : arch.ID) {
                    sendChar(c);
                }

                break;
            }

            case COMMAND_PING: {
                sendInt(COMMAND_ACK);
                break;
            }

            case COMMAND_ACK: {

                break;
            }

            default: {
                throw XrtException(
                    fmt::format("Unknown net command {}", _command),
                    XrtErrorNumber::UNKNOWN_COMMAND
                );
            }
        }
    } catch (XrtException& _exception) {
        fmt::println("Error processing net command {}: {} ({})", _command, _exception.what(), _exception.errorNumberInt());

        sendInt(COMMAND_ERROR);
        sendInt(_exception.errorNumberInt());
    } catch (std::exception& _exception) {
        fmt::println("Error processing net command {}: {}", _command, _exception.what());

        sendInt(COMMAND_ERROR);
        sendInt(static_cast<int>(XrtErrorNumber::GENERIC_ERROR));
    } catch (...) {
        fmt::println("Unknown error processing net command {}", _command);

        sendInt(COMMAND_ERROR);
        sendInt(static_cast<int>(XrtErrorNumber::GENERIC_ERROR));
    }

    return -1;
}

//-------------------------------------------------------------------------------------
std::string CommandLayer::receiveFile() {
    unsigned char _md5[MD5_DIGEST_LENGTH];
    std::string _filename = receiveString();
    receiveCharArray(_md5, MD5_DIGEST_LENGTH);
    std::string _md5Hex = toHexString(_md5);
    std::cout << "Receive file MD5: " << _md5Hex << std::endl;
    if (!cache.needInstallResource(_filename, _md5Hex)) {
        sendInt(COMMAND_DONE);

        return cache.getResourceFromFilename(_filename);
    } else {
        std::cout << "Send RETRY..." << std::endl;
        sendInt(COMMAND_RETRY);
        long _length = receiveLong();

        fmt::println("Receiving file {} ({} bytes)", _filename, _length);

        auto _charStream = recieveCharStream(_length);

        return cache.installResource(_filename, _md5Hex, *_charStream);
    }
}

//-------------------------------------------------------------------------------------
std::string CommandLayer::receiveString() {
    int _length = receiveInt();
    auto *_string = new unsigned char[_length];

    receiveCharArray(_string, _length);
    std::stringstream _stdString;
    for (int i = 0; i < _length; i++) {
        //      printf("%02x", (0xff & (unsigned int)_bytes[i]));
        _stdString << _string[i];
        //      std::cout << std::setfill('0') << std::setw(2) << std::hex << (0xff & (unsigned int)_md[i]);
    }
    delete[] _string;
    return _stdString.str();
}

//-------------------------------------------------------------------------------------
std::string CommandLayer::toHexString(unsigned char *_bytes) {
    std::stringstream _hexString;
    for (int i = 0; i < MD5_DIGEST_LENGTH; i++) {
        //      printf("%02x", (0xff & (unsigned int)_bytes[i]));
        _hexString << std::hex << std::setw(2) << std::setfill('0') << (int) _bytes[i];
        //      std::cout << std::setfill('0') << std::setw(2) << std::hex << (0xff & (unsigned int)_md[i]);
    }
    return _hexString.str();
}
//-------------------------------------------------------------------------------------
