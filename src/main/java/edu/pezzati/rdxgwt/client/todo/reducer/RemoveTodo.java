package edu.pezzati.rdxgwt.client.todo.reducer;

import edu.pezzati.rdxgwt.client.todo.Todo;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListInvalidData;

public class RemoveTodo implements TodoReducer {

	@Override
	public TodoListModel reduce(TodoListModel model, Object data) throws TodoListException {
		if(model == null || model.getTodoList() == null || data == null) return model;
		if(!(data instanceof Todo)) throw new TodoListInvalidData();
		model.getTodoList().remove(data);
		return model;
	}

	@Override
	public String getActionType() {
		return "remove-todo";
	}

}
