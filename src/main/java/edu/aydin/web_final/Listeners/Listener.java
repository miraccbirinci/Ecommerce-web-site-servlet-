package edu.aydin.web_final.Listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class Listener implements HttpSessionAttributeListener { //sessionda ne zaman degisiklik olursa onu yazdiriyor yani surekli sessiondaki degisiklikleri dinliyor
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println("-- HttpSessionAttributeListener#attributeAdded() --");
        System.out.printf("added attribute name: %s, value:%s %n", event.getName(),
                event.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

    }
}
