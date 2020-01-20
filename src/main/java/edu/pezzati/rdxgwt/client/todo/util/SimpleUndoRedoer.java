package edu.pezzati.rdxgwt.client.todo.util;

import java.util.LinkedList;

import edu.pezzati.rdxgwt.client.todo.TodoListEvent;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;

public abstract class SimpleUndoRedoer implements UndoRedoer {

	private TodoListModel todoListModel;
	private LinkedList<TodoListEvent> queue;
	private int queueMaxSize;

	public SimpleUndoRedoer() {
		queue = new LinkedList<TodoListEvent>();
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
	public void addEventToQueue(TodoListEvent todoListEvent) {
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
	 */
	@Override
	public abstract void popElementFromQueue(TodoListModel model, TodoListEvent pop);
}
