package edu.pezzati.rdxgwt.client.todo.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.pezzati.rdxgwt.client.todo.Todo;
import edu.pezzati.rdxgwt.client.todo.TodoListEvent;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;

public class UndoRedoerTest {

	private UndoRedoer unRe;

	@Before
	public void initEach() {
		unRe = Mockito.spy(new SimpleUndoRedoer() {
			@Override
			public void popElementFromQueue(TodoListModel model, TodoListEvent pop) {
				// DO NOTHING.
			}
		});
		unRe.setEventQueueMaxSize(10);
	}

	@Test
	public void undoRedoerDoNothingWhenGivenModelIsNull() {
		unRe.setModel(new TodoListModel());
		unRe.setModel(null);
		Assert.assertNotNull(unRe.getModel());
	}

	@Test
	public void undoRedoerMakesCopyOfTheGivenModel() {
		TodoListModel notExpected = getSimpleTodoListModel();
		unRe.setModel(notExpected);
		Assert.assertEquals(notExpected, unRe.getModel());
		Assert.assertTrue(notExpected != unRe.getModel());
	}
	
	@Test
	public void undoRedoerDoesNotStoreNullEventsInQueue() {
		unRe.addEventToQueue(getTodoListEvents().get(0));
		unRe.addEventToQueue(null);
		unRe.addEventToQueue(getTodoListEvents().get(2));
		Assert.assertEquals(2, unRe.getEventQueue().size());
	}

	@Test
	public void undoRedoerStoreEventsInAQueue() {
		unRe.addEventToQueue(getTodoListEvents().get(0));
		unRe.addEventToQueue(getTodoListEvents().get(1));
		unRe.addEventToQueue(getTodoListEvents().get(2));
		Assert.assertEquals(3, unRe.getEventQueue().size());
	}
	
	@Test
	public void whenEventsInQueueExceedTheMaxEventSizeUndoRedoerPopAnEventAndApplyItToModel() {
		unRe.setEventQueueMaxSize(3);
		unRe.setModel(getSimpleTodoListModel());
		unRe.addEventToQueue(getTodoListEvents().get(0));
		unRe.addEventToQueue(getTodoListEvents().get(1));
		unRe.addEventToQueue(getTodoListEvents().get(2));
		unRe.addEventToQueue(new TodoListEvent() {
			@Override
			public String getType() {
				return "todo-something";
			}
			@Override
			public Object getData() {
				return new Todo();
			}
		});
		Mockito.verify(unRe, Mockito.times(1)).popElementFromQueue(unRe.getModel(), unRe.getEventQueue().pop());
	}

	private TodoListModel getSimpleTodoListModel() {
		TodoListModel tlm = new TodoListModel();
		tlm.getTodoList().add(new Todo().setMemento("something 1."));
		tlm.getTodoList().add(new Todo().setMemento("something 2."));
		tlm.getTodoList().add(new Todo().setMemento("something 3."));
		return tlm;
	}
	
	private List<TodoListEvent> getTodoListEvents() {
		List<TodoListEvent> events = new ArrayList<TodoListEvent>();
		events.add(new TodoListEvent() {
			@Override
			public String getType() {
				return "add-todo";
			}

			@Override
			public Object getData() {
				return new Todo().setMemento("add 1.");
			}
		});
		events.add(new TodoListEvent() {
			@Override
			public String getType() {
				return "add-todo";
			}

			@Override
			public Object getData() {
				return new Todo().setMemento("add 2.");
			}
		});
		events.add(new TodoListEvent() {
			@Override
			public String getType() {
				return "update-todo";
			}

			@Override
			public Object getData() {
				Todo todo1 = new Todo().setMemento("foo");
				Todo todo2 = new Todo().setMemento("bar");
				return new Todo[] { todo1, todo2 };
			}
		});
		return events;
	}
}
