package com.dudu.dictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MoreOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_options);
        Intent getDataFileName = getIntent();
        String dataFileName = getDataFileName.getStringExtra("dataFileName");
        ImageButton delete = (ImageButton) findViewById(R.id.delete);
        ImageButton cancel = (ImageButton) findViewById(R.id.cancel);

        ButtonUtil.addClickScale(delete, 0.9f, 120);
        ButtonUtil.addClickScale(cancel, 0.9f, 120);

        delete.setOnClickListener(view -> {
            Intent intent= new Intent();
            intent.putExtra("dataFileName",dataFileName);
            intent.putExtra("data_return","删除成功");
            setResult(RESULT_OK,intent);
            finish();
        });
        cancel.setOnClickListener(view -> {
            Intent intent= new Intent();
            intent.putExtra("data_return","取消删除");
            setResult(RESULT_CANCELED,intent);
            finish();
        });
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}