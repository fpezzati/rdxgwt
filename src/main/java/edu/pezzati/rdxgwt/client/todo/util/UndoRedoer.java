package edu.pezzati.rdxgwt.client.todo.util;

import java.util.LinkedList;
import java.util.Map;

import edu.pezzati.rdxgwt.client.todo.TodoListEvent;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.TodoReducer;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;

public interface UndoRedoer {

	void setModel(TodoListModel todoListModel);

	TodoListModel getModel();

	void addEventToQueue(TodoListEvent todoListEvent) throws TodoListException;

	LinkedList<TodoListEvent> getEventQueue();

	void setEventQueueMaxSize(int i);

	TodoListModel popElementFromQueue(TodoListModel model, TodoListEvent pop) throws TodoListException;

	Map<String, TodoReducer> getReducers();
}
