package eu.organicity.client;

import eu.organicity.client.exception.UnauthorizedException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.StringWriter;
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
                    throw new UnauthorizedException();
                default:
                    LOGGER.info("Status code: " + clientHttpResponse.getStatusCode());
                    LOGGER.info("Response " + clientHttpResponse.getStatusText());
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(clientHttpResponse.getBody(), writer);
                    LOGGER.info(writer.toString());
                    break;
            }

        }
    }
}
