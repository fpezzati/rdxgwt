package edu.pezzati.rdxgwt.client;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import edu.pezzati.rdxgwt.client.todo.Todo;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.TodoListView;

public class rdxgwt implements EntryPoint {

	public void onModuleLoad() {
		TodoListView todoListView = new TodoListView();
		TodoListModel todoListModel = new TodoListModel();
		todoListModel = getSomeComplexModel();
		RootPanel.get().add(todoListView);
		todoListView.refresh(todoListModel);
	}
	
	private TodoListModel getSomeComplexModel() {
		TodoListModel todoListModel = new TodoListModel();
		Collection<Todo> todoList = new ArrayList<Todo>();
		todoList.add(new Todo().setMemento("Lorem ipsum quad dolor sim something..."));
		todoList.add(new Todo().setMemento("Pick baby from granpas."));
		todoList.add(new Todo().setMemento("Buy something to eat for dinner."));
		todoList.add(new Todo().setMemento("Pay the two bills."));
		todoList.add(new Todo().setMemento("Relax fiveteen minutes before go to sleep."));
		todoListModel.setTodoList(todoList );
		return todoListModel;
	}
}
