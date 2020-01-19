package edu.pezzati.rdxgwt.client.todo.reducer;

import edu.pezzati.rdxgwt.client.todo.TodoListModel;

public class ClearList implements TodoReducer {

	@Override
	public TodoListModel reduce(TodoListModel model, Object data) {
		if(model == null || model.getTodoList() == null) return model;
		model.getTodoList().clear();
		return model;
	}

	@Override
	public String getActionType() {
		return "clear-list";
	}

}
