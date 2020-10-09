package com.wbc.supervisor.client.dashboardutilities.utils;
import com.sencha.gxt.widget.core.client.grid.LiveGridView;

public class LiveGridViewExt<M> extends LiveGridView<M> {

  public LiveGridViewExt() {
    super();
  }

  @Override
  public void onHighlightRow( int rowIndex, boolean highlight ) {
    super.onHighlightRow(rowIndex, highlight);
  }

  @Override
  public void onRowSelect(int rowIndex) {
    super.onRowSelect(rowIndex);
  }

  @Override
  public void onRowDeselect( int rowIndex ) {
    super.onRowDeselect(rowIndex);
  }

}
