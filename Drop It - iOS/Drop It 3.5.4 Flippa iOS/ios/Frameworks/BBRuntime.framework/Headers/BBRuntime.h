#ifndef BBRUNTIME
#define BBRUNTIME

#ifdef WIN32
#include <string>
#include <functional>
#else
#import <string>
#import <functional>
#endif

#include "BBRuntimePlatformDefine.h"

extern "C"{
    BB_RUNTIME_API extern void BBRuntimeSetDataPath(const std::string &path);
    BB_RUNTIME_API extern void BBRuntimeResignActive();
    BB_RUNTIME_API extern void BBRuntimeEnterBackground();
    BB_RUNTIME_API extern void BBRuntimeEnterForeground();
    BB_RUNTIME_API extern void BBRuntimeBecomeActive();

    BB_RUNTIME_API extern void BBRuntimeDidLoad();
    BB_RUNTIME_API extern void BBRuntimeSetLoadCallback(std::function<void()> callback);
    BB_RUNTIME_API extern void BBRuntimeSetShutdownCallback(std::function<void()> callback);

    BB_RUNTIME_API const char* BBRuntimeJsSettingsString();
    BB_RUNTIME_API void BBRuntimeSetJsSettingsValue(const std::string &path, const std::string &value);
    BB_RUNTIME_API const char* BBRuntimeGetJsSettingsValue(const std::string &path);

    BB_RUNTIME_API void BBRuntimeAddGlobalEventListener(std::function<void(std::string, std::string)> *callback);
    BB_RUNTIME_API void BBRuntimeRemoveGlobalEventListener(std::function<void(std::string, std::string)> *callback);

    BB_RUNTIME_API void BBRuntimeExecuteScript(std::string &scriptBody);
    BB_RUNTIME_API bool BBRuntimeLoadAsset(std::string &path);
    BB_RUNTIME_API extern void BBRuntimeAddTexture(const std::string &filePath, bool alpha, const std::string &name);
}

#endif

