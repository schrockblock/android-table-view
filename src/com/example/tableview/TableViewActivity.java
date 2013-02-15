package com.example.tableview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class TableViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_view);
		
		ListView tableView = (ListView)findViewById(R.id.table_view);
		TableViewDataSource tvds = new TableViewDataSource(this){

			@Override
			protected int numberOfSections() {
				return 3;
			}

			@Override
			protected String titleForHeaderInSection(int section) {
				return "Header title for section "+section;
			}

			@Override
			protected int numberOfRowsInSection(int section) {
				return 4;
			}

			@Override
			protected DefaultCell cellForRowAtIndexPath(JIndexPath indexPath) {
				DefaultCell cell = new DefaultCell();
				
				cell.hasDisclosureIndicator = true;
				cell.textLabelText = "default "+indexPath.row;
				return cell;
			}
			
		};
		
		tableView.setAdapter(tvds);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_table_view, menu);
		return true;
	}

}
