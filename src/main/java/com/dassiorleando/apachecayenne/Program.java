package com.dassiorleando.apachecayenne;

import com.dassiorleando.apachecayenne.services.AuthorService;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;

/**
 * Main program
 * @author dassiorleando
 */
public class Program {

    static ObjectContext context = ServerRuntime.builder()
                .addConfig("cayenne-blog.xml")
                .build().newContext();

    public static void main(String [] args) {
        AuthorService authorService = new AuthorService(context);

        // Let's save an author with the name "Dassi Jerome"
        authorService.save("Dassi Jerome");
    }

}
