package edu.pezzati.rdxgwt.client.todo.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import edu.pezzati.rdxgwt.client.todo.TodoListEvent;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.TodoReducer;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;

public class SimpleUndoRedoer implements UndoRedoer {

	private TodoListModel todoListModel;
	private LinkedList<TodoListEvent> queue;
	private int queueMaxSize;
	private Map<String, TodoReducer> reducers;

	public SimpleUndoRedoer() {
		queue = new LinkedList<>();
		reducers = new HashMap<>();
	}

	@Override
	public void setModel(TodoListModel todoListModel) {
		if (todoListModel == null)
			return;
		this.todoListModel = new TodoListModel(todoListModel);
	}

	@Override
	public TodoListModel getModel() {
		return todoListModel;
	}

	@Override
	public void addEventToQueue(TodoListEvent todoListEvent) throws TodoListException {
		if (todoListEvent == null)
			return;
		queue.add(todoListEvent);
		if (queue.size() <= queueMaxSize)
			return;
		popElementFromQueue(getModel(), queue.pop());
	}

	@Override
	public LinkedList<TodoListEvent> getEventQueue() {
		return queue;
	}

	@Override
	public void setEventQueueMaxSize(int queueMaxSize) {
		this.queueMaxSize = queueMaxSize;
	}

	/**
	 * This is abstract because redoer should not know about reducers. So this
	 * method will be implemented into some presenter who wants to implement
	 * undo/redo.
	 * @throws TodoListException 
	 */
	@Override
	public TodoListModel popElementFromQueue(TodoListModel model, TodoListEvent pop) throws TodoListException {
		return reducers.get(pop.getType()).reduce(model, pop.getData());
	}

	@Override
	public Map<String, TodoReducer> getReducers() {
		return reducers;
	}
}
