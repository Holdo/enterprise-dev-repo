package cz.muni.fi.pb138.basex;

import org.basex.core.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
/**
 * Created by Michal Holic on 11/05/2016
 */
@Service
public class BaseXContext {

	private static final Logger log = LoggerFactory.getLogger(BaseXContext.class);

	private Context context = null;

	public Context getContext() {
		if (context == null) {
			log.debug("Initializing BaseX context");
			context = new Context();
		}
		return context;
	}

	@PreDestroy
	private void preDestroy() {
		if (context == null) return;
		log.debug("Closing BaseX context.");
		context.close();
	}
}
