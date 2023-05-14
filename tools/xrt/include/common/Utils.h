//-------------------------------------------------------------------------------------
//
//                             The XRT Project
//
// See LICENSE.TXT for details.
//
//-------------------------------------------------------------------------------------
#pragma once
#include <string>
#include <vector>
#include <sstream>
#include <common/Globals.h>

//-------------------------------------------------------------------------------------
void printUsage();
void signalHandler(int _signal);
int getFileTypeFromGeneralPath(const std::string& _path);
int getFileTypeFromPath(const std::string& _path);
std::string getFileStemFromGeneralPath(const std::string &_path);
std::vector<std::string> split(std::string _value, const std::string& _separator);
inline bool endsWith(std::string const & value, std::string const & ending);
std::string basename(const std::string& _path);
std::string getXpuHome();
//-------------------------------------------------------------------------------------
