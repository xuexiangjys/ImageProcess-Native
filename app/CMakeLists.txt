# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# 设置构建本机库文件所需的 CMake的最小版本
cmake_minimum_required(VERSION 3.4.1)

# TODO 设置OpenCv的路径变量
set(OPENCV_ANDROID_SDK /Users/xuexiang/Documents/OpenCV-android-sdk)
#配置加载native依赖
include_directories(${OPENCV_ANDROID_SDK}/sdk/native/jni/include)

#支持-std=gnu++11
set(CMAKE_VERBOSE_MAKEFILE on)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

#静态方式方式引入libopencv_java3.so文件
add_library(lib_opencv STATIC IMPORTED)
set(CURRENT_DIR ${CMAKE_SOURCE_DIR})
set_target_properties(lib_opencv
        PROPERTIES
        IMPORTED_LOCATION ${CURRENT_DIR}/src/main/jniLibs/${ANDROID_ABI}/libopencv_java3.so
        )

# TODO 设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/jniLibs/${ANDROID_ABI})

add_library( # Sets the name of the library.
        imageprocess-lib
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        src/main/cpp/imageprocess-lib)

find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

target_link_libraries( # Specifies the target library.
        imageprocess-lib
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib}
        lib_opencv
        )