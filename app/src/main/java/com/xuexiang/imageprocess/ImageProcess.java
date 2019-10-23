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

/**
 * 图片处理
 *
 * @author xuexiang
 * @since 2019-10-22 15:20
 */
public final class ImageProcess {

    static {
        System.loadLibrary("imageprocess-lib");
    }

    public static native String getVersion();

    /**
     * 处理灰度化
     *
     * @param buf
     * @param w
     * @param h
     * @return
     */
    public native int[] processGray(int[] buf, int w, int h);


}
