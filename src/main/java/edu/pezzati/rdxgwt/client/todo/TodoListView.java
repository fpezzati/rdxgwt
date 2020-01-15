package edu.pezzati.rdxgwt.client.todo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TodoListView implements IsWidget {

	private FlexTable todoTable;

	@Override
	public Widget asWidget() {
		HorizontalPanel basePanel = new HorizontalPanel();
		todoTable = new FlexTable();
		todoTable.setText(0, 0, "");
		TextBox mementoField = new TextBox();
		mementoField.addChangeHandler(getMementoFieldChangeHandler());
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
		for(Todo todo : model.getTodoList()) {
			TextBox mementoField = new TextBox();
			mementoField.addChangeHandler(getMementoFieldChangeHandler());
			Button removeButton = new Button("-");
			removeButton.addClickHandler(getRemoveButtonClickHandler());
			mementoField.setValue(todo.getMemento());
			todoTable.insertRow(todoTable.getRowCount());
			todoTable.setWidget(todoTable.getRowCount(), 0, mementoField);
			todoTable.setWidget(todoTable.getRowCount(), 1, removeButton);
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

	private ChangeHandler getMementoFieldChangeHandler() {
		return new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				GWT.log("edit todo");
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
}
