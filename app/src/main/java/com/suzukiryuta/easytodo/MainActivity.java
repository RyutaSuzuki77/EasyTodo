package com.suzukiryuta.easytodo;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private int num = 0;
    private int preDx[] = new int[10];
    private int preDy[] = new int[10];
    private Button todoContent[] = new Button[10];
    private ImageView todoArea;
    private RelativeLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = getApplicationContext();
        todoArea = (ImageView) findViewById(R.id.todoarea);
        parent = (RelativeLayout) findViewById(R.id.parent);

        Button inputButton = (Button) findViewById(R.id.inputButton);
        inputButton.setOnTouchListener(this);

        for (int i = 0; i < 10; i++) {
            todoContent[i] = new Button(context);
            todoContent[i].setId(i);
            todoContent[i].setOnTouchListener(this);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        int newDx = (int)e.getRawX();
        int newDy = (int)e.getRawY();
        if (v != null) {
            switch (v.getId()) {
                case R.id.inputButton:
                    if(e.getAction() == MotionEvent.ACTION_UP){
                        EditText inputText = findViewById(R.id.inputText);

                        if (inputText.getText().toString().equals("")) {
                            break;
                        }

                        if (num == 10) {
                            break;
                        }

                        todoContent[num].setWidth(todoArea.getWidth());
                        todoContent[num].setText(inputText.getText().toString());
                        parent.addView(todoContent[num]);
                        num++;

                        //キーボードを閉じる
                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        //入力テキスト初期化
                        inputText.setText("");
                    }

                    break;

                case 0:
                    if (e.getAction() == MotionEvent.ACTION_MOVE) {
                        int dx = todoContent[v.getId()].getLeft() + (newDx - preDx[v.getId()]);
                        int dy = todoContent[v.getId()].getTop() + (newDy - preDy[v.getId()]);
                        int imgW = dx + todoContent[v.getId()].getWidth();
                        int imgH = dy + todoContent[v.getId()].getHeight();

                        // todoコンテンツの位置を動かす
                        todoContent[v.getId()].layout(dx, dy, imgW, imgH);
                    }
                    preDx[v.getId()] = newDx;
                    preDy[v.getId()] = newDy;
                    break;

                default:

                    break;
            }


        }

        return true;
    }

}
