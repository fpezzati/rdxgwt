package edu.pezzati.rdxgwt.client.todo;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TodoListView implements IsWidget {

	private FlexTable todoTable;
	/**
	 * Helps finding which {@link Todo} object was edited in the {@link TextBox} widget.
	 */
	private Map<TextBox, Todo> todoTextField;
	private EventBus eventBus;
	
	public TodoListView() {
		todoTextField = new HashMap<>();
	}

	@Override
	public Widget asWidget() {
		HorizontalPanel basePanel = new HorizontalPanel();
		todoTable = new FlexTable();
		todoTable.setText(0, 0, "");
		TextBox mementoField = new TextBox();
		mementoField.addValueChangeHandler(getMementoFieldChangeHandler());
		Button removeButton = new Button("-");
		removeButton.addClickHandler(getRemoveButtonClickHandler());
		todoTable.setWidget(0, 0, mementoField);
		todoTable.setWidget(0, 1, removeButton);
		Button add = new Button("+");
		add.addClickHandler(getAddButtonClickHandler());
		Button clear = new Button("clear");
		clear.addClickHandler(getClearButtonClickHandler());
		VerticalPanel leftPanel = new VerticalPanel();
		leftPanel.add(todoTable);
		leftPanel.addStyleName("leftPanel");
		VerticalPanel rightPanel = new VerticalPanel();
		rightPanel.add(add);
		rightPanel.add(clear);
		rightPanel.addStyleName("rightPanel");
		basePanel.add(leftPanel);
		basePanel.add(rightPanel);
		return basePanel;
	}
	
	public void refresh(TodoListModel model) {
		todoTable.clear();
		todoTextField.clear();
		for(Todo todo : model.getTodoList()) {
			TextBox mementoField = new TextBox();
			mementoField.addValueChangeHandler(getMementoFieldChangeHandler());
			Button removeButton = new Button("-");
			removeButton.addClickHandler(getRemoveButtonClickHandler());
			mementoField.setValue(todo.getMemento());
			todoTable.insertRow(todoTable.getRowCount());
			todoTable.setWidget(todoTable.getRowCount(), 0, mementoField);
			todoTable.setWidget(todoTable.getRowCount(), 1, removeButton);
			todoTextField.put(mementoField, todo);
		}
	}
	
	private ClickHandler getAddButtonClickHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("click add");
			}
		};
	}

	private ClickHandler getClearButtonClickHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("click clear");
			}
		};
	}

	private ValueChangeHandler<String> getMementoFieldChangeHandler() {
		return new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				Todo todo = todoTextField.get(event.getSource());
				GWT.log("edit todo. New value: " + event.getValue() + ". It was: " + todo.getMemento());
			}
		};
	}
	
	private ClickHandler getRemoveButtonClickHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("click remove");
			}
		};
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}
}
