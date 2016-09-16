package ch.ivyteam.hipster;

import java.io.Console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final ConfigurableApplicationContext context;
	private RawMessageToHipsterMessageConverter converter;

    public Runner(ConfigurableApplicationContext context, RawMessageToHipsterMessageConverter converter) {
        this.context = context;
		this.converter = converter;
    }

    @Override
    public void run(String... args) throws Exception {
    	System.out.println("Converter up and running...");
   }
}