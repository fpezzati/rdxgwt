package edu.pezzati.rdxgwt.client.todo.reducer;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.pezzati.rdxgwt.client.todo.Todo;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListInvalidData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Rule;

public class UpdateTodoTest {
	
	@Rule
	public ExpectedException expEx = ExpectedException.none();
	private TodoReducer updateTodo = new UpdateTodo();

	@Test
	public void updateTodoHandlesUpdateTodoActions() {
		Assert.assertEquals("update-todo", updateTodo.getActionType());
	}
	
	@Test
	public void updateTodoDoesNothingIfModelIsNull() throws TodoListException {
		TodoListModel tlm = null;
		Todo[] data = { new Todo().setMemento("before"), new Todo().setMemento("after") };
		updateTodo.reduce(tlm , data );
		Assert.assertNull(tlm);
	}
	
	@Test
	public void updateTodoDoesNothingIfDataIsNull() throws TodoListException {
		TodoListModel expected = getModelWithSomeTodo();
		Object data = null;
		TodoListModel actual = updateTodo.reduce(getModelWithSomeTodo(), data);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void updateTodoExpectsAnArrayOfTwoTodoAsData() throws TodoListException {
		Object data = Arrays.asList("something", "wrong", "is gonna", "happend");
		expEx.expect(TodoListInvalidData.class);
		updateTodo.reduce(getModelWithSomeTodo(), data);
		Assert.fail();
	}
	
	@Test
	public void ifThereIsNoMatchBetweenGivenTodoAndModelTodoListUpdateTodoDoesNothing() throws TodoListException {
		TodoListModel expected = getModelWithSomeTodo();
		Todo[] data = { new Todo().setMemento("something 5."), new Todo().setMemento("brand new!")};
		TodoListModel actual = updateTodo.reduce(getModelWithSomeTodo(), data);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void ifThereIsAMatchBetweenGivenTodoAndATodoInModelUpdateTodoSubstitutesTheTodoInModelWithTheGivenOne() throws TodoListException {
		TodoListModel expected = getModelWithUpdatedTodo();
		Todo[] data = { new Todo().setMemento("something 2."), new Todo().setMemento("brand new!")};
		TodoListModel actual = updateTodo.reduce(getModelWithSomeTodo(), data);
		Assert.assertEquals(expected, actual);
	}
	
	private TodoListModel getModelWithSomeTodo() {
		TodoListModel tlm = new TodoListModel();
		tlm.getTodoList().add(new Todo().setMemento("something 1."));
		tlm.getTodoList().add(new Todo().setMemento("something 2."));
		tlm.getTodoList().add(new Todo().setMemento("something 3."));
		return tlm;
	}
	
	private TodoListModel getModelWithUpdatedTodo() {
		TodoListModel tlm = getModelWithSomeTodo();
		Collection<Todo> todos = new ArrayList<Todo>();
		for(Todo todo : tlm.getTodoList()) {
			if(todo.equals(new Todo().setMemento("something 2."))) {
				todos.add(new Todo().setMemento("brand new!"));
			} else {
				todos.add(todo);
			}
		}
		tlm.setTodoList(todos);
		return tlm;
	}
}
