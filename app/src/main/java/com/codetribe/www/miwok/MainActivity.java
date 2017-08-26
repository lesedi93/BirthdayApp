package com.codetribe.www.miwok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openNumberList(View view) {
        Intent intent = new Intent(this, NumbersActivity.class);
        startActivity(intent);
    }

    public void openFamilyMember(View view) {
        Intent intent = new Intent(this, FamilyMemberActivity.class);
        startActivity(intent);
    }

    public void openColour(View view) {
        Intent intent = new Intent(this, ColourActivity.class);
        startActivity(intent);

    }

    public void openPhrase(View view) {
        Intent intent = new Intent(this, PhrasesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_words, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_word_tswana:
                Toast.makeText(MainActivity.this, "Tswana", Toast.LENGTH_SHORT).show();
            case R.id.new_words_zulu:
                Toast.makeText(MainActivity.this, "Zulu", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}