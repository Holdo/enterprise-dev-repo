package cz.muni.fi.pb138.webmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class HelloWorldController {

	@RequestMapping("/")
	public String helloWorld() {
		return "Hello World!";
	}

	@RequestMapping("/async")
	public Callable<String> helloWorldAsync() {
		return () -> "async: " + HelloWorldController.this.helloWorld();
	}
}