package edu.pezzati.rdxgwt.client.todo.util;

public class UUID4 {

	public native static String generateUUID() /*- {
		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
			var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
			return v.toString(16);
		});
	} -*/;
}