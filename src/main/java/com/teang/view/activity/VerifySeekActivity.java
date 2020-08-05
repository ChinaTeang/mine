package com.teang.view.activity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.teang.R;
import com.teang.base.BaseActivity;
import com.teang.util.LogUtil;
import com.teang.util.ToastUtil;
import com.teang.view.custom.VerificationSeekBar;
import com.teang.view.custom.VerifyCodeView;

import butterknife.BindView;

public class VerifySeekActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sb_progress)
    VerificationSeekBar seekBar;
    @BindView(R.id.verify_code_view)
    VerifyCodeView verifyCodeView;

    @Override
    protected int initView() {
        return R.layout.activity_verify_seek;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        title.setText(R.string.verify_seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() != seekBar.getMax()) {
                    seekBar.setProgress(0);
                } else {
                    String content = verifyCodeView.getEditContent();
                    if ("888888".equals(content)) {
                        ToastUtil.showToast(context, "验证成功");
                    } else {
                        verifyCodeView.cleanEditContent();
                        seekBar.setProgress(0);
                        ToastUtil.showToast(context, "验证失败，请输入888888");
                    }

                }
            }
        });
        verifyCodeView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                LogUtil.e(TAG, verifyCodeView.getEditContent());
            }

            @Override
            public void invalidContent() {

            }
        });
    }

}
