package com.example.shotokansyllabus;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, HashMap<String, List<String>>> expandableListDetail;
    private Map<String, String> beltColours;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle, HashMap<String, HashMap<String, List<String>>> expandableListDetail, Map<String, String> beltColours) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.beltColours = beltColours;
    }

    public void updateData(List<String> newExpandableListTitle, HashMap<String, HashMap<String, List<String>>> newExpandableListDetail, Map<String, String> newBeltColours) {
        this.expandableListTitle = newExpandableListTitle;
        this.expandableListDetail = newExpandableListDetail;
        this.beltColours = newBeltColours;
        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        String groupTitle = this.expandableListTitle.get(listPosition);
        HashMap<String, List<String>> details = this.expandableListDetail.get(groupTitle);
        StringBuilder detailText = new StringBuilder();

        if (details.containsKey("basics")) {
            detailText.append("Basics:\n");
            for (String item : details.get("basics")) {
                detailText.append(" * ").append(item).append("\n");
            }
        }

        if (details.containsKey("kicks")) {
            detailText.append("Kicks:\n");
            for (String item : details.get("kicks")) {
                detailText.append(" * ").append(item).append("\n");
            }
        }

        if (details.containsKey("kata")) {
            detailText.append("Kata:\n");
            for (String item : details.get("kata")) {
                detailText.append(" * ").append(item).append("\n");
            }
        }

        if (details.containsKey("kumite")) {
            detailText.append("Kumite:\n");
            for (String item : details.get("kumite")) {
                detailText.append(" * ").append(item).append("\n");
            }
        }

        if (details.containsKey("combinations")) {
            detailText.append("Combinations:\n");
            for (String item : details.get("combinations")) {
                detailText.append(" * ").append(item).append("\n");
            }
        }

        return detailText.toString();
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = convertView.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return 1; // We have a single concatenated string to display per group
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, android.graphics.Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        String beltColour = beltColours.get(listTitle);
        if (beltColour != null) {
            listTitleTextView.setBackgroundColor(Color.parseColor(beltColour));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
