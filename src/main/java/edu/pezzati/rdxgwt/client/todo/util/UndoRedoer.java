package edu.pezzati.rdxgwt.client.todo.util;

import java.util.LinkedList;

import edu.pezzati.rdxgwt.client.todo.TodoListEvent;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;

public interface UndoRedoer {

	void setModel(TodoListModel todoListModel);

	TodoListModel getModel();

	void addEventToQueue(TodoListEvent todoListEvent);

	LinkedList<TodoListEvent> getEventQueue();

	void setEventQueueMaxSize(int i);

	void popElementFromQueue(TodoListModel model, TodoListEvent pop);
}
