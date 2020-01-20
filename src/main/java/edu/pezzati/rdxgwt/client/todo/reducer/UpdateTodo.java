package edu.pezzati.rdxgwt.client.todo.reducer;

import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;

public class UpdateTodo implements TodoReducer {

	@Override
	public TodoListModel reduce(TodoListModel model, Object data) throws TodoListException {
		if(model == null || model.getTodoList() == null || data == null) return model;
		return null;
	}

	@Override
	public String getActionType() {
		return "update-todo";
	}

}
