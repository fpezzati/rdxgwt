package edu.pezzati.rdxgwt.client.todo.reducer;

import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;

public interface TodoReducer {

	public TodoListModel reduce(TodoListModel model, Object data) throws TodoListException;

	public String getActionType();
}
