#ifndef BBRUNTIMESHARED
#define BBRUNTIMESHARED

#ifdef WIN32
#include <string>
#include <functional>
#include <ctime>
#else
#import <string>
#import <functional>
#import <ctime>
#endif

#include "BBRuntimePlatformDefine.h"

class BB_RUNTIME_API BBRuntimeShared{
public:
    static BBRuntimeShared* instance();
    
    void setDataPath(const std::string &path);
    const std::string& dataPath() const;

    void setGameId(const std::string &id);

    const std::string& ibVersion() const;
    int bigIbVersion() const;
    int majorIbVersion() const;
    int minorIbVersion() const;

    const std::string& buildNumber() const;
    std::time_t saveDate() const;
    
    void setLoadCallback(std::function<void()> callback);
    void setShutdownCallback(std::function<void()> callback);
    void didLoad();
    void didShutdown();

    std::string jsSettingsString() const;
    void setJsSettingsValue(const std::string &path, const std::string &value);
    std::string getJsSettingsValue(const std::string &path) const;

    void addGlobalEventListener(std::function<void(std::string, std::string)> *callback);
    void removeGlobalEventListener(std::function<void(std::string, std::string)> *callback);

    std::uint32_t addTexture(const std::string &filePath, bool alpha, const std::string &name);

    bool loadModelTree(const std::string &path);

    void executeScript( std::string scriptBody );
private:
    BBRuntimeShared();
    
    static BBRuntimeShared *_instance;
    std::function<void()> _loadCallback;
    std::function<void()> _shutdownCallback;
    std::string _dataPath;
};

#endif
