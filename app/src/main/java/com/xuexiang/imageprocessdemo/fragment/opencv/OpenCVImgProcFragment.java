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

package com.xuexiang.imageprocessdemo.fragment.opencv;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.xuexiang.imageprocess.XImageProcess;
import com.xuexiang.imageprocessdemo.R;
import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.system.CameraUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.xuexiang.xaop.consts.PermissionConsts.CAMERA;
import static com.xuexiang.xaop.consts.PermissionConsts.STORAGE;
import static com.xuexiang.xutil.system.CameraUtils.REQUEST_CAMERA;

/**
 * @author xuexiang
 * @since 2019-09-25 13:55
 */
@Page(name = "OpenCV ImgProc使用")
public class OpenCVImgProcFragment extends XPageFragment {

    @BindView(R.id.iv_content)
    AppCompatImageView ivContent;

    private String mImagePath;
    /**
     * 拍摄的头像
     */
    private File mCameraFile;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_opencv_imgproc;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListeners() {

    }


    @SingleClick
    @OnClick({R.id.btn_select, R.id.btn_camera, R.id.btn_cvtcolor, R.id.btn_threshold, R.id.btn_threshold_red})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                selectPicture();
                break;
            case R.id.btn_camera:
                startOpenCamera();
                break;
            case R.id.btn_cvtcolor:
                //灰度化
                cvtcolor();
                break;
            case R.id.btn_threshold:
                //二值化
                threshold();
                break;
            case R.id.btn_threshold_red:
                //去红
                threshold_red();
                break;
            default:
                break;
        }
    }

    /**
     * 灰度化
     */
    private void cvtcolor() {
        if (StringUtils.isEmpty(mImagePath)) {
            ToastUtils.toast("请先选择图片！");
            return;
        }
        // TODO: 2019-10-22  灰度化
        ivContent.setImageBitmap(XImageProcess.get().gray(mImagePath));
    }

    /**
     * 二值化
     */
    private void threshold() {
        if (StringUtils.isEmpty(mImagePath)) {
            ToastUtils.toast("请先选择图片！");
            return;
        }

        // TODO: 2019-10-22  二值化

    }

    /**
     * 去红
     */
    private void threshold_red() {
        if (StringUtils.isEmpty(mImagePath)) {
            ToastUtils.toast("请先选择图片！");
            return;
        }

        // TODO: 2019-10-22 去红
    }

    @Permission(STORAGE)
    private void selectPicture() {
        startActivityForResult(IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.IMAGE), REQUEST_IMAGE);
    }

    /**
     * 打开照相机
     */
    @Permission({CAMERA, STORAGE})
    public void startOpenCamera() {
        CameraUtils.startOpenCamera(this, new CameraUtils.OnOpenCameraListener() {
            @Override
            public void onOpenCamera(File cameraFile) {
                mCameraFile = cameraFile;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择系统图片并解析
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE:
                    if (data != null) {
                        Uri uri = data.getData();
                        ivContent.setImageURI(uri);
                        mImagePath = PathUtils.getFilePathByUri(uri);
                    }
                    break;
                case REQUEST_CAMERA:
                    ivContent.setImageURI(PathUtils.getUriForFile(mCameraFile));
                    mImagePath = mCameraFile.getPath();
                    break;
                default:
                    break;
            }
        }

    }

}
