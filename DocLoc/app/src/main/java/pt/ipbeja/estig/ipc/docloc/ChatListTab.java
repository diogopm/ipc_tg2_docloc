package pt.ipbeja.estig.ipc.docloc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ChatListTab extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private ChatListAdapter adapter;

    static ArrayList<ChatListItem> chatListItems = new ArrayList<>();;

    public static ChatListTab newInstance(int sectionNumber)
    {
        ChatListTab fragment = new ChatListTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public ChatListTab()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_chatlisttab, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.adapter = new ChatListAdapter(getActivity(), chatListItems);
        this.recyclerView.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(lm);
        if(chatListItems.size() < 1)
        {
            chatListItems.add(new ChatListItem(PersonManager.getInstance().getPersonList().get(0), "por aí? preciso de fazer-lhe uma pergunta se não se importar"));
            chatListItems.add(new ChatListItem(PersonManager.getInstance().getPersonList().get(2), "então até logo"));
        }


        return view;
    }



    public class ChatListItem
    {
        String name;
        String message;
        String time;
        Person person;

        public ChatListItem(Person person, String message)
        {
            this.person = person;
            this.message = message;
            this.name = person.fullName();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            this.time = df.format(c.getTime());
        }
    }

    public class ChatListAdapter extends RecyclerView.Adapter<ChatListHolder>
    {

        private LayoutInflater inflater;
        private List<ChatListItem> data;

        public ChatListAdapter(Context context, List<ChatListItem> data)
        {
            inflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public ChatListHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = inflater.inflate(R.layout.chatlist_item_layout, parent, false);
            ChatListHolder fh = new ChatListHolder(view);
            return fh;
        }

        @Override
        public void onBindViewHolder(ChatListHolder holder, int position)
        {
            ChatListItem chat = data.get(position);
            holder.name.setText(chat.name);
            holder.message.setText(chat.message);
            holder.time.setText(chat.time);
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }
    }
    public class ChatListHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name;
        TextView message;
        TextView time;

        public ChatListHolder(View itemView)
        {
            super(itemView);
            itemView.findViewById(R.id.card_layout).setOnClickListener(this);
            message = (TextView) itemView.findViewById(R.id.textView_msg);
            time = (TextView) itemView.findViewById(R.id.textview_time);
            name = (TextView) itemView.findViewById(R.id.textView_name);

        }

        @Override
        public void onClick(View v)
        {
            Intent a = new Intent(getActivity(), PersonView.class);
            Person p = PersonManager.getInstance().getPersonList().get(getAdapterPosition());
            Person person = chatListItems.get(getAdapterPosition()).person;
            a.putExtra("person", person);
            a.putExtra("chat", true);
            startActivity(a);
        }
    }

}


