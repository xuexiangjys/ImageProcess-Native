/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.imageprocess;

import android.graphics.Bitmap;

import com.xuexiang.xutil.display.ImageUtils;

/**
 * @author xuexiang
 * @since 2019-10-23 9:28
 */
public final class XImageProcess {

    private static volatile XImageProcess sInstance = null;

    private ImageProcess mImageProcess;

    private XImageProcess() {
        mImageProcess = new ImageProcess();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static XImageProcess get() {
        if (sInstance == null) {
            synchronized (XImageProcess.class) {
                if (sInstance == null) {
                    sInstance = new XImageProcess();
                }
            }
        }
        return sInstance;
    }


    /**
     * 灰度化
     *
     * @param imagePath 图片路径
     * @return
     */
    public Bitmap gray(String imagePath) {
        return gray(ImageUtils.getBitmap(imagePath));
    }

    /**
     * 灰度化
     *
     * @param srcBitmap 原始图片
     * @return
     */
    public Bitmap gray(Bitmap srcBitmap) {
        int w = srcBitmap.getWidth(), h = srcBitmap.getHeight();
        int[] pix = new int[w * h];
        srcBitmap.getPixels(pix, 0, w, 0, 0, w, h);
        int[] resultPixels = mImageProcess.processGray(pix, w, h);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixels, 0, w, 0, 0, w, h);
        return result;
    }

}
