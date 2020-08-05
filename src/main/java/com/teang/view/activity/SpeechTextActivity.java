package com.teang.view.activity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.teang.R;
import com.teang.base.BaseActivity;
import com.teang.util.SystemTTS;

import butterknife.BindView;

/**
 * 语音播报
 */
public class SpeechTextActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.button)
    Button button;

    private TextToSpeech textToSpeech = null;//创建自带语音对象

    @Override
    protected int initView() {
        return R.layout.activity_speech_text;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        title.setText(R.string.speech_text);
        String ocean = "从明天起，做一个幸福的人。\n" +
                "喂马，劈柴，周游世界。\n" +
                "从明天起，关心粮食和蔬菜。\n" +
                "我有一所房子，面朝大海，春暖花开。\n" +
                "\n" +
                "从明天起，和每一个亲人通信。\n" +
                "告诉他们我的幸福。\n" +
                "那幸福的闪电告诉我的。\n" +
                "我将告诉每一个人。\n" +
                "\n" +
                "给每一条河每一座山取一个温暖的名字。\n" +
                "陌生人，我也为你祝福。\n" +
                "愿你有一个灿烂的前程。\n" +
                "愿你有情人终成眷属。\n" +
                "愿你在尘世获得幸福。\n" +
                "我只愿面朝大海，春暖花开。";
        editText.setText(ocean);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(SpeechTextActivity.this, TTSService.class);
                i.putExtra("text", editText.getText().toString());
                startService(i);*/
                SystemTTS.getInstance().playText(editText.getText().toString());
            }
        });
    }

    @Override
    protected void onStop() {
        SystemTTS.getInstance().stopSpeak();
        //stopService(new Intent(this, TTSService.class));
        super.onStop();
    }
}
