package eu.organicity.client;

import eu.organicity.client.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;


public class CustomResponseErrorHandler implements ResponseErrorHandler {
    private static final Logger LOGGER = Logger.getLogger(CustomResponseErrorHandler.class.getName());

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (clientHttpResponse.getStatusCode() == HttpStatus.OK) {
            return false;
        } else if (clientHttpResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return true;
        }
        return true;

    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (clientHttpResponse.getStatusCode() != HttpStatus.OK) {
            switch (clientHttpResponse.getStatusCode()) {
                case UNAUTHORIZED:
                    LOGGER.info("Response " + clientHttpResponse.getStatusText());
                    InputStream inputStream1 = clientHttpResponse.getBody();
                    byte[] buffer1 = new byte[1024];
                    inputStream1.read(buffer1);
                    LOGGER.info(new String(buffer1, "UTF-8"));
                    throw new UnauthorizedException();
                default:
                    LOGGER.info("Status code: " + clientHttpResponse.getStatusCode());
                    LOGGER.info("Response " + clientHttpResponse.getStatusText());
                    InputStream inputStream = clientHttpResponse.getBody();
                    byte[] buffer = new byte[1024];
                    inputStream.read(buffer);
                    LOGGER.info(new String(buffer, "UTF-8"));
                    break;
            }

        }
    }
}
