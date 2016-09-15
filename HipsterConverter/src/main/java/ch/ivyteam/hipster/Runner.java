package ch.ivyteam.hipster;

import java.io.Console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final ConfigurableApplicationContext context;

    public Runner(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {
//    	Console console = System.console();
    	System.out.println("Converter up and running...");
//    	String readLine = null;
//		do {
//    		System.out.println("press 'q' to quit...");
//    		readLine = console.readLine();
//    	} while (readLine == null || !readLine.toLowerCase().equals("q"));
//    	context.close();
    }
}