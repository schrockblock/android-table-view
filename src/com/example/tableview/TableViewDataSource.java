package com.example.tableview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class TableViewDataSource extends BaseAdapter {
	Context context;
	
	public TableViewDataSource(Context ctxt){
		this.context = ctxt;
	}

	@Override
	public int getCount() {
		int result = 0;
		int numOfSecs = numberOfSections();
		if (numOfSecs==1)
			result = numberOfRowsInSection(0);
		else {
			for (int i = 0; i<numOfSecs; i++){
				result += numberOfRowsInSection(i);
			}
		}
		return result;
	}
	
	protected abstract int numberOfSections();
	
	protected abstract String titleForHeaderInSection(int section);
	
	protected abstract int numberOfRowsInSection(int section);
	
	protected abstract DefaultCell cellForRowAtIndexPath(JIndexPath indexPath);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DefaultCell dc = cellForRowAtIndexPath(new JIndexPath(getSectionForPosition(position), getRelativeIndexForPosition(position)));
		
		if (dc.getClass() == DefaultCell.class){
			DefaultHolder dh;
			
			if (convertView == null || convertView.getTag().getClass() != DefaultHolder.class){
				LayoutInflater inflater = ((Activity)context).getLayoutInflater();
				convertView = inflater.inflate(R.layout.cell, parent, false);
				
				dh = new DefaultHolder();

				dh.header = (TextView)convertView.findViewById(R.id.header_text_view);
				dh.title = (TextView)convertView.findViewById(R.id.title_text_view);
				dh.disclosureIndicator = (ImageView)convertView.findViewById(R.id.disclosure_indicator);
				TextView tv = (TextView)convertView.findViewById(R.id.detail_text_view);
				tv.setVisibility(View.GONE);
				
				convertView.setTag(dh);
			}else{
				dh = (DefaultHolder)convertView.getTag();
				
				convertView.setTag(dh);
			}

			if (getRelativeIndexForPosition(position) == 0){
				String t = titleForHeaderInSection(getSectionForPosition(position));
					dh.header.setText(t);
					dh.header.setVisibility(View.VISIBLE);
			}else{
				dh.header.setVisibility(View.GONE);
			}
			
			dh.title.setText(dc.textLabelText);
			if (dc.hasDisclosureIndicator){
				dh.disclosureIndicator.setVisibility(View.VISIBLE);
			}
		}else if (dc.getClass() == SubtitleCell.class){
			SubtitleHolder dh;
			
			if (convertView == null || convertView.getTag().getClass() != SubtitleHolder.class){
				LayoutInflater inflater = ((Activity)context).getLayoutInflater();
				convertView = inflater.inflate(R.layout.cell, parent, false);
				
				dh = new SubtitleHolder();

				dh.header = (TextView)convertView.findViewById(R.id.header_text_view);
				dh.title = (TextView)convertView.findViewById(R.id.title_text_view);
				dh.disclosureIndicator = (ImageView)convertView.findViewById(R.id.disclosure_indicator);
				dh.detailTextView = (TextView)convertView.findViewById(R.id.detail_text_view);
				dh.detailTextView.setVisibility(View.VISIBLE);
				
				convertView.setTag(dh);
			}else{
				dh = (SubtitleHolder)convertView.getTag();
				
				convertView.setTag(dh);
			}

			if (getRelativeIndexForPosition(position) == 0){
				String t = titleForHeaderInSection(getSectionForPosition(position));
					dh.header.setText(t);
					dh.header.setVisibility(View.VISIBLE);
			}else{
				dh.header.setVisibility(View.GONE);
			}
			
			if (dc.hasDisclosureIndicator){
				dh.disclosureIndicator.setVisibility(View.VISIBLE);
			}
			
			dh.title.setText(dc.textLabelText);
			dh.detailTextView.setText(((SubtitleCell)dc).detailTextLabelText);
		}
		return convertView;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	private int getSectionForPosition(int position){
		int result = 0;
		int numOfSecs = numberOfSections();
		if (numOfSecs != 1){
			for (result = 0; result < numOfSecs; result++){
				int rows = numberOfRowsInSection(result);
				if (position - rows < 0){
					break;
				}
				position -= rows;
			}
		}
		return result;
	}
	
	private int getRelativeIndexForPosition(int position){
		int numOfSecs = numberOfSections();
		int result = position;
		if (numOfSecs != 1){
			for (int i = 0; i < numOfSecs; i++){
				int rows = numberOfRowsInSection(i);
				if (result - rows < 0){
					break;
				}
				result = result - rows;
			}
		}
		return result;
	}
	
	class JIndexPath{
		int section;
		int row;
		
		public JIndexPath(int section, int row){
			this.section = section;
			this.row = row;
		}
	}
	
	class DefaultCell{
		String textLabelText;
		int bgColor;
		boolean hasDisclosureIndicator;
	}
	
	class DefaultHolder{
		TextView header;
		TextView title;
		ImageView disclosureIndicator;
	}
	
	class SubtitleCell extends DefaultCell{
		String detailTextLabelText;
	}
	
	class SubtitleHolder extends DefaultHolder{
		TextView detailTextView;
	}

}
