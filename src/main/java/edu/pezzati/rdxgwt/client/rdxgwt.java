package edu.pezzati.rdxgwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import edu.pezzati.rdxgwt.client.todo.TodoListView;

public class rdxgwt implements EntryPoint {
  
  public void onModuleLoad() {
	  RootPanel.get().add(new TodoListView());
  }
}
