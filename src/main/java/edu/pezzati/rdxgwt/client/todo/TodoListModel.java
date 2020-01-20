package edu.pezzati.rdxgwt.client.todo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class TodoListModel implements Serializable {

	private static final long serialVersionUID = 4787325855591319709L;
	private Collection<Todo> todoList;
	
	public TodoListModel() {
		todoList = new ArrayList<Todo>();
	}
	
	public TodoListModel(TodoListModel todoListModel) {
		this.todoList = new ArrayList<Todo>();
		for(Todo todo : todoListModel.todoList) {
			this.todoList.add(new Todo(todo));
		}
	}

	public Collection<Todo> getTodoList() {
		return todoList;
	}
	
	public void setTodoList(Collection<Todo> todoList) {
		this.todoList = todoList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(todoList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TodoListModel other = (TodoListModel) obj;
		if (todoList == null) {
			if (other.todoList != null)
				return false;
		} else if (!todoList.equals(other.todoList))
			return false;
		return true;
	}
}
