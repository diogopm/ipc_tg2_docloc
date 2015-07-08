package pt.ipbeja.estig.ipc.docloc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class FavoritesTab extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;


    public static FavoritesTab newInstance(int sectionNumber)
    {
        FavoritesTab fragment = new FavoritesTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public FavoritesTab()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_favorites_tab, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.adapter = new FavoriteAdapter(getActivity(), PersonManager.getInstance().getFavoriteList());
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                Person person = PersonManager.getInstance().getPersonList().get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();
                person.toggleFavorite();
                PersonManager.getInstance().getFavoriteList().remove(position);
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                Snackbar.make(FavoritesTab.this.recyclerView, "item removed", Snackbar.LENGTH_LONG).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        // Inflate the layout for this fragment


        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.adapter.notifyDataSetChanged();
    }

    public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteHolder>
    {

        private LayoutInflater inflater;
        private List<Person> favorites;

        public FavoriteAdapter(Context context, List<Person> data)
        {
            inflater = LayoutInflater.from(context);
            this.favorites = data;
        }

        @Override
        public FavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = inflater.inflate(R.layout.favorite_item_layout, parent, false);
            FavoriteHolder fh = new FavoriteHolder(view);
            return fh;
        }

        @Override
        public void onBindViewHolder(FavoriteHolder holder, int position)
        {
            Person p = favorites.get(position);
            holder.name.setText(p.getFullName());
            holder.status.setText(p.getStatusDescriptive());
            holder.lastSeen.setText(R.string.last_seen);
            holder.statusIcon.setImageResource(p.getStatusDrawableID());
            //holder.profileImage.setImageResource(p.getImage());
            //holder.statusIcon.setImageResource(p.getStatus() == Person.AVAILABLE ? android.R.drawable.presence_online : p.getStatus() == Person.BUSY ? android.R.drawable.presence_busy : android.R.drawable.presence_offline);
        }

        @Override
        public int getItemCount()
        {
            return favorites.size();
        }
    }
    public class FavoriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name;
        TextView status;
        TextView lastSeen;
        ImageView statusIcon;
        //ImageView profileImage;

        public FavoriteHolder(View itemView)
        {
            super(itemView);
            itemView.findViewById(R.id.card_layout).setOnClickListener(this);

            name = (TextView) itemView.findViewById(R.id.textView_name);
            status = (TextView) itemView.findViewById(R.id.textview_status);
            lastSeen = (TextView) itemView.findViewById(R.id.textView_last_seen);
            statusIcon = (ImageView) itemView.findViewById(R.id.imageView_status);
            //profileImage = (ImageView) itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v)
        {
            Intent a = new Intent(getActivity(), PersonView.class);
            Person p = PersonManager.getInstance().getFavoriteList().get(getAdapterPosition());
            a.putExtra("person", p);
            startActivity(a);
        }
    }

}


