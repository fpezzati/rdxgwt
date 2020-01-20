package edu.pezzati.rdxgwt.client.todo.reducer;

import java.util.ArrayList;
import java.util.Collection;

import edu.pezzati.rdxgwt.client.todo.Todo;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListInvalidData;

public class UpdateTodo implements TodoReducer {

	@Override
	public TodoListModel reduce(TodoListModel model, Object data) throws TodoListException {
		if(model == null || model.getTodoList() == null || data == null) return model;
		if(!(data instanceof Todo[]) || ((Todo[])data).length < 2) throw new TodoListInvalidData();
		Collection<Todo> todos = new ArrayList<Todo>();
		for(Todo todo : model.getTodoList()) {
			if(todo.equals(((Todo[])data)[0])) {
				todos.add(((Todo[])data)[1]);
			} else {
				todos.add(todo);
			}
		}
		model.setTodoList(todos);
		return model;
	}

	@Override
	public String getActionType() {
		return "update-todo";
	}

}
