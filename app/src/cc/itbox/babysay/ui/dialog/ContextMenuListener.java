package cc.itbox.babysay.ui.dialog;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;

public interface ContextMenuListener {
	public boolean onContextItemSelected(MenuItem item);

	public void onContextMenuClosed(ContextMenu menu);

	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo);
}
