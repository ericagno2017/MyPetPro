package ar.com.enrique.clinicsmanager.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

@Service
public class RestResponseHandler {

    @Autowired
    private BackEndErrorFactory errorFactory;

//    private static final Logger LOGGER = Logger.getLogger(RestResponseHandler.class);

    public Response buildSuccessResponse(Object entity, StatusType successStatus) {
        return Response.status(successStatus)
                .entity(entity)
                .build();
    }

    public Response buildSuccessResponse(StatusType successStatus) {
        return buildSuccessResponse(new EmptyObject(), successStatus);
    }

    public Response buildErrorResponse(Exception exc) {
        return buildErrorResponse(exc, true);
    }

    public Response buildErrorResponse(Exception exc, boolean logException) {

//        if (logException) {
//            LOGGER.error("Failed!", exc);
//        }

        BackEndError internalError = errorFactory.buildInternalError(exc);

        return Response.status(internalError.getHttpCode())
                .type(MediaType.APPLICATION_JSON)
                .entity(internalError)
                .build();
    }

    public Response buildRedirectURI(URI location) {
        return Response.seeOther(location)
                .build();
    }

    public StreamingOutput createStreamingOutput(byte[] out) {
        return new StreamingOutput() {

            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                output.write(out);
            }
        };
    }
}
