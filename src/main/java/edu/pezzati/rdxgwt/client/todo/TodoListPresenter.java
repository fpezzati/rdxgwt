package edu.pezzati.rdxgwt.client.todo;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import com.google.web.bindery.event.shared.Event.Type;

import edu.pezzati.rdxgwt.client.todo.reducer.TodoReducer;

public class TodoListPresenter {
	
	private Map<String, TodoReducer> reducers;
	private EventBus eventbus;

	public TodoListPresenter() {
		setReducers(new HashMap<String, TodoReducer>());
	}

	public Map<String, TodoReducer> getReducers() {
		return reducers;
	}

	public void setReducers(Map<String, TodoReducer> reducers) {
		this.reducers = reducers;
	}

	public void setEventbus(EventBus eventbus) {
		this.eventbus = eventbus;
		this.eventbus.addHandler(TodoListEventHandler.Type, new TodoListEventHandler() {
			@Override
			public void onChange(TodoListEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
