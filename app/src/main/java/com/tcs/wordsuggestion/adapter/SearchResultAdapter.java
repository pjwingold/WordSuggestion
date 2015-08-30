package com.tcs.wordsuggestion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tcs.wordsuggestion.common.TCSApplication;
import com.tcs.wordsuggestion.parser.TCSWordParser;
import com.tcs.wordsuggestion.parser.entity.WordParseResult;

import java.util.List;

/**
 * Adapter for the auto complete textview, get source from in memory cached source,
 * and pass it and the filter text to the Parser library for look-up results
 *
 */
public class SearchResultAdapter extends ArrayAdapter<String> implements Filterable {
    public SearchResultAdapter(Context context) {
        super(context, -1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;

        if (convertView != null) {
            tv = (TextView) convertView;
        }
        else {
            tv = (TextView) LayoutInflater.from(getContext()).inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }
        tv.setText(getItem(position));

        return tv;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint == null) {
                    return null;
                }
                List<WordParseResult> results = TCSWordParser.parseCSV(constraint.toString(), TCSApplication.getSource());

                FilterResults filterResults = new FilterResults();
                filterResults.values = results;
                filterResults.count = results.size();

                return filterResults;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();//clear previous results
                if (results == null || results.count == 0) {
                    notifyDataSetInvalidated();
                    return;
                }

                for (WordParseResult result : (List<WordParseResult>) results.values) {
                    add(result.getResult());
                }

                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return resultValue == null ? "" : resultValue.toString();
            }
        };

        return filter;
    }
}
