package app.Services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.Models.Notifications;
import app.Util.HibernateUtil;

@Stateless
public class NotificationService {
    public boolean createNotification(Notifications notification){
        Transaction transaction = null;
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:5000")
                    .path("usertype")
                    .queryParam("id", notification.getStudentId());
                String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
                System.out.println(response);
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);
            String role = jsonResponse.get("role").asText();
            if (!role.equals("student"))
                return false;
            session.save(notification);
            transaction.commit();
            HibernateUtil.closeSession(session);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public List<Notifications> getNotifications(Long id){
        Session session = null;
        List<Notifications> notifications = null;
        try {
            session = HibernateUtil.getSession();
            String hql = "FROM Notifications WHERE studentId = :id";
            Query<Notifications> query = session.createQuery(hql, Notifications.class);
            query.setParameter("id", id);
            notifications = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                HibernateUtil.closeSession(session);
        }
        return notifications;
    }
}
