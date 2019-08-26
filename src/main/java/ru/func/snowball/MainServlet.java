package ru.func.snowball;

import org.jsoup.Jsoup;
import ru.func.snowball.post.PostParser;
import ru.func.snowball.post.parser.parserimpl.JoyReactorJSONPostParser;
import ru.func.snowball.post.parser.parserimpl.JoyReactorXMLPostParser;
import ru.func.snowball.post.parser.parserimpl.PikabuJSONPostParser;
import ru.func.snowball.post.parser.parserimpl.PikabuXMLPostParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String postUrl = req.getParameter("post");

        PostParser postParser;

        if (req.getParameter("type").equalsIgnoreCase("xml")) {
            resp.setCharacterEncoding("utf-8");
            if (postUrl.contains("joyreactor"))
                postParser = new JoyReactorXMLPostParser();
            else
                postParser = new PikabuXMLPostParser();
        } else  {
            resp.setCharacterEncoding("windows-1251");
            if (postUrl.contains("joyreactor"))
                postParser = new JoyReactorJSONPostParser();
            else
                postParser = new PikabuJSONPostParser();
        }

        resp.getWriter().write(postParser.getPost(Jsoup.connect(postUrl).get(), postUrl, null));
    }
}
