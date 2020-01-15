package edu.pezzati.rdxgwt.client.todo;

import java.util.Collection;

public class TodoListModel {

	private Collection<Todo> todoList;

	public Collection<Todo> getTodoList() {
		return todoList;
	}
	
	public void setTodoList(Collection<Todo> todoList) {
		this.todoList = todoList;
	}
}
