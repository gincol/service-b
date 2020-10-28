package es.vn.sb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.vn.sb.utils.Constants;
import es.vn.sb.utils.Utils;

@RestController
@RequestMapping("/hello")
public class HelloController {

	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Value("${spring.application.name}")
	private String appName;

	@Value("${spring.application.version}")
	private String appVersion;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> hello() {
		logger.info("START hello():");
		return new ResponseEntity<String>(String.format("HELLO from '%s' in pod '%s'\n", appName, Utils.getPodName()),
				HttpStatus.OK);
	}

	@RequestMapping(path = "/version", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> version(
			@RequestHeader(value = "sprint", required = false, defaultValue = "0") String sprint) {
		logger.info("START hello(): sprint: " + sprint);

		return new ResponseEntity<String>(
				String.format("HELLO from '%s' in sprint: '%s', version: '%s' and pod: '%s'", appName, sprint,
						appVersion, Utils.getPodName()),
				HttpStatus.OK);
	}

	@RequestMapping(path = "/direct", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> helloDirect() {
		logger.info("START helloDirect():");
		return new ResponseEntity<String>(String.format("HELLO from '%s', version: '%s' and pod: '%s'", appName,
				appVersion, Utils.getPodName()), HttpStatus.OK);
	}

	@RequestMapping(path = "/error/{error}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> helloError(@PathVariable int error) {
		logger.info("START helloError():");
		Constants.ERROR = error;
		
		return new ResponseEntity<String>(String.format("ERROR value from '%s', version: '%s', pod: '%s' and error: '%d'\n", appName,
				appVersion, Utils.getPodName(), error),
				HttpStatus.OK);
	}

}
