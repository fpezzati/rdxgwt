package edu.pezzati.rdxgwt.client.todo.reducer;

import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;

public class UpdateTodo implements TodoReducer {

	@Override
	public TodoListModel reduce(TodoListModel model, Object data) throws TodoListException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getActionType() {
		return "update-todo";
	}

}
