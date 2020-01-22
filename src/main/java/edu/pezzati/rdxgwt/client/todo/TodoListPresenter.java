package edu.pezzati.rdxgwt.client.todo;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;

import edu.pezzati.rdxgwt.client.todo.reducer.TodoReducer;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;
import edu.pezzati.rdxgwt.client.todo.undoredo.SimpleUndoRedoer;
import edu.pezzati.rdxgwt.client.todo.undoredo.UndoRedoEvent;
import edu.pezzati.rdxgwt.client.todo.undoredo.UndoRedoEventHandler;
import edu.pezzati.rdxgwt.client.todo.undoredo.UndoRedoer;

public class TodoListPresenter {
	
	private Map<String, TodoReducer> reducers;
	private EventBus eventbus;
	private TodoListModel model;
	private TodoListView view;
	private UndoRedoer undoRedo;
	
	public TodoListPresenter() {
		setReducers(new HashMap<String, TodoReducer>());
		undoRedo = new SimpleUndoRedoer();
		undoRedo.setEventQueueMaxSize(10);
	}

	public Map<String, TodoReducer> getReducers() {
		return reducers;
	}

	public void setReducers(Map<String, TodoReducer> reducers) {
		this.reducers = reducers;
	}

	public void setEventbus(EventBus eventbus) {
		this.eventbus = eventbus;
		this.eventbus.addHandler(TodoListEvent.TYPE, new TodoListEventHandler() {
			@Override
			public void onChange(TodoListEvent event) {
				try {
					GWT.log("Incoming action: " + event.getType());
					if(reducers.containsKey(event.getType())) {
						reducers.get(event.getType()).reduce(model, event.getData());
						view.refresh(model);
					} else {
						GWT.log("action " + event.getType() +" not supported.");
					}
				} catch (TodoListException e) {
					GWT.log(e.getMessage());
				}
			}
		});
		this.eventbus.addHandler(UndoRedoEvent.TYPE, new UndoRedoEventHandler() {
			@Override
			public void onChange(UndoRedoEvent event) {
				GWT.log("Undo about steps: " + event.steps());
				try {
					view.refresh(undoRedo.undoAboutSteps(event.steps()));
				} catch (TodoListException e) {
					GWT.log(e.getMessage());
				}
			}
		});
	}
	
	public void setModel(TodoListModel model) {
		this.model = model;
		undoRedo.setModel(model);
	}

	public void setView(TodoListView view) {
		this.view = view;
	}
}
