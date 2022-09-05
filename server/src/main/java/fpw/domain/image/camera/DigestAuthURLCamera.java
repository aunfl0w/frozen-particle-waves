package fpw.domain.image.camera;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DigestAuthURLCamera extends BasicAuthURLCamera {

	@Override
	InputStream getInputStream(URL u) throws IOException {
			final int TIMEOUT = 7000;
			final CloseableHttpClient httpclient = HttpClients.createDefault();
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(TIMEOUT)
					.setConnectionRequestTimeout(TIMEOUT)
					.setSocketTimeout(TIMEOUT)
					.build();
			final HttpClientContext localContext = HttpClientContext.create();
			localContext.setRequestConfig(config);
			final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(new AuthScope(AuthScope.ANY),
					new UsernamePasswordCredentials(username, password));
			localContext.setCredentialsProvider(credentialsProvider);
			final HttpGet httpget = new HttpGet(url);
			CloseableHttpResponse res = httpclient.execute(httpget, localContext);
			log.info(res.toString());
			return res.getEntity().getContent();
		
	}

}
