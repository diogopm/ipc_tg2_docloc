package pt.ipbeja.estig.ipc.docloc;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Random;

public class PersonView extends AppCompatActivity
{
    Person person;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_view);
        PersonManager pm = PersonManager.getInstance();

        Intent intent = getIntent();


        this.person = (Person) intent.getSerializableExtra("person");
        for (Person p : pm.getPersonList())
        {
            if(p.equals(person))
            {
                person = p;
                break;
            }
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(person.getFullName());
        setup();

        loadBackdrop();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setup();
    }

    private void setup()
    {
        FloatingActionButton fa = (FloatingActionButton) findViewById(R.id.fab_favorite);
        if(this.person.isFavorite())
        {
            fa.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white));
        }
        else
        {
            fa.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_unchecked));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBackdrop()
    {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.testeg).centerCrop().into(imageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    public void onClickFavorite(View item)
    {
        FloatingActionButton fa = (FloatingActionButton) item;
        this.person.toggleFavorite();

        if (this.person.isFavorite())
        {
            Snackbar.make(item.getRootView(), this.person.getFullName() + " added to your favorites.", Snackbar.LENGTH_SHORT).show();
            fa.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white));
        } else
        {
            Snackbar.make(item.getRootView(), this.person.getFullName() + " removed from your favorites.", Snackbar.LENGTH_SHORT).show();
            fa.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_unchecked));
        }

    }

}
