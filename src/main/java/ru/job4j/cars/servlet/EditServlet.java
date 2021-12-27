package ru.job4j.cars.servlet;

import ru.job4j.cars.store.AdStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditServlet", value = "/edit.do")
public class EditServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s = req.getParameter("id").split("ad")[1];
        int id = Integer.parseInt(s);
        AdStore.getInstance().updateAdSaleStatus(id);
    }
}
