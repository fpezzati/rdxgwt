package edu.pezzati.rdxgwt.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootPanel;

import edu.pezzati.rdxgwt.client.todo.Todo;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.TodoListPresenter;
import edu.pezzati.rdxgwt.client.todo.TodoListView;
import edu.pezzati.rdxgwt.client.todo.reducer.AddTodo;
import edu.pezzati.rdxgwt.client.todo.reducer.ClearList;
import edu.pezzati.rdxgwt.client.todo.reducer.RemoveTodo;
import edu.pezzati.rdxgwt.client.todo.reducer.TodoReducer;
import edu.pezzati.rdxgwt.client.todo.reducer.UpdateTodo;

public class rdxgwt implements EntryPoint {
	
	private TodoListPresenter presenter;
	private TodoListModel model;
	private TodoListView view;

	public rdxgwt() {
		model = new TodoListModel();
		model.setTodoList(new ArrayList<Todo>());
		model.getTodoList().add(new Todo());
		presenter = new TodoListPresenter();
		view = new TodoListView();
		TodoReducer addTodo = new AddTodo();
		TodoReducer clearTodos = new ClearList();
		TodoReducer removeTodo = new RemoveTodo();
		TodoReducer updateTodo = new UpdateTodo();
		presenter.getReducers().put(addTodo.getActionType(), addTodo);
		presenter.getReducers().put(clearTodos.getActionType(), clearTodos);
		presenter.getReducers().put(removeTodo.getActionType(), removeTodo);
		presenter.getReducers().put(updateTodo.getActionType(), updateTodo);
		EventBus eventBus = new SimpleEventBus();
		presenter.setEventbus(eventBus);
		presenter.setModel(model);
		presenter.setView(view);
		view.setEventBus(eventBus);
	}

	public void onModuleLoad() {
		RootPanel.get().add(view);
		view.refresh(model);
	}
}
