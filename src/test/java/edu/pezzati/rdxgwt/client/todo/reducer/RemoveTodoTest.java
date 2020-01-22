package edu.pezzati.rdxgwt.client.todo.reducer;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.pezzati.rdxgwt.client.todo.Todo;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListInvalidData;

public class RemoveTodoTest {
	
	@Rule
	public ExpectedException expEx = ExpectedException.none();
	private TodoReducer removeTodo;
	private TodoListModel model;
	private Object data;

	@Before
	public void initEach() {
		removeTodo = new RemoveTodo();
	}
	
	@Test
	public void removeTodoCanHandleRemoveTodoActionsType() {
		Assert.assertEquals("remove-todo", removeTodo.getActionType());
	}
	
	@Test
	public void removeTodoDoesNothingWhenModelIsNull() throws TodoListException {
		TodoListModel actual = removeTodo.reduce(model, data);
		Assert.assertNull(actual);
	}
	
	@Test
	public void removeTodoDoesNothingWhenDataIsNull() throws TodoListException {
		TodoListModel expected = getModelWithSomeTodos();
		TodoListModel actual = removeTodo.reduce(getModelWithSomeTodos(), null);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void removeTodoExpectsATodoAsData() throws TodoListException {
		expEx.expect(TodoListInvalidData.class);
		List<Integer> intList = Arrays.asList(1, 3, 5);
		removeTodo.reduce(getModelWithSomeTodos(), intList);
		Assert.fail();
	}
	
	@Test
	public void removeTodoDoesNothingWhenGivenTodoDoesNotMatchWithAnyTodoInModel() throws TodoListException {
		TodoListModel expected = getModelWithSomeTodos();
		TodoListModel actual = removeTodo.reduce(getModelWithSomeTodos(), new Todo().setUuid("4").setMemento("Not my note!"));
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void givenATodoRemoveTodoDeleteTheTodoInModelThatMatchesWithTheGivenOne() throws TodoListException {
		TodoListModel expected = getModelWithSomeLessTodos();
		TodoListModel actual = removeTodo.reduce(getModelWithSomeTodos(), new Todo().setUuid("2").setMemento("not so important thing."));
		Assert.assertEquals(expected, actual);
	}

	private TodoListModel getModelWithSomeTodos() {
		TodoListModel tlm = new TodoListModel();
		tlm.getTodoList().add(new Todo().setUuid("1").setMemento("remember this."));
		tlm.getTodoList().add(new Todo().setUuid("2").setMemento("not so important thing."));
		tlm.getTodoList().add(new Todo().setUuid("3").setMemento("son's birthday party tomorrow."));
		return tlm;
	}
	
	private TodoListModel getModelWithSomeLessTodos() {
		TodoListModel tlm = getModelWithSomeTodos();
		tlm.getTodoList().remove(new Todo().setUuid("2").setMemento("not so important thing."));
		return tlm;
	}
}
