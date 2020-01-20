package edu.pezzati.rdxgwt.client.todo;

import java.io.Serializable;
import java.util.Objects;


public class Todo implements Serializable {

	private static final long serialVersionUID = -5520325283377072191L;
	private String memento;
	
	public Todo() {}

	public Todo(Todo todo) {
		this.memento = todo.memento;
	}

	public String getMemento() {
		return memento;
	}

	public Todo setMemento(String memento) {
		this.memento = memento;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(memento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (memento == null) {
			if (other.memento != null)
				return false;
		} else if (!memento.equals(other.memento))
			return false;
		return true;
	}
}
