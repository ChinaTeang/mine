package com.teang.util;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.teang.global.MyApp;

import java.util.Locale;

/**
 * 系统语音播报
 */
public class SystemTTS extends UtteranceProgressListener {
    private static SystemTTS singleton;
    private TextToSpeech textToSpeech; // 系统语音播报类
    //系统是否支持中文播报
    private boolean isSupport = true;

    public static SystemTTS getInstance() {
        if (singleton == null) {
            synchronized (SystemTTS.class) {
                if (singleton == null) {
                    singleton = new SystemTTS();
                }
            }
        }
        return singleton;
    }

    private SystemTTS() {
        textToSpeech = new TextToSpeech(MyApp.getAppContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                //系统语音初始化成功
                if (i == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.CHINA);
                    textToSpeech.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                    textToSpeech.setSpeechRate(1.0f);
                    textToSpeech.setOnUtteranceProgressListener(SystemTTS.this);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        //系统不支持中文播报
                        isSupport = false;
                    }
                }
            }
        });
    }

    public void playText(String text) {
        if (textToSpeech == null) {
            return;
        }
        if (textToSpeech.isSpeaking()) {
            stopSpeak();
        }
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public void stopSpeak() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }

    public boolean isSupport() {
        return isSupport;
    }

    @Override
    public void onStart(String s) {

    }

    @Override
    public void onDone(String s) {

    }

    @Override
    public void onError(String s) {

    }
}
