package com.dudu.dictation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DictationAdapter extends ArrayAdapter<Dictation> {
    private int resourceId;
    public DictationAdapter(Context context, int textViewResourseId, List<Dictation> objects){
        super(context,textViewResourseId,objects);
        resourceId = textViewResourseId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Dictation dictation = getItem(position);  //获取当前的Dictation实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView dictationImage = (ImageView)view.findViewById(R.id.name_image);
        TextView dictationName = (TextView) view.findViewById(R.id.name_text);
        dictationImage.setImageResource(dictation.getImageId());
        dictationName.setText(dictation.getName());
        return view;
    }
}
