package com.brtvsk.unused;

//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;
//import java.util.NoSuchElementException;

//@Provider
//public class ExceptionHandler implements ExceptionMapper<Throwable> {
//    @Override
//    public Response toResponse(Throwable t) {
//        int status = 500;
//        String message = "Internal Server Error";
//        if (t instanceof NoSuchElementException) {
//            status = 404;
//            message = t.getMessage();
//        } else if (t instanceof IllegalArgumentException) {
//            status = 422;
//            message = t.getMessage();
//        }
//        return Response.status(status).entity(message).build();
//    }
//}
