package pt.ipbeja.estig.ipc.docloc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ChatTab extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_tab, container, false);
    }

}


