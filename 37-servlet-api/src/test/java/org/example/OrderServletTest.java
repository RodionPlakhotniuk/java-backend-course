package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Order;
import org.example.model.Product;
import org.example.servlet.OrderServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServletTest {

    private OrderServlet servlet;
    private ObjectMapper objectMapper;

    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;

    @BeforeEach
    void setUp() {
        servlet = new OrderServlet();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateAndGetOrder() throws IOException, ServletException {
        Order order = new Order(1, "2025-07-24", 150.0,
                List.of(new Product(1, "Product1", 50.0), new Product(2, "Product2", 100.0)));

        ByteArrayInputStream postInput = new ByteArrayInputStream(objectMapper.writeValueAsBytes(order));
        when(mockRequest.getInputStream()).thenReturn(new DelegatingServletInputStream(postInput));

        ByteArrayOutputStream postOutput = new ByteArrayOutputStream();
        when(mockResponse.getOutputStream()).thenReturn(new DelegatingServletOutputStream(postOutput));

        servlet.doPost(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_CREATED);
        verify(mockResponse).setContentType("application/json");
        String jsonResponse = postOutput.toString();
        Order createdOrder = objectMapper.readValue(jsonResponse, Order.class);
        assertEquals(order, createdOrder);

        reset(mockRequest, mockResponse);

        when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream getOutput = new ByteArrayOutputStream();
        when(mockResponse.getOutputStream()).thenReturn(new DelegatingServletOutputStream(getOutput));

        servlet.doGet(mockRequest, mockResponse);

        verify(mockResponse).setContentType("application/json");
        Order fetchedOrder = objectMapper.readValue(getOutput.toByteArray(), Order.class);
        assertEquals(order, fetchedOrder);
        verify(mockResponse, never()).setStatus(anyInt());
    }

    @Test
    void testGetOrderNotFound() throws IOException, ServletException {
        when(mockRequest.getPathInfo()).thenReturn("/999");
        servlet.doGet(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testGetOrderBadRequest() throws IOException, ServletException {
        when(mockRequest.getPathInfo()).thenReturn("/abc");
        servlet.doGet(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);

        reset(mockRequest, mockResponse);
        when(mockRequest.getPathInfo()).thenReturn(null);
        servlet.doGet(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);

        reset(mockRequest, mockResponse);
        when(mockRequest.getPathInfo()).thenReturn("/");
        servlet.doGet(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


    @Test
    void testUpdateOrder() throws IOException, ServletException {
        Order originalOrder = new Order(2, "2025-07-25", 200.0, List.of(new Product(3, "Product3", 200.0)));

        ByteArrayInputStream postInput = new ByteArrayInputStream(objectMapper.writeValueAsBytes(originalOrder));
        when(mockRequest.getInputStream()).thenReturn(new DelegatingServletInputStream(postInput));
        when(mockResponse.getOutputStream()).thenReturn(new DelegatingServletOutputStream(new ByteArrayOutputStream()));

        servlet.doPost(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_CREATED);

        reset(mockRequest, mockResponse);

        Order updatedOrder = new Order(2, "2025-07-26", 250.0,
                List.of(new Product(3, "Product3", 100.0), new Product(4, "Product4", 150.0)));

        ByteArrayInputStream putInput = new ByteArrayInputStream(objectMapper.writeValueAsBytes(updatedOrder));
        when(mockRequest.getInputStream()).thenReturn(new DelegatingServletInputStream(putInput));
        when(mockRequest.getPathInfo()).thenReturn("/2");

        ByteArrayOutputStream putOutput = new ByteArrayOutputStream();
        when(mockResponse.getOutputStream()).thenReturn(new DelegatingServletOutputStream(putOutput));

        servlet.doPut(mockRequest, mockResponse);

        verify(mockResponse).setContentType("application/json");
        Order fetchedUpdatedOrder = objectMapper.readValue(putOutput.toByteArray(), Order.class);
        assertEquals(updatedOrder, fetchedUpdatedOrder);
        verify(mockResponse, never()).setStatus(anyInt());

        reset(mockRequest, mockResponse);

        when(mockRequest.getPathInfo()).thenReturn("/2");
        ByteArrayOutputStream getOutput = new ByteArrayOutputStream();
        when(mockResponse.getOutputStream()).thenReturn(new DelegatingServletOutputStream(getOutput));

        servlet.doGet(mockRequest, mockResponse);
        Order retrievedOrder = objectMapper.readValue(getOutput.toByteArray(), Order.class);
        assertEquals(updatedOrder, retrievedOrder);
    }

    @Test
    void testUpdateOrderNotFound() throws IOException, ServletException {

        when(mockRequest.getPathInfo()).thenReturn("/999");

        servlet.doPut(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testUpdateOrderBadRequestPathMismatch() throws IOException, ServletException {
        Order originalOrder = new Order(3, "2025-07-25", 200.0, List.of(new Product(3, "Product3", 200.0)));

        ByteArrayInputStream postInput = new ByteArrayInputStream(objectMapper.writeValueAsBytes(originalOrder));
        when(mockRequest.getInputStream()).thenReturn(new DelegatingServletInputStream(postInput));
        when(mockResponse.getOutputStream()).thenReturn(new DelegatingServletOutputStream(new ByteArrayOutputStream()));
        servlet.doPost(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_CREATED);

        reset(mockRequest, mockResponse);

        Order updatedOrder = new Order(4, "2025-07-26", 250.0, List.of(new Product(3, "Product3", 100.0)));
        ByteArrayInputStream putInput = new ByteArrayInputStream(objectMapper.writeValueAsBytes(updatedOrder));
        when(mockRequest.getInputStream()).thenReturn(new DelegatingServletInputStream(putInput));
        when(mockRequest.getPathInfo()).thenReturn("/3");

        servlet.doPut(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testUpdateOrderBadRequest() throws IOException, ServletException {

        when(mockRequest.getPathInfo()).thenReturn("/abc");
        servlet.doPut(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);

        reset(mockRequest, mockResponse);
        when(mockRequest.getPathInfo()).thenReturn(null);
        servlet.doPut(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);

        reset(mockRequest, mockResponse);
        when(mockRequest.getPathInfo()).thenReturn("/");
        servlet.doPut(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDeleteOrder() throws IOException, ServletException {
        Order order = new Order(3, "2025-07-27", 300.0, List.of(new Product(5, "Product5", 300.0)));

        ByteArrayInputStream postInput = new ByteArrayInputStream(objectMapper.writeValueAsBytes(order));
        when(mockRequest.getInputStream()).thenReturn(new DelegatingServletInputStream(postInput));
        when(mockResponse.getOutputStream()).thenReturn(new DelegatingServletOutputStream(new ByteArrayOutputStream()));

        servlet.doPost(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_CREATED);

        reset(mockRequest, mockResponse);

        when(mockRequest.getPathInfo()).thenReturn("/3");
        servlet.doDelete(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_NO_CONTENT);

        reset(mockRequest, mockResponse);

        when(mockRequest.getPathInfo()).thenReturn("/3");
        servlet.doGet(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDeleteOrderNotFound() throws IOException, ServletException {

        when(mockRequest.getPathInfo()).thenReturn("/999");
        servlet.doDelete(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDeleteOrderBadRequest() throws IOException, ServletException {
        when(mockRequest.getPathInfo()).thenReturn("/abc");
        servlet.doDelete(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);

        reset(mockRequest, mockResponse);
        when(mockRequest.getPathInfo()).thenReturn(null);
        servlet.doDelete(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);

        reset(mockRequest, mockResponse);
        when(mockRequest.getPathInfo()).thenReturn("/");
        servlet.doDelete(mockRequest, mockResponse);
        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


    static class DelegatingServletInputStream extends ServletInputStream {
        private final InputStream sourceStream;

        public DelegatingServletInputStream(InputStream sourceStream) {
            this.sourceStream = sourceStream;
        }

        @Override
        public int read() throws IOException {
            return sourceStream.read();
        }

        @Override
        public boolean isFinished() {
            try {
                return sourceStream.available() == 0;
            } catch (IOException e) {
                return true;
            }
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }
    }

    static class DelegatingServletOutputStream extends ServletOutputStream {
        private final OutputStream targetStream;

        public DelegatingServletOutputStream(OutputStream targetStream) {
            this.targetStream = targetStream;
        }

        @Override
        public void write(int b) throws IOException {
            targetStream.write(b);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            throw new UnsupportedOperationException();
        }
    }
}