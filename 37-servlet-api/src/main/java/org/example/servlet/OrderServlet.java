package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/orders/*")
public class OrderServlet extends HttpServlet {
    private final Map<Integer, Order> orders = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = objectMapper.readValue(req.getInputStream(), Order.class);
        if (orders.containsKey(order.getId())) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        }
        orders.put(order.getId(), order);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getOutputStream(), order);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            Order order = orders.get(id);
            if (order == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), order);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            if (!orders.containsKey(id)) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Order updatedOrder = objectMapper.readValue(req.getInputStream(), Order.class);
            if (id != updatedOrder.getId()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            orders.put(id, updatedOrder);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), updatedOrder);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            Order removed = orders.remove(id);
            if (removed == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}