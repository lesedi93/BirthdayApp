package com.codetribe.www.miwok;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by codetribe on 6/23/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private int color_resourceID;

    public WordAdapter(Context context,int resource,List<Word> listWords, int color_resourceID) {
        super(context, 0, listWords);
        this.color_resourceID = color_resourceID;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View listViewWords = convertView;

        if(listViewWords == null)
        {
            listViewWords = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word currentWord = getItem(position);


        //find the textView in the list_item.xml with the ID of default_translation
        TextView tv_default = (TextView)listViewWords.findViewById(R.id.txt_default);
        //Set the default translation and set this text to the textview
        tv_default.setText(currentWord.getmDefaultTranslation());
        //find the textView in the list_item.xml with the ID of miwok
        TextView tv_miwok = (TextView)listViewWords.findViewById(R.id.txt_miwok);
        //Set the miwok and set this text to the textview
        tv_miwok.setText(currentWord.getmMiwokTranslation());
        //find the ImageView in the list_item.xml with the ID of miwok
        ImageView mImage = (ImageView) listViewWords.findViewById(R.id.image);
        //Set the imageView to the resouce specified in the current word

        if(currentWord.hasImage())
        {
            mImage.setVisibility(View.VISIBLE);
            mImage.setImageResource(currentWord.getImageResource_id());
        }else
        {
            mImage.setVisibility(View.GONE);
        }
        //Set the theme color for the lisrView
        View textContainer = listViewWords.findViewById(R.id.color_container);
        //Find the color that the resource id maps to
        int color = ContextCompat.getColor(getContext(),color_resourceID);
        //Set the backround color of the text_container
        textContainer.setBackgroundColor(color);

        return listViewWords;
    }




}
