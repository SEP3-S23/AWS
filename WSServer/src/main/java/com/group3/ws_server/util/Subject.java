package com.group3.ws_server.util;
import java.beans.PropertyChangeListener;

public interface Subject {

    PropertyChangeListener addListener(String eventName, PropertyChangeListener listener);

    void removeListener(String eventName, PropertyChangeListener listener);

}
