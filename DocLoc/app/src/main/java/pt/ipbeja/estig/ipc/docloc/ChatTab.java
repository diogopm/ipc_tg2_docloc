package pt.ipbeja.estig.ipc.docloc;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ChatTab extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private ChatAdapter adapter;

    static ArrayList<ChatItem> chatItems = new ArrayList<>();;

    public static ChatTab newInstance(int sectionNumber)
    {
        ChatTab fragment = new ChatTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public ChatTab()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_chat_tab, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.adapter = new ChatAdapter(getActivity(), chatItems);
        this.recyclerView.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setReverseLayout(true);
        this.recyclerView.setLayoutManager(lm);
        if(chatItems.size() < 1)
        {
            chatItems.add(new ChatItem("cenas"));
            chatItems.add(new ChatItem("coisas"));
            chatItems.add(new ChatItem("mais cenas"));
            chatItems.add(new ChatItem("mais coisas"));
            chatItems.add(new ChatItem("cenas"));
            chatItems.add(new ChatItem("coisas"));
            chatItems.add(new ChatItem("mais cenas"));
            chatItems.add(new ChatItem("mais coisas"));
        }


        return view;
    }



    public class ChatItem
    {
        String message;
        String time;

        public ChatItem(String message)
        {
            this.message = message;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            this.time = df.format(c.getTime());
        }
    }

    public class ChatAdapter extends RecyclerView.Adapter<ChatHolder>
    {

        private LayoutInflater inflater;
        private List<ChatItem> data;

        public ChatAdapter(Context context, List<ChatItem> data)
        {
            inflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = inflater.inflate(R.layout.chat_item_layout, parent, false);
            ChatHolder fh = new ChatHolder(view);
            return fh;
        }

        @Override
        public void onBindViewHolder(ChatHolder holder, int position)
        {
            ChatItem chat = data.get(position);
            holder.message.setText(chat.message);
            holder.time.setText(chat.time);
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }
    }
    public class ChatHolder extends RecyclerView.ViewHolder
    {
        TextView message;
        TextView time;

        public ChatHolder(View itemView)
        {
            super(itemView);

            message = (TextView) itemView.findViewById(R.id.message);
            time = (TextView) itemView.findViewById(R.id.time);


        }

    }

}


