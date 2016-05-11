package cz.muni.fi.pb138.basex;

import org.basex.core.Context;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * Created by Michal Holic on 11/05/2016
 */
@Component
public class BaseXContext implements DisposableBean {

	private Context context = null;

	public Context getContext() {
		if (context == null) context = new Context();
		return context;
	}

	@Override
	public void destroy() throws Exception {
		if (context == null) return;
		context.close();
	}

}
