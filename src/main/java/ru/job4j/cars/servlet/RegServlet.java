package ru.job4j.cars.servlet;

import ru.job4j.cars.model.User;
import ru.job4j.cars.store.AdStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegServlet", value = "/reg.do")
public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        AdStore store = AdStore.getInstance();
        if (store.findUserByName(name) != null || store.findUserByEmail(email) != null) {
            resp.sendError(409);
        } else {
            store.save(User.of(name, email, password));
        }
    }
}
