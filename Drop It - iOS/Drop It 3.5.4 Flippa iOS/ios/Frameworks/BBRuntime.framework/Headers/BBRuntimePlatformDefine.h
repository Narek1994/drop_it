#ifndef BBRUNTIMEPLATFORMDEFINE
#define BBRUNTIMEPLATFORMDEFINE

#ifdef WIN32

#if defined(BB_RUNTIME_STATIC)
#define BB_RUNTIME_API
#else // BB_RUNTIME_STATIC
#if defined(BB_RUNTIME_EXPORT)
#define BB_RUNTIME_API __declspec(dllexport)
#else
#define BB_RUNTIME_API __declspec(dllimport)
#endif // BB_RUNTIME_EXPORT
#endif // BB_RUNTIME_STATIC

#else
#define BB_RUNTIME_API
#endif // WIN32

#endif // BBRUNTIMEPLATFORMDEFINE
