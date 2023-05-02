package library.libraryManager.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String receiver,
                          String receiverName,
                          String title,
                          String price,
                          String address,
                          String phoneNumber,
                          String date,
                          String orderNumber){
        SimpleMailMessage message=new SimpleMailMessage();

        message.setFrom("springemailsender23@gmail.com");
        message.setTo(receiver);
        message.setText("Dear "+receiverName+",\n" +
                "\n" +
                "We are pleased to inform you that your recent order with our company has been successfully processed and confirmed. Your order number is "+receiverName+", and we have received the payment for your order.\n" +
                "\n" +
                "We would like to take this opportunity to thank you for choosing our company for your purchase. Our team has processed your order with the utmost care, and we are pleased to inform you that your items will be shipped as soon as possible.\n" +
                "\n" +
                "Your order details are as follows:\n" +
                "\n" +
                "Order Number: "+orderNumber+"\n" +
                "Order Date: "+date+"\n" +
                "Shipping Address: "+address+"\n" +
                "Phone Number: "+phoneNumber+"\n"+
                "Book Ordered: \""+title+"\"\n" +
                "Total Cost: "+price+"\n" +
                "\n" +
                "Please review the details above and let us know if you have any questions or concerns. If you need to make any changes to your order, please contact us as soon as possible.\n" +
                "\n" +
                "We will be sending you an email with the tracking number once your order is shipped, so that you can track your package and plan for its arrival.\n" +
                "\n" +
                "Thank you once again for choosing our company for your purchase. We appreciate your business and look forward to serving you in the future.\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "Mario\n" +
                "Bookstore23");
        message.setSubject("Bookstore23 Order Confirmation - "+orderNumber);


        mailSender.send(message);
    }

}
