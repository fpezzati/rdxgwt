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
		Todo todo1 = new Todo();
		todo1.setMemento("Lorem ipsum quad dolor sim something...");
		Todo todo2 = new Todo();
		todo2.setMemento("Pick baby from granpas.");
		Todo todo3 = new Todo();
		todo3.setMemento("Buy something to eat for dinner.");
		Todo todo4 = new Todo();
		todo4.setMemento("Pay the two bills.");
		Todo todo5 = new Todo();
		todo5.setMemento("Relax fiveteen minutes before go to sleep.");
		
		
		todoList.add(todo1);
		todoList.add(todo2);
		todoList.add(todo3);
		todoList.add(todo4);
		todoList.add(todo5);
		todoListModel.setTodoList(todoList );
		return todoListModel;
	}
}
