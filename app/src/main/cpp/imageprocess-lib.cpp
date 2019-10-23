#include <jni.h>
#include <string>
#include <android/log.h>

#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <opencv2/opencv.hpp>

#define TAG "CMake-JNI" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

using namespace cv;
using namespace std;

extern "C"
JNIEXPORT jstring JNICALL
Java_com_xuexiang_imageprocess_ImageProcess_getVersion(JNIEnv *env, jclass thiz) {
    std::string version = "1.0.0";
    return env->NewStringUTF(version.c_str());
}
extern "C"
JNIEXPORT jintArray JNICALL
Java_com_xuexiang_imageprocess_ImageProcess_processGray(JNIEnv *env, jobject thiz, jintArray buf_,
                                                     jint w, jint h) {
    jint *buf = env->GetIntArrayElements(buf_, NULL);

    if (buf == NULL)return 0;
    Mat imgData(h, w, CV_8UC4, (unsigned char *) buf);//创建Mat矩阵对象

    uchar *ptr = imgData.ptr(0);
    for (int i = 0; i < w * h; i++) {
        //计算公式：Y(亮度) = 0.299*R + 0.587*G + 0.114*B
        //对于一个int四字节，其彩色值存储方式为：BGRA,注意这是opencv对像素的存储
        int grayScale = (int) (ptr[4 * i + 2] * 0.299 + ptr[4 * i + 1] * 0.587 +
                               ptr[4 * i + 0] * 0.114);
        ptr[4 * i + 1] = grayScale;
        ptr[4 * i + 2] = grayScale;
        ptr[4 * i + 0] = grayScale;
    }
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, buf);
    env->ReleaseIntArrayElements(buf_, buf, 0);
    return result;
}